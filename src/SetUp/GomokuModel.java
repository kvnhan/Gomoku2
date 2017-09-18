package SetUp;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class GomokuModel {
	
	String[][] b = new String[15][15];
	HashMap<String, Integer> columns = new HashMap<String, Integer>();
	
	public GomokuModel(){}
	
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
	// Set the board
	public void setBoard(int Row, String Column, String stone){

		setUpCol();
		int newR = Row - 1;
		int newC = columns.get(Column) - 1;
		//TODO: if stone is black, the it is X, else O
		String player = "X";
		for(int i = 0; i < 15; i++){
			if(i == newR){
				for(int j = 0; j < 15; j++){
					if(j == newC){
						b[i][j] = player;
					}
				}
			}
		}
		
	}
	
	public void showBoard(){
		String rows = "+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+";
		System.out.println(rows);
		for(int i = 0; i < 15; i++){
			for(int j = 0; j < 15; j++){
				b[i][j] = " ";
				if(j == 0){
					System.out.print("| " + b[i][j]);
				}else if(j == 14){
					System.out.print(" | " + b[i][j] + " |");
					System.out.println();
					System.out.println(rows);
				}else{
					
					System.out.print(" | " + b[i][j]);
				}
				
				
			}
		}
		
	}
	

}
