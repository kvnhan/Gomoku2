package SetUp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;

public class MakeMove {
	
	static String mystone = null;
	static String otherstone = null;
	static boolean went = false;
	
	void play(Position pos, MiniMax mmx, GomokuModel gm){
		
		FileReader fr = null;
		BufferedReader br = null;
		LinkedList<Position> lastMoves = new LinkedList<Position>();


		try{
			
		URL url = SetUp.Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        System.out.println(parentPath);
        
        String sCurrentLine;
        String fileSeparator = System.getProperty("file.separator");
        String textFile = parentPath + fileSeparator + "end_game";
        File endFile = new File(textFile);
        
        while(true){
	        //Check if end_game.txt exists
	        if(!endFile.exists()){
	        	// Check if there exists a .go file
	            LinkedList<String> goFiles = new LinkedList<String>();
	            File dir = new File(parentPath);
	            for (File file : dir.listFiles()) {
	              if (file.getName().equals("metzkhanwilderV2.go")) {
	                goFiles.add(file.getName());
	              }
	            }
	            
	            if(!goFiles.isEmpty()){
	            	//Read move_file.txt
		            if(went == false){
		            	textFile = parentPath + fileSeparator + "move_file";
		            	try {
		     				fr = new FileReader(textFile);
		     				br = new BufferedReader(fr);
		     				sCurrentLine = br.readLine();
			     	       	if(sCurrentLine != null) {
			     	       		if(mystone == null){
			     	       			mystone = "X";
			     	       		}
			     	        	String[] token = sCurrentLine.split("\\s+");
			     	        	if(mystone.equals("X")){
			     	        		otherstone = "O";
			     	        	}else{
			     	        		otherstone = "X";
			     	        	}
			     	        	pos.g.setBoard(Integer.parseInt(token[2]), token[1], otherstone);
			     	        	Position lastM = new Position(Integer.parseInt(token[2]) - 1, pos.g.convert(token[1]), 0.0, otherstone, pos.g);
			     	        	lastMoves.addFirst(lastM);
			     	        	pos = mmx.minimax(pos.g, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, mystone, false, lastMoves);
			     	        	pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), mystone);
			     	        	pos.g.showBoard(mystone, "metzkhanwilderV2");
			     	        	lastMoves.addFirst(pos);
			     	        	String move = "metzkhanwilderV2 " + pos.g.parseCol(pos.column + 1) + " " + (pos.row + 1);
			     	        	pos.g.writeFile(textFile, move);
			     	        	went = true;
			     	        	continue;
			     	        }else{
				     	        mystone = "O";
			    	        	String move = "metzkhanwilderV2 H 7";
			    	        	pos.g.writeFile(textFile, move);
			    	        	pos.g.setBoard(7,"H", mystone);
			    	        	pos.g.showBoard(mystone, "metzkhanwilderV2");
			    	        	went = true;
			    	        	continue;
			     	        }
		     			} catch (Exception e) {
		     				if(fr == null){
		     					System.out.println("Sorry, no such file");
		     				}
		     			} finally {
		     			    if (fr != null) {
		     			        try {
		     			            fr.close();
		     			        } catch (IOException e) {
		     			            e.printStackTrace();
		     			        }
		     			    }
		     			}
		            	
		            }	            	     	     
	            }else{
	            	went = false;
	            }
	            
	        }else{
	        	FileReader f = null;
	    		BufferedReader b = null;
	            String winner = parentPath + fileSeparator + "end_game";
	    		try {
     				f = new FileReader(winner);
     				b = new BufferedReader(f);
     				sCurrentLine = b.readLine();
     				String[] tkn = sCurrentLine.split("\\s+");
     				if(tkn[1].equals("metzkhanwilderV2")){
     					System.out.println("I Win.");
     				}else{
     					pos.g.showBoard(mystone, "metzkhanwilderV2");
     					System.out.println("I Lose.");
     				}
     				f.close();
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    		
	        	System.out.println("Good Game");
	        	
	        	break;
	        	
	        }
        }   
   
		}catch(Exception e){
			
		}
	}
	
	

}
