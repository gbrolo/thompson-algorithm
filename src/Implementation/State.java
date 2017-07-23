package Implementation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class State {
    private List<State> previousStates;
    private List<State> nextStates;
    private int stateId;

    public State(int stateId) {
        this.stateId = stateId;
        this.previousStates = new LinkedList<State>();
        this.nextStates = new LinkedList<State>();
        AFN.stateCount++;
    }

    public State(int stateId, List<State> previousState, List<State> nextState) {
        this.stateId = stateId;
        this.previousStates = previousState;
        this.nextStates = nextState;
        AFN.stateCount++;
    }

    public void addPreviousState(State previousState) {
        this.previousStates.add(previousState);
    }

    public void addNextState(State nextState) {
        this.nextStates.add(nextState);
    }

    public String toString() {
        return String.valueOf(this.stateId);
    }
}
