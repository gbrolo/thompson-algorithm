package Implementation;

import java.util.LinkedList;
import java.util.List;

/**
 * State.
 * A state in the AFN.
 * Speaking in Graph terms it would be a Vertex v.
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class State {
    private List<State> previousStates;
    private List<State> nextStates;
    private int stateId;

    private boolean isInitial;
    private boolean isFinal;

    public State(int stateId) {
        this.stateId = stateId;
        this.previousStates = new LinkedList<State>();
        this.nextStates = new LinkedList<State>();
        AFN.stateCount++; // sets a unique ID which comes from AFN's total stateCount
    }

    public State(int stateId, List<State> previousState, List<State> nextState) {
        this.stateId = stateId;
        this.previousStates = previousState;
        this.nextStates = nextState;
        AFN.stateCount++;
    }

    public State(int stateId, boolean dfa) {
        this.stateId = stateId;
        this.previousStates = new LinkedList<>();
        this.nextStates = new LinkedList<>();
    }

    public void addPreviousState(State previousState) {
        this.previousStates.add(previousState);
    }

    public void addNextState(State nextState) {
        this.nextStates.add(nextState);
    }

    public List<State> getPreviousStates() {
        return this.previousStates;
    }

    public List<State> getNextStates() { return this.nextStates; }

    public String toString() {
        return String.valueOf(this.stateId);
    }

    public int getStateId() { return this.stateId; }

    public void setInitial(boolean isInitial) { this.isInitial = isInitial; }

    public boolean getInitial() { return this.isInitial; }

    public void setFinal(boolean isFinal) { this.isFinal = isFinal; }

    public boolean getFinal() { return this.isFinal; }
}
