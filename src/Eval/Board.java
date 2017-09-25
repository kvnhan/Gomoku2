package Eval;

import java.util.Collections;
import java.lang.Math;
import java.util.Comparator;
import java.util.LinkedList;

public class Board {
	
public String[][] cells = new String[15][15];
public LinkedList<Path> pl = new LinkedList<Path>();


public Board()
{
}

public void initBoard()
{
	for(int i=0; i<cells.length; i++) {

        for(int j=0; j<cells[i].length; j++) {
        	cells[i][j] = "Empty";
        }
    }
}
public void printBoard() 
{
	System.out.println("=================================");
	for(int i=0; i<cells.length; i++) {
		System.out.println("");
        for(int j=0; j<cells[i].length; j++) {
        	System.out.print("|" + cells[i][j] + "|");
        }
    }
}

public void makeMove(Integer h, Integer v, String value){
	cells[h][v] = value;
}


// Function to find all paths on the board for a given player
// .. and evaluate the board to give it a score
public Integer eval(String player)
{
	// The list of all paths on the board
	// Empty to start
	LinkedList<Path> pathlist = new LinkedList<Path>();
	
	// Figure out who the opponent player is...
	String opp;
	if(player.equals("O")){
		opp = "X";
	}else{
		opp = "O";
	}
	
	// Iterate over all 225 cells on the board
	for(int i=0; i<cells.length; i++) {
        for(int j=0; j<cells[i].length; j++) {
        		// When we find one of our own cells, search around it
        		if(cells[i][j].equals(player)){
        			
        			// Need to consider streaks in all possible directions
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			
        			// Create a cell object at this position
        			Cell c = new Cell(i, j);
        			
        			// This cell belongs to all possible paths
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			
        			// Make a path object to represent these streaks
        			Path vp = new Path(0, vl, "vert", player);
        			Path hp = new Path(0, hl, "horiz", player);
        			Path dup = new Path(0, dul, "diagup", player);
        			Path ddp = new Path(0, ddl, "diagdown", player);

        			// Now search around this cell in all possible directions
        			// (Generates paths recursively)
        			searchAroundHoriz(i, j, hp, player);
        			searchAroundVert(i, j, vp, player);
        			searchAroundDiagUp(i, j, dup, player);
        			searchAroundDiagDown(i, j, ddp, player);
        			
        			// Add all of these paths to the list of paths on this board
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        		}
        		
        		// Do the same for an opponents piece...
        		else if(cells[i][j].equals(opp)){
        			
        			// Need to consider streaks in all possible directions
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			
        			// Create a cell object at this position
        			Cell c = new Cell(i, j);
        			
        			// This cell belongs to all possible paths
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			
        			// Make a path object to represent these streaks
        			Path vp = new Path(0, vl, "vert", opp);
        			Path hp = new Path(0, hl, "horiz", opp);
        			Path dup = new Path(0, dul, "diagup", opp);
        			Path ddp = new Path(0, ddl, "diagdown", opp);

        			// Now search around this cell in all possible directions
        			// (Generates paths recursively)
        			searchAroundHoriz(i, j, hp, opp);
        			searchAroundVert(i, j, vp, opp);
        			searchAroundDiagUp(i, j, dup, opp);
        			searchAroundDiagDown(i, j, ddp, opp);
        			
        			// Add all of these paths to the list of paths on this board
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        		}       		
        }          
    }
	
	// Get the total score for this board and return it
	Integer finalscore = scoreBoard(pathlist, player);
	return finalscore;
}

// Helper function to perform the actual board scoring
// Also orders the list of all paths on a board by their score
private Integer scoreBoard(LinkedList<Path> pathlist, String player) {
	Integer score = 0;
	
		// For each path...
        for(int j=0; j<pathlist.size(); j++) {

        	Integer tempscore = 0;
        	Path currpath = pathlist.get(j);
        	
        	// Paths of length 1 are worth very little
        	if (currpath.path.size() == 1){
            	if(currpath.player.equals(player)){
            		currpath.score = 10;
            		score += 10;
            	}
            	else{       		
            		// Opponents paths always have negative scores
            		currpath.score = -10;
            		score -= 10;
            	}
            	
            	// No additional checking needed
        		continue;
        	}
        	
        	// Ditto for paths of length 2
        	if (currpath.path.size() == 2){
            	if(currpath.player.equals(player)){
            		currpath.score = 50;
            		score += 50;
            	}
            	else{
            		currpath.score = -50;
            		score -= 50;
            	}
            	
            	// No additional checking needed
        		continue;
        	}
        	
        	// Paths of length 5 automatically win
        	if (currpath.path.size() == 5){
            	if(currpath.player.equals(player)){
            		currpath.score = 10000000;
            		score += 10000000;
            	}
            	else{
            		currpath.score = -10000000;
            		score -= 10000000;
            	}
            	
            	// No additional checking needed
        		continue;
        	}
        	
        	// Find the end cells in each path, and the cells just before/after
        	// Need this info to figure out where the "ends" of a path should be
    		Cell firstcell = currpath.path.getFirst();
    		Cell second = currpath.path.get(1);
    		Cell secondtolast = currpath.path.get(currpath.path.size() - 2);
    		Cell lastcell = currpath.path.getLast();
    		
    		// Figure out which direction to look
    		Integer diffi = secondtolast.vert - lastcell.vert;
    		Integer diffj = secondtolast.horiz - lastcell.horiz;
    		Integer newi = lastcell.vert - diffi;
    		Integer newj = lastcell.horiz - diffj;
    		
    		// Calculate the coordinates of the cells before/after the path
    		// Works in any direction
    		// (I'll call these boundaries)
    		Integer diffistart = second.vert - firstcell.vert;
    		Integer diffjstart = second.horiz - firstcell.horiz;
    		Integer newistart = firstcell.vert - diffistart;
    		Integer newjstart = firstcell.horiz - diffjstart;
    		
    		// If one of the boundaries is outside the bounds of the board,
    		// Just truncate it to the end of the actual path
    		if(newjstart > 14 || newjstart < 0){
    			newjstart = firstcell.horiz;
    		}
    		if(newistart > 14 || newistart < 0){
    			newistart = firstcell.vert;
    		}
    		if(newj > 14 || newj < 0){
    			newj = lastcell.horiz;
    		}
    		if(newi > 14 || newistart < 0){
    			newi = lastcell.vert;
    		}
   
        	// If both "boundary" cells are empty...
        	else if(cells[newi][newj].equals(" ") && cells[newistart][newjstart].equals(" ")){
        		// A path of size 4 is an automatic win
        		// (Cannot be blocked)
        		if (currpath.path.size() == 4){
        			currpath.score = 10000000;
        			tempscore += 10000000;
        		}
        		// A path of size 3 is okay
        		else if (currpath.path.size() == 3){
        			currpath.score = 1000;
        			tempscore += 1000;
        		}

        	}
    		
    		// If only one boundary is empty...
        	else if(cells[newi][newj].equals(" ")|| cells[newistart][newjstart].equals(" ")){
        		// A path of size 4 is good but not a guaranteed win
        		if (currpath.path.size() == 4){
        			currpath.score = 2000;
        			tempscore += 2000;
        		}
        		// A path of size 3 is okay
        		else if (currpath.path.size() == 3){
        			currpath.score = 500;
        			tempscore += 500;
        		}
        	}
    		
    		// (NOTICE WE DO NOT CARE ABOUT PATHS THAT ARE BLOCKED ON BOTH ENDS)
    		
    		// Flip the cardinality of the score if opponent
        	if(currpath.player.equals(player)){
        		score += tempscore;
        	}else{
        		score -= tempscore;
        		currpath.score = -currpath.score;
        	}
        }

        // Sort the path list in order of the path scores
    	Collections.sort(pathlist, new Comparator<Path>(){
 		   @Override
 		   public int compare(Path o1, Path o2){
 		        if(o1.score.compareTo(o2.score) < 0){
 		           return -1; 
 		        }
 		        if(o1.score.compareTo(o2.score) > 0){
 		           return 1; 
 		        }
 		        return 0;
 		   }
 		}); 
    	
    // Set this local pathlist to be this boards pathlist
    this.pl = pathlist;
        
    // Return the total score for the board
	return score;
}


private Integer searchAroundHoriz(int i, int j, Path p, String player) {
	Boolean visited = false;

	if (j > 0){
		if (cells[i][j-1]  == player){
			Cell c = new Cell(i,j - 1);

			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundHoriz(i, j-1, p, player);
			}
		}
	}
	if(j < 14){
		if (cells[i][j+1]  == player){
			Cell c = new Cell(i,j + 1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundHoriz(i, j+1, p, player);
			}
		}
	}
	
	return p.score;
}

private Integer searchAroundVert(int i, int j, Path p, String player) {
	Boolean visited = false;
	if (i > 0){
		if (cells[i-1][j]  == player){
			Cell c = new Cell(i-1,j);
			for (Cell c1 : p.path) {
				if(c1.horiz == j && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundVert(i-1, j, p, player);
			}
		}
	}
	if(i < 14){
		if (cells[i+1][j]  == player){
			Cell c = new Cell(i+1,j);
			for (Cell c1 : p.path) {
				if(c1.horiz == j && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundVert(i+1, j, p, player);
			}
		}
	}

	return p.score;

	}

private Integer searchAroundDiagDown(int i, int j, Path p, String player) {
	Boolean visited = false;
	if (i < 14 && j < 14){
		if (cells[i+1][j+1] == player){
			Cell c = new Cell(i+1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
			p.path.addFirst(c);
			searchAroundDiagDown(i+1, j+1, p, player);
			}
		}
	}
	if(i > 0 && j > 0){		
		if (cells[i-1][j-1]  == player){
			Cell c = new Cell(i-1,j-1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundDiagDown(i-1, j-1, p, player);
			}
		}
		
	}

	return p.score;

	}

private Integer searchAroundDiagUp(int i, int j, Path p, String player) {
	Boolean visited = false;

	if (j < 14 && i > 0){
		if (cells[i-1][j+1] == player){
			Cell c = new Cell(i-1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundDiagUp(i-1, j+1, p, player);
			}
		}
	}
	if(i < 14 && j > 0){
		if (cells[i+1][j-1] == player){
			Cell c = new Cell(i+1,j-1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
				p.path.addFirst(c);
				searchAroundDiagUp(i+1, j-1, p, player);
			}
		}
		
	}

	return p.score;

	}

}

