package Eval;

import java.util.Collections;
import java.lang.Math;
import java.util.Comparator;
import java.util.LinkedList;

public class Board {
	
public String[][] cells = new String[15][15];


public Board()
{
	this.cells = cells;
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

public Integer eval()
{

	Integer horizcount = 0;
	Integer vertcount = 0;
	Integer diagupcount = 0;
	Integer diagdowncount = 0;
	LinkedList<Path> pathlist = new LinkedList<Path>();
	for(int i=0; i<cells.length; i++) {

        for(int j=0; j<cells[i].length; j++) {
        		if(cells[i][j].equals("O")){
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			Cell c = new Cell(i, j);
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			Path vp = new Path(0, vl, "vert", "O");
        			Path hp = new Path(0, hl, "horiz", "O");
        			Path dup = new Path(0, dul, "diagup", "O");
        			Path ddp = new Path(0, ddl, "diagdown", "O");

        			horizcount = searchAroundHoriz(i, j, hp, "O");
        			vertcount = searchAroundVert(i, j, vp, "O");
        			diagupcount = searchAroundDiagUp(i, j, dup, "O");
        			diagdowncount = searchAroundDiagDown(i, j, ddp, "O");
        			
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        		
        				
        		}
        		else if(cells[i][j].equals("X")){
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			Cell c = new Cell(i, j);
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			Path vp = new Path(0, vl, "vert", "X");
        			Path hp = new Path(0, hl, "horiz", "X");
        			Path dup = new Path(0, dul, "diagup", "X");
        			Path ddp = new Path(0, ddl, "diagdown", "X");

        			horizcount = searchAroundHoriz(i, j, hp, "X");
        			vertcount = searchAroundVert(i, j, vp, "X");
        			diagupcount = searchAroundDiagUp(i, j, dup, "X");
        			diagdowncount = searchAroundDiagDown(i, j, ddp, "X");
        			
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        	
        		}
        		
        }
        
        
    }
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
	
	Integer finalscore = scoreBoard(pathlist);
	
	return finalscore;
}

private Integer scoreBoard(LinkedList<Path> pathlist) {
	Integer score = 0;
        for(int j=0; j<pathlist.size(); j++) {
        	Integer tempscore = 0;
        	Path currpath = pathlist.get(j);
        	
        	if (currpath.path.size() == 1){
            	if(currpath.player.equals("O")){
            		score += 10;
            	}
            	else{
            		score -= 10;
            	}
        		continue;
        	}
        	if (currpath.path.size() == 2){
            	if(currpath.player.equals("O")){
            		score += 50;
            	}
            	else{
            		score -= 50;
            	}
        		continue;
        	}
    		Cell firstcell = currpath.path.getFirst();
    		Cell second = currpath.path.get(1);
    		Cell secondtolast = currpath.path.get(currpath.path.size() - 2);
    		Cell lastcell = currpath.path.getLast();
    		
    		Integer diffi = secondtolast.vert - lastcell.vert;
    		Integer diffj = secondtolast.horiz - lastcell.horiz;
    		Integer newi = lastcell.vert - diffi;
    		Integer newj = lastcell.horiz - diffj;
    		
    		Integer diffistart = second.vert - firstcell.vert;
    		Integer diffjstart = second.horiz - firstcell.horiz;
    		Integer newistart = firstcell.vert - diffistart;
    		Integer newjstart = firstcell.horiz - diffjstart;
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
    		
        	if(currpath.path.size() >= 5){
        		tempscore += 10000000;
        	}
        	
        	else if(cells[newi][newj].equals(" ") && cells[newistart][newjstart].equals(" ")){
        		if (currpath.path.size() == 4){
        			tempscore += 10000000;
        		}
        		else if (currpath.path.size() == 3){
        			tempscore += 1000;
        		}
        		else if (currpath.path.size() == 2){
        			tempscore += 200;
        		}

        	}
        	else if(cells[newi][newj].equals(" ")|| cells[newistart][newjstart].equals(" ")){
        		if (currpath.path.size() == 4){
        			tempscore += 2000;
        		}
        		else if (currpath.path.size() == 3){
        			tempscore += 500;
        		}
        		else if (currpath.path.size() == 2){
        			tempscore += 50;
        		}
        	}
        	if(currpath.player.equals("O")){
        		score += tempscore;
        	}
        	else{
        		score -= tempscore;
        	}
        }

	return score;
}


private Integer searchAroundHoriz(int i, int j, Path p, String player) {
	Boolean visited = false;
	
	//System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (j > 0){
		
		if (cells[i][j-1]  == player){
			Cell c = new Cell(i,j - 1);

			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i){
					visited = true;
				}
			}
			if(!visited){
				//System.out.println("ADDED NODE: " + (i) + "," + (j-1));
				p.score = p.score + 1;
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
				//System.out.println("ADDED NODE: " + (i) + "," + (j+1));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundHoriz(i, j+1, p, player);
			}
		}
	}
	
	return p.score;
}

private Integer searchAroundVert(int i, int j, Path p, String player) {
	Boolean visited = false;
	//System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i > 0){
		if (cells[i-1][j]  == player){
			Cell c = new Cell(i-1,j);
			for (Cell c1 : p.path) {
				if(c1.horiz == j && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				//System.out.println("ADDED NODE: " + (i-1) + "," + (j));
				p.score = p.score + 1;
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
				//System.out.println("ADDED NODE: " + (i+1) + "," + (j));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundVert(i+1, j, p, player);
			}
		}
	}

	return p.score;

	}

private Integer searchAroundDiagDown(int i, int j, Path p, String player) {
	Boolean visited = false;
	//System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i < 14 && j < 14){
		if (cells[i+1][j+1] == player){
			Cell c = new Cell(i+1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
			//System.out.println("ADDED NODE: " + (i+1) + "," + (j+1));
			p.score = p.score + 1;
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
				//System.out.println("ADDED NODE: " + (i-1) + "," + (j-1));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundDiagDown(i-1, j-1, p, player);
			}
		}
		
	}

	return p.score;

	}

private Integer searchAroundDiagUp(int i, int j, Path p, String player) {
	Boolean visited = false;
//	System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (j < 14 && i > 0){
		if (cells[i-1][j+1] == player){
			Cell c = new Cell(i-1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
			//	System.out.println("ADDED NODE: " + (i-1) + "," + (j+1));
				p.score = p.score + 1;
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
			//	System.out.println("ADDED NODE: " + (i+1) + "," + (j-1));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundDiagUp(i+1, j-1, p, player);
			}
		}
		
	}

	return p.score;

	}
private LinkedList<Board>generateChildren(String player){
	LinkedList<Board> children = new LinkedList<Board>();
	String[][] currcells = this.cells;
	for(int i=0; i<currcells.length; i++) {
        for(int j=0; j<currcells[i].length; j++) {
        	if(currcells[i][j] == null){
        		currcells[i][j] = player;
        		String[][] newcells = currcells;
        		Board cb = new Board();
        		children.add(cb);
        		currcells[i][j] = null;
        	}
        }
    }
	return children;
}

public Integer minimax(Board b, Integer depth, String player, Integer alpha, Integer beta) {
	Integer best = 0; //temp
	
	//check if this is a leaf
	if (depth == 2){
        return b.eval();
	}
    
    if (player == "O"){
        best = -200000000; // "negative infinity"
        LinkedList<Board> childBoards = b.generateChildren("O");
        for (Board cb : childBoards){
            Integer value = minimax(cb, depth+1, "X", alpha, beta);
            best = Math.max(best, value);
            alpha = Math.max(alpha, best);
            if (beta <= alpha){
            	break;
            }
        }
        return best;
    }
    else{
        best= 2000000; // "positive infinity"
        LinkedList<Board> childBoards = b.generateChildren("X");
        for (Board cb : childBoards){
            Integer value = minimax(cb, depth+1, "O", alpha, beta);
            best = Math.min( best, value);
            beta = Math.min( beta, value);
            if (beta <= alpha){
                break;
            }
        }
        return best;
    }
}	

}

