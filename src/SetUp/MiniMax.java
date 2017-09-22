package SetUp;


import java.util.HashMap;
import java.util.LinkedList;

import Eval.Board;



public class MiniMax {

	boolean myTurn = true ;
	
	public Position minimax(GomokuModel board, int depth, double alpha, double beta, String me, String opponent, Position bestMove){
		Position tempPos;
		Board b = new Board();
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
		
		if(depth == 0){
			int finalScore; 
			if(myTurn){
				finalScore = b.eval(me);
			}else{
				finalScore = b.eval(opponent);
			}
			Position move = new Position(bestMove.row, bestMove.column, finalScore);
			return move;
			
		}
				
		//Min and Max with alpha-beta pruning
		while(moveList.size() > 0){
			GomokuModel tempBoard = board;
			Position newMove = moveList.getFirst();
			if(myTurn == false){
				tempBoard.makeMove(tempBoard, newMove.row, newMove.column, opponent);
				myTurn = true;
			}else{
				tempBoard.makeMove(tempBoard, newMove.row, newMove.column, me);
				myTurn = false;
			}
			tempPos = minimax(tempBoard, depth - 1, alpha, beta, me, opponent, newMove);
			tempBest = tempPos.score;
			if (tempBest > alpha) {
				best = tempBest;
				bestMove = newMove;
			}
			if (alpha >= beta) {
				Position p = new Position(bestMove.row, bestMove.column, alpha);
				myTurn = true;
				return p;
			}
			moveList.removeFirst();
		}
		Position pos = new Position(bestMove.row, bestMove.column, alpha);
		myTurn = true;
		return pos;	}
}
