package model.algorithms;


public class State implements Comparable<State> {

	private Object mState;

	private State mParentState;
	private Action mLeadingAction;

	private double f;
	private double g;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;

		State other = (State) obj;
		return this.mState.equals(other.mState);
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f + g;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public Object getState() {
		return mState;
	}

	public void setState(Object mState) {
		this.mState = mState;
	}

	public State getParentState() {
		return mParentState;
	}

	public void setParentState(State mParentState) {
		this.mParentState = mParentState;
	}

	public Action getLeadingAction() {
		return mLeadingAction;
	}

	public void setLeadingAction(Action mLeadingAction) {
		this.mLeadingAction = mLeadingAction;
	}

	@Override
	public int compareTo(State o) {
		return (int) (f - o.f);
	}
}
//