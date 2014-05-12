package model.algorithms.a_star;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import maze.Maze_G_Distance;
import maze.Maze_H_Distance;
import maze.Maze;
import maze.MazeDomain;
import model.algorithms.Action;
import model.algorithms.Domain;
import model.algorithms.Distance;
import model.algorithms.AbsSearcher;
import model.algorithms.State;
import model.algorithms.StateActionPair;

public class AStar extends AbsSearcher implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Domain mDomain;
	private Distance h,g;
	
	public AStar() {
		this.mDomain= new MazeDomain(new Maze());
		this.g=new Maze_G_Distance();
		this.h=new Maze_H_Distance();
	}
	
	public AStar(Domain domain ,Distance g, Distance h) {
		this.mDomain = domain;
		this.h=h;
		this.g=g;
	}

	@Override 
	public ArrayList<Action> search (State sstart, State sgoal)
	{
		State father, current;
		State start=getState(sstart);
		State goal=getState(sgoal);		
		double tempGScore = 0;
		ArrayList<Action> legalActions;
		HashMap<State, StateActionPair> cameFrom = new HashMap<State, StateActionPair>() ;
		
		
		OpenSetAdd(start);
		start.setG(g.getDistance(start, start));
		start.setF(g.getDistance(start, start) + h.getDistance(start, goal));
		
		while (!IsEmptyOpenSet())
		{
			father=OpenSetPoll();
			if (father.equals(goal))
				return reconstructPath(cameFrom, father);
			ClosedSetAdd(father);
			legalActions=mDomain.getActions(father);
			for (Action a : legalActions)
			{
				current=getState(a.doAction(father));
				tempGScore=father.getG() + g.getDistance(father, current);
				
				if(ClosedSetContains(current) && tempGScore >= current.getG())
					continue;
				if ( !OpenSetContains(current) || tempGScore < current.getG() )
				{
					cameFrom.put(current, new StateActionPair(father,a));
					current.setG(tempGScore);
					current.setF(current.getG() + h.getDistance(current, goal));
					if (!OpenSetContains(current))
						OpenSetAdd(current);
				}
			}
				
		}
		
		return null;
		
	}
	
	private ArrayList<Action> reconstructPath (HashMap<State, StateActionPair> cameFrom, State current)
	{
		ArrayList<Action> path = new ArrayList<Action>();
		if (cameFrom==null || cameFrom.isEmpty())
			return null;
		while (cameFrom.containsKey(current) && cameFrom.get(current).getState()!=null) {
				path.add(0,cameFrom.get(current).getAction());
				current=cameFrom.get(current).getState();
		}
		return path;
	}
	
	public Domain getDomain() {
		return mDomain;
	}

	public void setDomain(Domain domain) {
		this.mDomain = domain;
	}

	public Distance getG() {
		return g;
	}

	public void setG(Distance g) {
		this.g = g;
	}

	public Distance getH() {
		return h;
	}

	public void setH(Distance h) {
		this.h = h;
	}

	public Domain getmDomain() {
		return mDomain;
	}

	public void setmDomain(Domain mDomain) {
		this.mDomain = mDomain;
	}
	

}

