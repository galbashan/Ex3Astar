package model.algorithms.minimax;

abstract class Minimax // Java pseudo-code
{
	
	Model2048 m = new Model2048();
	/*
	protected static final Layout layout = Layout.getTheLayout(); // Singleton

	protected abstract int minOrMax(int s1, int s2); // min() or max()

	protected abstract Minimax makeMinimax(); // Factory Method

	protected abstract int getGameOverScore(); // WIN, LOSE, or TIE

	protected abstract int getWorstScore(); // INFINITY or MINUS_INFINITY
	// ...
	*/
	public int minimax( int depth ) // Template Method
	{
		if( layout.isGameOver() )
		{
			return getGameOverScore();
		}
		if( depth == 0 )
		{
			return evaluateBoard() // Maybe Random
		}
		Minimax child = makeMinimax()
		int bestSoFar = getWorstScore()
		Vector moves = layout.getAllLegalMoves()
		Enumeration moveEnum = moves.elements()
		while( moveEnum.hasMoreElements() )
		{
			Move nextMove = (Move) moveEnum.nextElement()
			layout.processMove( nextMove ) // Must undo this
			int score = child.minimax( depth - 1 ) // !
			bestSoFar = minOrMax( bestSoFar, score )
			layout.unprocessMove( nextMove )
		}
		return bestSoFar;
	 }
}
