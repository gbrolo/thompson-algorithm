package Implementation;

/**
 * Transition.
 * Every symbol in the AFN is represented with a Transition that has an initial State, a final State and a String with the symbol.
 * Speaking in Graph terms, it would be an Edge e.
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class Transition {
    private State initialState;
    private State finalState;
    private String transitionSymbol;

    public Transition(String transitionSymbol) {
        this.transitionSymbol = transitionSymbol;
        this.initialState = new State(AFN.stateCount);
        this.finalState = new State(AFN.stateCount);

        /* add link to states */
        this.initialState.addNextState(this.finalState);
        this.finalState.addPreviousState(this.initialState);
    }

    // concatenate
    public Transition(String transitionSymbol, State initialState, State finalState) {
        this.transitionSymbol = transitionSymbol;
        this.initialState = initialState;
        this.finalState = finalState;

        /* add link to states */
        this.initialState.addNextState(this.finalState);
        this.finalState.addPreviousState(this.initialState);
    }

    public State getInitialState() {
        return this.initialState;
    }

    public State getFinalState() {
        return this.finalState;
    }

    public String toString() {
        return initialState.toString() + " - " + transitionSymbol + " - " + finalState.toString();
    }

    public String getTransitionSymbol() { return this.transitionSymbol; }
}
