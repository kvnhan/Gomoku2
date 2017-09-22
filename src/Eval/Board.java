package Eval;

import java.util.Collections;
import java.lang.Math;
import java.util.Comparator;
import java.util.LinkedList;

public class Board {
	
String[][] cells = new String[5][5];


public Board(String[][] cells)
{
	this.cells = cells;
}

public void initBoard()
{
	for(int i=0; i<cells.length; i++) {
		System.out.println("");
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
		System.out.println("");
        for(int j=0; j<cells[i].length; j++) {
        		if("White" == cells[i][j]){
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			Cell c = new Cell(i, j);
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			Path vp = new Path(0, vl, "vert", "White");
        			Path hp = new Path(0, hl, "horiz", "White");
        			Path dup = new Path(0, dul, "diagup", "White");
        			Path ddp = new Path(0, ddl, "diagdown", "White");

        			horizcount = searchAroundHoriz(i, j, hp, "White");
        			vertcount = searchAroundVert(i, j, vp, "White");
        			diagupcount = searchAroundDiagUp(i, j, dup, "White");
        			diagdowncount = searchAroundDiagDown(i, j, ddp, "White");
        			
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        			
        			System.out.println("SCORE! = " + horizcount);
        			System.out.println("VERTSCORE! = " + vertcount);
        			System.out.println("UPSCORE! = " + diagupcount);
        			System.out.println("DOWNSCORE! = " + diagdowncount);
        				
        		}
        		else if("Black" == cells[i][j]){
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			Cell c = new Cell(i, j);
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			Path vp = new Path(0, vl, "vert", "Black");
        			Path hp = new Path(0, hl, "horiz", "Black");
        			Path dup = new Path(0, dul, "diagup", "Black");
        			Path ddp = new Path(0, ddl, "diagdown", "Black");

        			horizcount = searchAroundHoriz(i, j, hp, "Black");
        			vertcount = searchAroundVert(i, j, vp, "Black");
        			diagupcount = searchAroundDiagUp(i, j, dup, "Black");
        			diagdowncount = searchAroundDiagDown(i, j, ddp, "Black");
        			
        			pathlist.add(vp);
        			pathlist.add(hp);
        			pathlist.add(dup);
        			pathlist.add(ddp);
        			
        			System.out.println("SCORE! = " + horizcount);
        			System.out.println("VERTSCORE! = " + vertcount);
        			System.out.println("UPSCORE! = " + diagupcount);
        			System.out.println("DOWNSCORE! = " + diagdowncount);
        				
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
        		tempscore += 10;
        		continue;
        	}
        	if (currpath.path.size() == 2){
        		tempscore += 50;
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
    		if(newjstart > 4){
    			newjstart = firstcell.horiz;
    		}
    		if(newistart > 4){
    			newistart = firstcell.vert;
    		}
    		if(newj > 4){
    			newj = lastcell.horiz;
    		}
    		if(newi > 4){
    			newi = lastcell.vert;
    		}
    		
        	if(currpath.path.size() >= 5){
        		tempscore += 10000000;
        	}
        	
        	else if(cells[newi][newj] == null && cells[newistart][newjstart] == null){
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
        	else if(cells[newi][newj] == null || cells[newistart][newjstart] == null){
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
        	if(currpath.player == "White"){
        		score += tempscore;
        	}
        	else{
        		score -= tempscore;
        	}
        }
        System.out.println("FINALSCORE: " + score );
	return score;
}

private Integer searchAroundHoriz(int i, int j, Path p, String player) {
	Boolean visited = false;
	
	System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i < 14 && i > 0){
		
		if (cells[i][j-1]  == player){
			Cell c = new Cell(i,j - 1);

			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i) + "," + (j-1));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundHoriz(i, j-1, p, player);
			}
		}
		else if (cells[i][j+1]  == player){
			Cell c = new Cell(i,j + 1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i) + "," + (j+1));
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
	System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i < 14 && i > 0){
		if (cells[i-1][j]  == player){
			Cell c = new Cell(i-1,j);
			for (Cell c1 : p.path) {
				if(c1.horiz == j && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i-1) + "," + (j));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundVert(i-1, j, p, player);
			}
		}
		else if (cells[i+1][j]  == player){
			Cell c = new Cell(i+1,j);
			for (Cell c1 : p.path) {
				if(c1.horiz == j && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i+1) + "," + (j));
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
	System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i < 14 && i > 0){
		if (cells[i+1][j+1] == player){
			Cell c = new Cell(i+1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
			System.out.println("ADDED NODE: " + (i+1) + "," + (j+1));
			p.score = p.score + 1;
			p.path.addFirst(c);
			searchAroundDiagDown(i+1, j+1, p, player);
			}
		}
		
		else if (cells[i-1][j-1]  == player){
			Cell c = new Cell(i-1,j-1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i-1) + "," + (j-1));
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
	System.out.println("CURRENT NODE: " + (i) + "," + (j));
	if (i < 14 && i > 0){
		if (cells[i-1][j+1] == player){
			Cell c = new Cell(i-1,j+1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j+1 && c1.vert == i-1){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i-1) + "," + (j+1));
				p.score = p.score + 1;
				p.path.addFirst(c);
				searchAroundDiagUp(i-1, j+1, p, player);
			}
		}
		else if (cells[i+1][j-1] == player){
			Cell c = new Cell(i+1,j-1);
			for (Cell c1 : p.path) {
				if(c1.horiz == j-1 && c1.vert == i+1){
					visited = true;
				}
			}
			if(!visited){
				System.out.println("ADDED NODE: " + (i+1) + "," + (j-1));
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
	for(int i=0; i<cells.length; i++) {
        for(int j=0; j<cells[i].length; j++) {
        	if(cells[i][j] == null){
        		cells[i][j] = player;
        		Board b = new Board(cells);
        		children.add(b);
        		cells[i][j] = null;
        	}
        }
    }
	return children;
}

private Integer minimax(Board b, Integer depth, String player, Integer alpha, Integer beta) {
	Integer best = 0; //temp
	
	//check if this is a leaf
	if (depth == 2){
        return b.eval();
	}
    
    if (player == "White"){
        best = -200000000; // "negative infinity"
        LinkedList<Board> childBoards = b.generateChildren("White");
        for (Board cb : childBoards){
            Integer value = minimax(cb, depth+1, "White", alpha, beta);
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
        LinkedList<Board> childBoards = b.generateChildren("Black");
        for (Board cb : childBoards){
            Integer value = minimax(cb, depth+1, "Black", alpha, beta);
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

