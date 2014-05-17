package model.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class AbsSearcher implements Searcher{

	private int evaluatedNodesNum=0;
	private ArrayList<State> closed_set;
	private PriorityQueue<State> open_set;
	private HashMap<State, State> knownStates;


	public AbsSearcher() {
		this.closed_set  = new ArrayList<State>(); //1
		this.open_set = new PriorityQueue<State>(); //2
		this.knownStates= new HashMap<State, State>();
		this.evaluatedNodesNum=0;
	}
	
	public State getState(State state) {
		if (knownStates.containsKey(state))
			return knownStates.get(state);
		knownStates.put(state, state);
		return state;
	}
	
	public int getNumOfEvaluatedNodes() {
		return evaluatedNodesNum;
	}

	protected Boolean ClosedSetAdd(State state)
	{
		return closed_set.add(state);		
	}
	protected Boolean OpenSetAdd(State state)
	{
		evaluatedNodesNum++;
		return open_set.add(state);		
	}
	protected State OpenSetPoll()
	{	
		 return open_set.remove();
		 
	}
	protected Boolean IsEmptyOpenSet()
	{
		if (open_set.isEmpty())
			return true;
		else
			return false;
	}
	protected Boolean ClosedSetContains(State state)
	{
		if (closed_set.contains(state))
			return true;
		else
			return false;
	}
	protected Boolean OpenSetContains(State state)
	{
		if (open_set.contains(state))
			return true;
		else
			return false;
	}

	public int getEvaluatedNodesNum() {
		return evaluatedNodesNum;
	}
}
