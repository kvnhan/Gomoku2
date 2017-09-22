package SetUp;


import java.util.HashMap;
import java.util.LinkedList;

import Eval.Board;



public class MiniMax {

	boolean myTurn = true;
	LinkedList<Position> originalBoard = new LinkedList<Position>();
	
	public void preserveBoard(GomokuModel g){
		originalBoard = new LinkedList<Position>();
		for(int i = 0; i < g.n; i++){
			for(int j = 0; j < g.n; j++){
				Position old = new Position(i,j,0,g.b[i][j], g);
				originalBoard.add(old);
			}
		}
	}
	
	public GomokuModel reset(){
		GomokuModel g = new GomokuModel();
		for(Position pos: originalBoard){
			int row = pos.row;
			int col = pos.column;
			String player = pos.stone;
			g.b[row][col] = player;
		}
		return g;
	}
	
	public Position minimax(GomokuModel board, int depth, double alpha, double beta, String me, String opponent, Position bestMove, boolean save){
		Position tempPos;
		Board b = new Board();
		if(save == false){
			preserveBoard(board);
		}
		double best;
		double tempBest;
		HashMap<Position, String> myStone;
		if(myTurn){
			myStone = board.getPlayerPiece(me);
		}else{
			myStone = board.getPlayerPiece(opponent);
		}
		// LinkedList<Position> aroundOpponent = new LinkedList<Position>();
		LinkedList<Position> moveList = new LinkedList<Position>();
		
		//Get a list of possible empty spaces to make a move
		if(myStone.size() == 0){
			for(Position position: board.getEmptySpaces().keySet()){
				moveList.add(position);
			}
		}
		
		for(Position p: myStone.keySet()){
			for(Position coord: board.lookAround(p).keySet()){
				moveList.add(coord);
			}
		}
		String player;
		if(depth == 0){
			int finalScore; 
			if(myTurn){
				finalScore = b.eval(me);
				player = me;
			}else{
				finalScore = b.eval(opponent);
				player = opponent;
			}
			board = reset();
			Position move = new Position(bestMove.row, bestMove.column, finalScore, player, board);
			return move;
			
		}
				
		//Min and Max with alpha-beta pruning
		while(moveList.size() > 0){
			Position newMove = moveList.getFirst();
			if(myTurn == false){
				board.makeMove(newMove.row, newMove.column, opponent);
				myTurn = true;
			}else{
				board.makeMove(newMove.row, newMove.column, me);
				myTurn = false;
			}
			tempPos = minimax(board, depth - 1, alpha, beta, me, opponent, newMove, true);
			tempBest = tempPos.score;
			if (tempBest > alpha) {
				best = tempBest;
				bestMove = newMove;
			}
			if (alpha >= beta) {
				myTurn = true;
				board = reset();
				Position p = new Position(bestMove.row, bestMove.column, alpha, me, board);
				return p;
			}
			moveList.removeFirst();
		}
		myTurn = true;
		board = reset();
		Position pos = new Position(bestMove.row, bestMove.column, alpha, me, board);
		return pos;	
		}
}
