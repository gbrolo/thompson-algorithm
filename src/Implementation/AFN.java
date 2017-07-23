package Implementation;

import Syntax.PostFix;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * AFN
 * An AFN constructed from a regExp using McNaughton-Yamada-Thompson's algorithm
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class AFN {
    private PostFix postFix; // to handle infix to postfix
    private String regExp; // the regExp in infix
    private String postFixRegExp; // the regExp in postfix
    private List<Character> symbolList; // AFN's symbol list
    private List<Transition> transitionsList; // AFN's transitions list

    public static int stateCount;

    private State currentInitialState;
    private State currentFinalState;
    private State previousInitialState;
    private State previousFinalState;
    private Stack<State> lookahead;

    /**
     * Constructor
     * @param regExp
     */
    public AFN(String regExp) {
        stateCount = 0;
        this.regExp = regExp;
        postFix = new PostFix();
        postFixRegExp = PostFix.infixToPostfix(regExp);
        System.out.println(postFixRegExp);
        symbolList = new LinkedList<Character>();
        transitionsList = new LinkedList<Transition>();
        lookahead = new Stack<State>();
        computeSymbolList();
        regExpToAFN();
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
     * Returns symbolList, the List with symbols as Characters
     * @return @symbolList
     */
    public List<Character> getSymbolList () {
        return this.symbolList;
    }

    public List<Transition> getTransitionsList () {
        return this.transitionsList;
    }

    private void regExpToAFN(){
        // valid expression stored at postFixRegExp

        Transition tr0 = new Transition(Character.toString(postFixRegExp.charAt(0)));
        transitionsList.add(tr0);
        currentInitialState = tr0.getInitialState();
        currentFinalState = tr0.getFinalState();

        for (int i = 1; i < postFixRegExp.length(); i ++) {
            // if character at i is in symbolList
            if (symbolList.contains(postFixRegExp.charAt(i))) {
                Transition tr1 = new Transition(Character.toString(postFixRegExp.charAt(i)));
                transitionsList.add(tr1);
                previousInitialState = currentInitialState;
                currentInitialState = tr1.getInitialState();
                previousFinalState = currentFinalState;
                currentFinalState = tr1.getFinalState();

                // lookahead
                if ((i+1) <= postFixRegExp.length()) {
                    if (symbolList.contains(postFixRegExp.charAt(i+1))) {
                        // danger, I said danger
                        lookahead.push(previousFinalState);
                    }
                }
            } else if (Character.toString(postFixRegExp.charAt(i)).equals("|")) {
                unify(previousInitialState, previousFinalState, currentInitialState, currentFinalState);
            } else if (Character.toString(postFixRegExp.charAt(i)).equals("*")) {
                kleene(currentInitialState, currentFinalState);
            } else if (Character.toString(postFixRegExp.charAt(i)).equals(".")) {
                if(lookahead.size() > 0) {
                    previousFinalState = lookahead.pop();
                }
                concatenate(previousFinalState, currentInitialState);
            }
        }
    }

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

    private void concatenate(State initialState, State finalState) {
        Transition tr1 = new Transition("ε", initialState, finalState);
        currentInitialState = currentFinalState;
        transitionsList.add(tr1);
    }

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

}
