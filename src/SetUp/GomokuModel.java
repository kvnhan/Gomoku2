package SetUp;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class GomokuModel {
	public int n = 3;
	public String[][] b = new String[n][n];
	HashMap<String, Integer> columns = new HashMap<String, Integer>();
	
	public GomokuModel(){
		
	}
	
	public void setUpCol(){
		columns.put("A", 1);
		columns.put("B", 2);
		columns.put("C", 3);
		columns.put("D", 4);
		columns.put("E", 5);
		columns.put("F", 6);
		columns.put("G", 7);
		columns.put("H", 8);
		columns.put("I", 9);
		columns.put("J", 10);
		columns.put("K", 11);
		columns.put("L", 12);
		columns.put("M", 13);
		columns.put("N", 14);
		columns.put("O", 15);
	}
	
	//Write to move_file 
	public void writeFile(String path, String move){
		try { 
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
		    out.write(move);
		    out.flush();
		    out.close();
		  }
		  catch (IOException e) {  
		  }
		
		
	}
	public String parseCol(int col){
		String c = null;
		setUpCol();
		for(String s: columns.keySet()){
			if(columns.get(s) == col){
				c = s;
				return c;
			}
		}
		return c;
	}
	public void makeMove(int h, int v, String value){
		b[h][v] = value;
	}
	
	// Set the board
	public void setBoard(int Row, String Column, String stone){
		setUpCol();
		int newR = Row - 1;
		int newC = columns.get(Column) - 1;
		for(int i = 0; i < n; i++){
			if(i == newR){
				for(int j = 0; j < n; j++){
					if(j == newC){
						b[i][j] = stone;
					}
				}
			}
		}
		
	}
	
	public void showBoard(){
		String rows = "+---+---+---+";
		//String rows = "+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+";
		System.out.println(rows);
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(b[i][j] == null){
					b[i][j] = " ";
				}
				if(j == 0){
					System.out.print("| " + b[i][j]);
				}else if(j == n - 1){
					System.out.print(" | " + b[i][j] + " |");
					System.out.println();
					System.out.println(rows);
				}else{
					
					System.out.print(" | " + b[i][j]);
				}
				
				
			}
		}
		
	}
	
	public HashMap<Position, String> getEmptySpaces(){
		HashMap<Position, String> empty = new HashMap<Position, String>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (b[i][j].equals(" ")) {
					Position p = new Position(i,j, 0, " ", this);
					empty.put(p, "empty");
				}
			}
		}
		
		return empty;
	}
	
	public HashMap<Position, String> getPlayerPiece(String stone){
		HashMap<Position, String> p = new HashMap<Position, String>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (b[i][j].equals(stone)) {
					Position pos = new Position(i,j, 0, stone, this);
					p.put(pos, stone);
				}
			}
		}
		return p;
	}

	
	public HashMap<Position, String> lookAround(Position p){
		HashMap<Position, String> empty = new HashMap<Position, String>();
		if(p.row - 1 >= 0){
			if(b[p.row - 1][p.column].equals(" ")){
				Position pos = new Position(p.row - 1,p.column, 0, p.stone, this);
				empty.put(pos, "empty");
			}
			if (p.column - 1 >= 0) {
				if (b[p.row - 1][p.column - 1].equals(" ")) {
					Position pos = new Position(p.row - 1,p.column - 1, 0, p.stone, this);
					empty.put(pos, "empty");
				}
			}
		}
		if (p.column + 1 < n) {
			if (b[p.row][p.column + 1].equals(" ")) {
				Position pos = new Position(p.row,p.column + 1, 0, p.stone, this);
				empty.put(pos, "empty");
			}
			if (p.row - 1 >= 0) {
				if (b[p.row - 1][p.column + 1].equals(" ")) {
					Position pos = new Position(p.row - 1,p.column + 1, 0, p.stone, this);
					empty.put(pos, "empty");
				}
			}
		}
		if (p.row + 1 < n) {
			if (b[p.row + 1][p.column].equals(" ")) {
				Position pos = new Position(p.row + 1,p.column, 0, p.stone, this);
				empty.put(pos, "empty");
			}
			if (p.column + 1 < n) {
				if (b[p.row + 1][p.column + 1].equals(" ")) {
					Position pos = new Position(p.row + 1,p.column + 1, 0, p.stone, this);
					empty.put(pos, "empty");
				}
			}
		}
		return empty;
	}
	

}
