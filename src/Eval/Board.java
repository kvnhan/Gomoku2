package Eval;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Board {
	
String[][] cells = new String[5][5];


public Board()
{
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

public Integer eval(String player)
{
	Integer count = 0;
	Integer horizcount = 0;
	Integer vertcount = 0;
	Integer diagupcount = 0;
	Integer diagdowncount = 0;
	LinkedList<Path> pathlist = new LinkedList<Path>();
	for(int i=0; i<cells.length; i++) {
		System.out.println("");
        for(int j=0; j<cells[i].length; j++) {
        		if(player == cells[i][j]){
        			LinkedList<Cell> hl = new LinkedList<Cell>();
        			LinkedList<Cell> vl = new LinkedList<Cell>();
        			LinkedList<Cell> dul = new LinkedList<Cell>();
        			LinkedList<Cell> ddl = new LinkedList<Cell>();
        			Cell c = new Cell(i, j);
        			hl.add(c);
        			vl.add(c);
        			dul.add(c);
        			ddl.add(c);
        			Path vp = new Path(0, vl, "vert");
        			Path hp = new Path(0, hl, "horiz");
        			Path dup = new Path(0, dul, "diagup");
        			Path ddp = new Path(0, ddl, "diagdown");

        			horizcount = searchAroundHoriz(i, j, hp, player);
        			vertcount = searchAroundVert(i, j, vp, player);
        			diagupcount = searchAroundDiagUp(i, j, dup, player);
        			diagdowncount = searchAroundDiagDown(i, j, ddp, player);
        			
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
	System.out.println("SCORE = " + count);
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
	
	Integer finalscore = scoreBoard(pathlist, player);
	
	return finalscore;
}

private Integer scoreBoard(LinkedList<Path> pathlist, String player) {
	Integer score = 0;
        for(int j=0; j<pathlist.size(); j++) {
        	
        	Path currpath = pathlist.get(j);
        	
        	if (currpath.path.size() == 1){
        		score += 10;
        		continue;
        	}
        	if (currpath.path.size() == 2){
        		score += 50;
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
        		score += 10000000;
        	}
        	
        	else if(cells[newi][newj] == null && cells[newistart][newjstart] == null){
        		if (currpath.path.size() == 4){
            		score += 10000000;
        		}
        		else if (currpath.path.size() == 3){
            		score += 1000;
        		}
        		else if (currpath.path.size() == 2){
            		score += 200;
        		}

        	}
        	else if(cells[newi][newj] == null || cells[newistart][newjstart] == null){
        		if (currpath.path.size() == 4){
            		score += 2000;
        		}
        		else if (currpath.path.size() == 3){
            		score += 500;
        		}
        		else if (currpath.path.size() == 2){
            		score += 50;
        		}
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

//private Integer searchAround(int i, int j, Path p, String player) {
//	Boolean visited = false;
//	System.out.println("CURRENT NODE: " + (i) + "," + (j));
//
//	if (i < 14 && i > 0){
//		if (cells[i+1][j+1] == player){
//			Cell c = new Cell(i+1,j+1);
//			p.score = p.score + 1;
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j+1 && c1.vert == i+1){
//					visited = true;
//				}
//			}
//			if(!visited){
//			System.out.println("ADDED NODE: " + (i+1) + "," + (j+1));
//			p.path.addFirst(c);
//			searchAroundDiagDown(i+1, j+1, p, player);
//			}
//		}
//		else if (cells[i-1][j+1] == player){
//			Cell c = new Cell(i-1,j+1);
//			p.score = p.score + 1;
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j+1 && c1.vert == i-1){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i-1) + "," + (j+1));
//				p.path.addFirst(c);
//				searchAroundDiagUp(i-1, j+1, p, player);
//			}
//		}
//		else if (cells[i+1][j-1] == player){
//			Cell c = new Cell(i+1,j-1);
//			p.score = p.score + 1;
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j-1 && c1.vert == i+1){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i+1) + "," + (j-1));
//				p.path.addFirst(c);
//				searchAroundDiagUp(i+1, j-1, p, player);
//			}
//		}
//		else if (cells[i-1][j-1]  == player){
//			Cell c = new Cell(i-1,j-1);
//			p.score = p.score + 1;
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j-1 && c1.vert == i-1){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i-1) + "," + (j-1));
//				p.path.addFirst(c);
//				searchAroundDiagDown(i-1, j-1, p, player);
//			}
//		}
//		else if (cells[i][j-1]  == player){
//			Cell c = new Cell(i,j - 1);
//			p.score = p.score + 1;
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j-1 && c1.vert == i){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i) + "," + (j-1));
//				p.path.addFirst(c);
//				searchAroundHoriz(i, j-1, p, player);
//			}
//		}
//		else if (cells[i][j+1]  == player){
//			Cell c = new Cell(i,j + 1);
//			p.score = p.score + 1;	
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j+1 && c1.vert == i){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i) + "," + (j+1));
//				p.path.addFirst(c);
//				searchAroundHoriz(i, j+1, p, player);
//			}
//		}
//		else if (cells[i-1][j]  == player){
//			Cell c = new Cell(i-1,j);
//			p.score = p.score + 1;	
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j && c1.vert == i-1){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i-1) + "," + (j));
//				p.path.addFirst(c);
//				searchAroundVert(i-1, j, p, player);
//			}
//		}
//		else if (cells[i+1][j]  == player){
//			Cell c = new Cell(i+1,j);
//			p.score = p.score + 1;	
//			for (Cell c1 : p.path) {
//				if(c1.horiz == j && c1.vert == i+1){
//					visited = true;
//				}
//			}
//			if(!visited){
//				System.out.println("ADDED NODE: " + (i+1) + "," + (j));
//				p.path.addFirst(c);
//				searchAroundVert(i+1, j, p, player);
//			}
//		}
//	}
//
//	return p.score;
//
//	}


	
}

