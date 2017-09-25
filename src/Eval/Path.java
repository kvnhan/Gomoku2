package Eval;

import java.util.LinkedList;

// Class to describe continuous streaks of pieces on the board
public class Path {
	Integer score;			 // The score of this individual path
	LinkedList<Cell> path; 	 // The cells in this path
	String dir;  			 // The direction this path is headed 
				 			 // (Horiz, Vert, DiagUp, DiagDown)
	String player;			 // The player to whose pieces make up the path
	
	
	// Constructor
	public Path(Integer score, LinkedList<Cell> path, String dir, String player){
		this.score = score;
		this.path = path;
		this.dir = dir;
		this.player = player;
	}
}
