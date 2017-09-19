package Eval;

import java.util.LinkedList;

public class Path {
	Integer score;
	LinkedList<Cell> path;
	String dir;
	
	public Path(Integer score, LinkedList<Cell> path, String dir){
		this.score = score;
		this.path = path;
		this.dir = dir;
	}
}
