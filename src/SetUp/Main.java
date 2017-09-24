package SetUp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {

	static String mystone = null;
	static String otherstone = null;
	static boolean went = false;

	public static void main(String[] args) {
		
		FileReader fr = null;
		BufferedReader br = null;
		GomokuModel gm = new GomokuModel();
		MiniMax mmx = new MiniMax();
		Position pos = new Position(0, 0, 0, " ", gm);
		pos.g.setBoard(7, "G", "X");
		pos.g.setBoard(5, "E", "X");
		pos.g.setBoard(5, "F", "X");
		pos.g.setBoard(4, "D", "X");
		pos.g.setBoard(4, "C", "O");
		pos.g.setBoard(2, "H", "O");
		pos.g.setBoard(8, "B", "O");
		pos.g.showBoard();
		pos = mmx.minimax(pos.g, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "O", false);
		pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), "O");
		pos.g.showBoard();
	
		try{
			
		URL url = SetUp.Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        System.out.println(parentPath);
        
        String sCurrentLine;
        String fileSeparator = System.getProperty("file.separator");
        String textFile = parentPath + fileSeparator + "end_game";
        File endFile = new File(textFile);
        /*
        while(true){
	        //Check if end_game.txt exists
	        if(!endFile.exists()){
	        	// Check if there exists a .go file
	            LinkedList<String> goFiles = new LinkedList<String>();
	            File dir = new File(parentPath);
	            for (File file : dir.listFiles()) {
	              if (file.getName().equals("bomb.go")) {
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
			     	    	   if(sCurrentLine.equals("")){
			     	    		   	mystone = "O";
				    	        	String move = "bomb H 7";
				    	        	pos.g.writeFile(textFile, move);
				    	        	pos.g.setBoard(7,"H", mystone);
				    	        	pos.g.showBoard();
				    	        	went = true;
				    	        	continue;
			     	    	   }
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
			     	        	pos = mmx.minimax(pos.g, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, mystone, false);
			     	        	pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), mystone);
			     	        	pos.g.showBoard();
			     	        	String move = "bomb " + pos.g.parseCol(pos.column + 1) + " " + (pos.row);
			     	        	pos.g.writeFile(textFile, move);
			     	        	went = true;
			     	        	continue;
			     	        }else{
				     	        mystone = "O";
			    	        	String move = "bomb H 7";
			    	        	pos.g.writeFile(textFile, move);
			    	        	pos.g.setBoard(7,"H", mystone);
			    	        	pos.g.showBoard();
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
	        	break;
	        }
        }   */ 
   
		}catch(Exception e){
			
		}

	}

}
