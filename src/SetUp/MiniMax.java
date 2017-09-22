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
				Position old = new Position(i,j,0,g.board.cells[i][j], g);
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
			g.board.cells[row][col] = player;
		}
		return g;
	}
	
	public GomokuModel undo(GomokuModel g, Position p){
		g.board.cells[p.row][p.column] = " ";
		return g;
	}
	public Position minimax(GomokuModel board, int depth, double alpha, double beta, String me, String opponent, boolean save){
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
		Position temp = null;
		for(Position p: myStone.keySet()){
			for(Position coord: board.lookAround(p).keySet()){
				if(temp == null){
					temp = coord;
					moveList.add(coord);
					continue;
				}
				if((temp.row == coord.row && temp.column != coord.column) ||
				(temp.row != coord.row && temp.column != coord.column)||
				(temp.row != coord.row && temp.column == coord.column)){
					temp = coord;
					moveList.add(coord);
					
				}
			}
		}
		String player;
		if(depth == 0){
			int finalScore; 
			if(myTurn){
				finalScore = board.board.eval();
				player = me;
			}else{
				finalScore = board.board.eval();
				myTurn = false;
				player = opponent;
			}
			board = reset();
			if(moveList.size() == 0){
				player = "GameOver";
				Position position = new Position(0,0, finalScore, player, board);
				return position;
			}
			
			Position move = moveList.getFirst();
			Position position = new Position(move.row, move.column, finalScore, player, board);
			return position;
			
		}
		Position bestMove = null;

		//Min and Max with alpha-beta pruning
			if(me.equals("X") && myTurn){
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, me);
					myTurn = false;
					board.showBoard();
					tempPos = minimax(board, depth - 1, alpha, beta, me, opponent, true);
					board = undo(board, newMove);
					board.showBoard();
					if(bestMove == null || bestMove.score < tempPos.score){
						bestMove = tempPos;
						bestMove.move = newMove;
						
					}
					if(tempPos.score > alpha){
						alpha = tempPos.score;
						bestMove = tempPos;
						bestMove.move = newMove;
					}
					if(beta <= alpha){
						bestMove.score = beta;
						bestMove.move = null;
						return bestMove;
					}
					
					moveList.removeFirst();
					
				}
				board = reset();
				return bestMove;
			}else{
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, opponent);
					myTurn = true;
					board.showBoard();
					tempPos = minimax(board, depth - 1, alpha, beta, me, opponent, true);
					board = undo(board, newMove);
					board.showBoard();
					if(bestMove == null || bestMove.score > tempPos.score){
						bestMove = tempPos;
						bestMove.move = newMove;
						
					}
					if(tempPos.score < beta){
						beta = tempPos.score;
						bestMove = tempPos;
						bestMove.move = newMove;
					}
					if(beta <= alpha){
						bestMove.score = alpha;
						bestMove.move = null;
						return bestMove;
					}
					
					moveList.removeFirst();
				}
			}
			board = reset();
			return bestMove;	
		}
}
