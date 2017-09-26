package SetUp;


import java.util.HashMap;
import java.util.LinkedList;

import Eval.Board;
import Eval.Cell;
import Eval.Path;



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
	
	public Position evalThreat(Path threat, GomokuModel board, String player){
		Position move = null;
		if(threat.path.size() > 2){
			Cell first;
			Cell last;
			first = threat.path.getFirst();
			last = threat.path.getLast();
			if(threat.dir.equals("vert")){
				if(first.vert - 1 >= 0){
					if(board.board.cells[first.vert - 1][first.horiz] != null){
						if(board.board.cells[first.vert - 1][first.horiz].equals(" ") ||
								board.board.cells[first.vert - 1][first.horiz] == null){
							move = new Position(first.vert - 1, first.horiz, 0, player, board);
							return move;
						}
					}
				}
				if(last.vert + 1 <= 14){
					if(board.board.cells[last.vert + 1][last.horiz] != null){
						if(board.board.cells[last.vert + 1][last.horiz].equals(" ") ||
								board.board.cells[last.vert + 1][last.horiz] == null){
							move = new Position(last.vert + 1,last.horiz, 0, player, board);
							return move;
						}
					}
				}
				board.board.discardThreat();
				move = evalThreat(board.board.pl.getFirst(), board, player);
				return move;
				
			}else if(threat.dir.equals("horiz")){
					if(first.horiz - 1 >= 0){
						if(board.board.cells[first.vert][first.horiz - 1] != null){
							if(board.board.cells[first.vert][first.horiz - 1].equals(" ")){
								move = new Position(first.vert, first.horiz - 1, 0, player, board);
								return move;
							}
						}
					}
					if(last.horiz + 1 <= 14){
						if(board.board.cells[last.vert][last.horiz + 1] != null){
							if(board.board.cells[last.vert][last.horiz + 1].equals(" ")){
								move = new Position(last.vert, last.horiz + 1, 0, player, board);
								return move;
							}
						}
					}
					board.board.discardThreat();
					move = evalThreat(board.board.pl.getFirst(), board, player);
					return move;
			}else if(threat.dir.equals("diagup")){
					if(first.horiz + 1 <= 14 && first.vert - 1 >= 0){
						if(board.board.cells[first.vert - 1][first.horiz + 1] != null){
							if(board.board.cells[first.vert - 1][first.horiz + 1].equals(" ")){
								move = new Position(first.vert - 1, first.horiz + 1, 0, player, board);
								return move;
							}
						}
					}
					if(last.horiz - 1 >= 0 && last.vert + 1 <= 14){
						if(board.board.cells[last.vert + 1][last.horiz - 1] != null){
							if(board.board.cells[last.vert + 1][last.horiz - 1].equals(" ")){
								move = new Position(last.vert + 1, last.horiz - 1, 0, player, board);
								return move;
						}
					}
					
					board.board.discardThreat();
					move = evalThreat(board.board.pl.getFirst(), board, player);
					return move;
					}
			}else if(threat.dir.equals("diagdown")){
					if(first.horiz - 1 >= 0 && first.vert - 1 >= 0){
						if(board.board.cells[first.vert - 1][first.horiz - 1] != null){
							if(board.board.cells[first.vert - 1][first.horiz - 1].equals(" ")){
								move = new Position(first.vert - 1, first.horiz - 1, 0, player, board);
								return move;
							}
						}
					}
					if(last.horiz + 1 <= 14 && last.vert + 1 <= 14){
						if(board.board.cells[last.vert + 1][last.horiz + 1] != null){
							if(board.board.cells[last.vert + 1][last.horiz + 1].equals(" ")){
								move = new Position(last.vert + 1, last.horiz + 1, 0, player, board);
								return move;
							}
						}
					}
					board.board.discardThreat();
					move = evalThreat(board.board.pl.getFirst(), board, player);
					return move;
				}
			}
					
		return move;
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
		
		
		// Get a list of threats and settle them
		if(myTurn){
			int tempScore = board.board.eval(player);
			Path threat = null;
			threat = board.board.pl.getFirst();
			Position p = null;
			p = evalThreat(threat, board, player);
			if(p != null){
				return p;
			}
				
		}
		//Get empty locations around each player's stone locations
		Position temp = null;
		String opponent;
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
		temp = null;
		if(player.equals("X")){
			opponent = "O";
		}else{
			opponent = "X";
		}
		for(Position position: board.getPlayerPiece(opponent).keySet()){
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
		
		String p;
		//Evaluate the score of the board
		if(depth == 0){
			int finalScore; 
			finalScore = board.board.eval(player);
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
		int opponentcore;

		// Maximizer
			if(myTurn){
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, player);
					myTurn = false;
					tempPos = minimax(board, depth - 1, alpha, beta, opponent, true);
					board = undo(board, newMove);
					if(bestMove == null || bestMove.score < tempPos.score){
						bestMove = newMove;
						bestMove.score = tempPos.score;
						
					}
					if(tempPos.score > alpha){
						alpha = tempPos.score;
						bestMove = newMove;
						bestMove.score = tempPos.score;
					}
					if(beta <= alpha){
						break;
					}
					
					moveList.removeFirst();
					
				}
				board = reset();
				return bestMove;
			// Minimizer
			}else{
				while(moveList.size() > 0){
					Position newMove = moveList.getFirst();
					board.makeMove(newMove.row, newMove.column, player);
					myTurn = true;
					tempPos = minimax(board, depth - 1, alpha, beta, opponent, true);
					board = undo(board, newMove);
					if(bestMove == null || bestMove.score > tempPos.score){
						bestMove = newMove;
						
						
					}
					if(tempPos.score < beta){
						beta = tempPos.score;
						bestMove = newMove;
						bestMove.score = tempPos.score;
					}
					if(beta <= alpha){
						break;
					}
					if(tempPos.score <= -1000){
						board = reset();
						bestMove.score = tempPos.score;
						return bestMove;
					}
					moveList.removeFirst();
				}
			}
			board = reset();
			return bestMove;	
		}
}
