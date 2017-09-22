package Eval;

import java.util.LinkedList;

public class Path {
	Integer score;
	LinkedList<Cell> path;
	String dir;
	String player;
	
	public Path(Integer score, LinkedList<Cell> path, String dir, String player){
		this.score = score;
		this.path = path;
		this.dir = dir;
		this.player = player;
	}
}
