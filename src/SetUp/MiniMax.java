package SetUp;


import java.util.HashMap;
import java.util.LinkedList;

import Eval.Board;



public class MiniMax {

	boolean myTurn = true;
	boolean first = true;
	LinkedList<Position> originalBoard = new LinkedList<Position>();
	
	// Keep the  original board before changes being made
	public void preserveBoard(GomokuModel g){
		originalBoard = new LinkedList<Position>();
		for(int i = 0; i < g.n; i++){
			for(int j = 0; j < g.n; j++){
				Position old = new Position(i,j,0,g.board.cells[i][j], g);
				originalBoard.add(old);
			}
		}
	}
	
	// Reset board back to original state
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
	
	// Undo the last move
	public GomokuModel undo(GomokuModel g, Position p){
		g.board.cells[p.row][p.column] = " ";
		return g;
	}
	
	public Position minimax(GomokuModel board, int depth, double alpha, double beta, String player, boolean save){
		Position tempPos;
		Board b = new Board();
		if(save == false){
			preserveBoard(board);
		}
		
		double best;
		double tempBest;
		HashMap<Position, String> myStone;
		// Get Player's stone locations
		myStone = board.getPlayerPiece(player);
		
		LinkedList<Position> moveList = new LinkedList<Position>();
		
		//Get empty locations around each player's stone locations
		Position temp = null;
		String tempS;
		if(player.equals("X")){
			tempS = "O";
		}else{
			tempS = "X";
		}
		for(Position position: board.getPlayerPiece(tempS).keySet()){
			for(Position coord: board.lookAround(position).keySet()){
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
		
		temp = null;	
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
		String p;
		//Evaluate the score of the board
		if(depth == 0){
			int finalScore; 
			finalScore = board.board.eval();
			p = player;
			board = reset();
			if(moveList.size() == 0){
				p = "GameOver";
				Position position = new Position(0,0, finalScore, player, board);
				return position;
			}
			
			Position move = moveList.getFirst();
			Position position = new Position(move.row, move.column, finalScore, player, board);
			return position;
			
		}
		Position bestMove = null;
		int tempScore;

		// If AI is the max
			if(player.equals("O") && myTurn){
				first = true;
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, player);
					tempScore = -board.board.eval();
					myTurn = false;
					tempPos = minimax(board, depth - 1, alpha, beta, "X", true);
					board = undo(board, newMove);
					if(bestMove == null || -bestMove.score < -tempPos.score){
						bestMove = tempPos;
						bestMove.move = newMove;
						
					}
					if(-tempPos.score > alpha){
						alpha = -tempPos.score;
						bestMove = tempPos;
						bestMove.move = newMove;
					}
					if(beta <= alpha){
						break;
					}
					
					moveList.removeFirst();
					
				}
				board = reset();
				return bestMove;
			// If AI is the min
			}else{
				first = false;
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, player);
					myTurn = true;
					tempScore = -board.board.eval();
					tempPos = minimax(board, depth - 1, alpha, beta, "O", true);
					board = undo(board, newMove);
					if(bestMove == null || bestMove.score > -tempPos.score){
						bestMove = tempPos;
						bestMove.move = newMove;
						
					}
					if(-tempPos.score < beta){
						beta = -tempPos.score;
						bestMove = tempPos;
						bestMove.move = newMove;
					}
					if(beta <= alpha){
						break;
					}
					
					moveList.removeFirst();
				}
			}
			board = reset();
			return bestMove;	
		}
}
