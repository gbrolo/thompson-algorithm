package Implementation;

import Syntax.ExpressionSimplifier;
import Syntax.PostFix;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * AFN
 * An AFN constructed from a regExp using McNaughton-Yamada-Thompson's algorithm.
 * Output @AFN.txt: a txt file with the contents of the AFN.
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class AFN {
    private PostFix postFix; // to handle infix to postfix
    private String regExp; // the regExp in infix
    private String postFixRegExp; // the regExp in postfix
    private List<Character> symbolList; // AFN's symbol list
    private List<Transition> transitionsList; // AFN's transitions list
    private List<State> finalStates; // AFN's acceptation states list
    private List<String> initialState; // AFN's initial state
    private List<String> states; // AFN's states

    public static int stateCount; // id for States

    /* For state handlening */
    private State currentInitialState;
    private State currentFinalState;
    private State previousInitialState;
    private State previousFinalState;
    private Stack<State> lookahead;
    private Stack<State> lookahead2;
    private Stack<State> lookahead3;
    private Stack<State> lookahead4;

    /* Abbrebiations and yuxtaposition concatenation */
    private ExpressionSimplifier expressionSimplifier;

    /**
     * Constructor
     * @param regExp
     */
    public AFN(String regExp) {
        stateCount = 0;
        this.regExp = regExp;
        expressionSimplifier = new ExpressionSimplifier(this.regExp);
        postFix = new PostFix();
        postFixRegExp = PostFix.infixToPostfix(expressionSimplifier.getRegExp());
        //System.out.println("Simplified: " + expressionSimplifier.getRegExp());
        symbolList = new LinkedList<Character>();
        transitionsList = new LinkedList<Transition>();
        lookahead = new Stack<State>();
        lookahead2 = new Stack<State>();
        lookahead3 = new Stack<State>();
        lookahead4 = new Stack<State>();
        finalStates = new LinkedList<State>();
        initialState = new LinkedList<String>();
        states = new LinkedList<String>();
        computeSymbolList();
        regExpToAFN();
        computeStateList();
        computeInitialState();
    }

    /**
     * Checks for symbols in postFixRegExp and adds them to symbolList
     */
    private void computeSymbolList() {
        for (int i = 0; i < postFixRegExp.length(); i++) {
            if(!PostFix.precedenceMap.containsKey(postFixRegExp.charAt(i))) {
                if (!symbolList.contains(postFixRegExp.charAt(i))) {
                    symbolList.add(postFixRegExp.charAt(i));
                    Collections.sort(symbolList);
                }
            }
        }
    }

    /**
     * Adds AFN's states to stateList
     */
    public void computeStateList() {
        for (int i = 0; i < transitionsList.size(); i++) {
            if (!states.contains(transitionsList.get(i).getInitialState().toString())) {
                states.add(transitionsList.get(i).getInitialState().toString());
            }

            if (!states.contains(transitionsList.get(i).getFinalState().toString())) {
                states.add(transitionsList.get(i).getFinalState().toString());
            }
        }
    }

    /**
     * Adds AFN's initial state to list
     */
    public void computeInitialState() {
        for (int i = 0; i < transitionsList.size(); i++) {
            if (transitionsList.get(i).getInitialState().getPreviousStates().size() == 0) {
                if (!initialState.contains(transitionsList.get(i).getInitialState().toString())) {
                    initialState.add(transitionsList.get(i).getInitialState().toString());
                }
            }
        }
    }

    /**
     * The real deal.
     * Parses the expression to an AFN.
     */
    private void regExpToAFN(){
        // valid expression stored at postFixRegExp

        // new transition for first symbol
        // from here, every symbol has a Transition that represents it
        Transition tr0 = new Transition(Character.toString(postFixRegExp.charAt(0)));
        transitionsList.add(tr0);
        currentInitialState = tr0.getInitialState();
        currentFinalState = tr0.getFinalState();

        // Traverse the rest of the expression
        for (int i = 1; i < postFixRegExp.length(); i ++) {
            // if character at i is in symbolList
            if (symbolList.contains(postFixRegExp.charAt(i))) {
                Transition tr1 = new Transition(Character.toString(postFixRegExp.charAt(i)));
                transitionsList.add(tr1);
                previousInitialState = currentInitialState;
                currentInitialState = tr1.getInitialState();
                previousFinalState = currentFinalState;
                currentFinalState = tr1.getFinalState();

                // lookahead for yuxtaposed symbols
                if ((i+1) <= postFixRegExp.length()) {
                    if (symbolList.contains(postFixRegExp.charAt(i+1))) {
                        // danger, I said danger
                        lookahead.push(previousFinalState);
                    }
                }
            } else if (Character.toString(postFixRegExp.charAt(i)).equals("|")) {
                // if char is OR
                if (lookahead4.size() > 0) {
                    previousInitialState = lookahead4.pop();
                }
                //System.out.println(previousFinalState);
                unify(previousInitialState, previousFinalState, currentInitialState, currentFinalState);
            } else if (Character.toString(postFixRegExp.charAt(i)).equals("*")) {
                //if char is kleene

                // lookahead for concatenation
                if ((i+1) < postFixRegExp.length()) {
                    if (Character.toString(postFixRegExp.charAt(i+1)).equals(".")) {
                        // danger, I said danger
                        lookahead.push(previousFinalState);
                    }
                }

                if (lookahead2.size() > 0) {
                    currentInitialState = lookahead2.pop();
                }
                kleene(currentInitialState, currentFinalState);
            } else if (Character.toString(postFixRegExp.charAt(i)).equals(".")) {
                // if char is concatenation

                if ((i+2) < (postFixRegExp.length())) {
                    if ((!symbolList.contains(postFixRegExp.charAt(i+2))) && (Character.toString(postFixRegExp.charAt(i+2)).equals("|"))) {
                        lookahead4.push((previousInitialState));
                    }
                }

                // lookahead for concatenation
                if ((i+1) < postFixRegExp.length()) {
                    if (Character.toString(postFixRegExp.charAt(i+1)).equals(".")) {
                        // danger, I said danger
                        lookahead2.push(previousInitialState);
                    }
                }

                if(lookahead.size() > 0) {
                    previousFinalState = lookahead.pop();
                }

                if(lookahead3.size() > 0) {
                    currentFinalState = lookahead3.pop();
                }

                if(lookahead2.size() > 0) {
                    lookahead3.push(currentFinalState);
                    currentFinalState = lookahead2.pop();
                }
                // lookahead for kleene
                if ((i+1) < postFixRegExp.length()) {
                    if (Character.toString(postFixRegExp.charAt(i+1)).equals("*")) {
                        // danger, I said danger
                        lookahead2.push(previousInitialState);
                    }
                }
                concatenate(previousFinalState, currentInitialState);
            }

            if (i == postFixRegExp.length()-1) {
                finalStates.add(currentFinalState);
                if (symbolList.contains('ε')) {
                    symbolList.remove(symbolList.indexOf('ε'));
                }
            }
        }
    }

    /**
     * Union | in Thompson's algorithm
     * @param upperInitialState
     * @param upperFinalState
     * @param lowerInitialState
     * @param lowerFinalState
     */
    private void unify(State upperInitialState, State upperFinalState, State lowerInitialState, State lowerFinalState) {
        // create two new states
        State in = new State(stateCount);
        State out = new State(stateCount);

        currentInitialState = in;
        currentFinalState = out;

        // create new transitions
        Transition tr1 = new Transition("ε", in, upperInitialState);
        Transition tr2 = new Transition("ε", in, lowerInitialState);

        Transition tr3 = new Transition("ε", upperFinalState, out);
        Transition tr4 = new Transition("ε", lowerFinalState, out);

        transitionsList.add(tr1);
        transitionsList.add(tr2);
        transitionsList.add(tr3);
        transitionsList.add(tr4);
    }

    /**
     * Concatenation in Thompson's algorithm
     * @param initialState
     * @param finalState
     */
    private void concatenate(State initialState, State finalState) {
        Transition tr1 = new Transition("ε", initialState, finalState);
        currentInitialState = currentFinalState;
        transitionsList.add(tr1);
    }

    /**
     * Kleene star in Thompson's algorithm
     * @param initialState
     * @param finalState
     */
    private void kleene(State initialState, State finalState) {
        State in = new State(stateCount);
        currentInitialState = in;
        State out = new State(stateCount);
        currentFinalState = out;

        Transition tr1 = new Transition("ε", finalState, initialState); // upper kleene part
        Transition tr2 = new Transition("ε", in, out); // lower kleene part
        Transition tr3 = new Transition("ε", in, initialState); // initial to initial of expression
        Transition tr4 = new Transition("ε", finalState, out); // final of expression to out

        transitionsList.add(tr1);
        transitionsList.add(tr2);
        transitionsList.add(tr3);
        transitionsList.add(tr4);
    }

    public List<Character> getSymbolList () {
        return this.symbolList;
    }

    public List<Transition> getTransitionsList () {
        return this.transitionsList;
    }

    public List<State> getFinalStates () {
        return this.finalStates;
    }

    public List<String> getStates () {
        return this.states;
    }

    public List<String> getInitialState () {
        return this.initialState;
    }

    public String getPostFixRegExp() { return this.postFixRegExp; }

}
