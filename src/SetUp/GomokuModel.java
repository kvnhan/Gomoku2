package SetUp;

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
	
	public void setBoard(int Row, String Column, String stone){
		int newR = Row - 1;
		int newC = columns.get(Column) - 1;
		//TODO if stone is black, the it is 1, else 0
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
		
		System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
		for(int i = 0; i < 15; i++){
			for(int j = 0; j < 15; j++){
				b[i][j] = " ";
				if(j == 0){
					System.out.print("| " + b[i][j]);
				}else if(j == 14){
					System.out.print(" | " + b[i][j] + " |");
					System.out.println();
					System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
				}else{
					
					System.out.print(" | " + b[i][j]);
				}
				
				
			}
		}
		
	}
	

}
