package model.algorithms;


public class StateActionPair {
	
	private State State;
	private Action action;
	
	public StateActionPair(State State, Action action) {
		super();
		this.State = State;
		this.action = action;
	}
	
	public State getState() {
		return State;
	}
	public void setState(State State) {
		this.State = State;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
}
