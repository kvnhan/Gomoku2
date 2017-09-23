package SetUp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {

	static String mystone = null;
	static String otherstone = null;


	public static void main(String[] args) {
		
		FileReader fr = null;
		BufferedReader br = null;
		GomokuModel gm = new GomokuModel();
		MiniMax mmx = new MiniMax();
		Position pos = new Position(0, 0, 0, " ", gm);
	
		try{
			
		URL url = SetUp.Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        System.out.println(parentPath);
        
        String sCurrentLine;
        String fileSeparator = System.getProperty("file.separator");
        String textFile = parentPath + fileSeparator + "end_game.txt";
        File endFile = new File(textFile);
        
        while(true){
	        //Check if end_game.txt exists
	        if(!endFile.exists()){
	        	// Check if there exists a .go file
	            LinkedList<String> goFiles = new LinkedList<String>();
	            File dir = new File(parentPath);
	            for (File file : dir.listFiles()) {
	              if (file.getName() == "bomb.go.txt") {
	                goFiles.add(file.getName());
	              }
	            }
	            //Read move_file.txt
	            if(!goFiles.isEmpty()){
	            	textFile = parentPath + fileSeparator + "move_file.txt";
	            	try {
	     				fr = new FileReader(textFile);
	     				br = new BufferedReader(fr);
	     	
	     			} catch (Exception e) {
	     				System.out.println("Sorry, no such file");
	     			}
	     	        if((sCurrentLine = br.readLine()) == null){
	     	        	mystone = "X";	     	
	     	        }else{
	     	        	mystone = "O";
	     	        }
	     	        while ((sCurrentLine = br.readLine()) != null) {
	     	        	String[] token = sCurrentLine.split("\\s+");
	     	        	if(mystone.equals("X")){
	     	        		otherstone = "O";
	     	        	}else{
	     	        		otherstone = "X";
	     	        	}
	     	        	pos.g.setBoard(Integer.parseInt(token[2]), token[1], otherstone);
	     	        	pos = mmx.minimax(pos.g, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, mystone, otherstone, false);
	     	        	pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), mystone);
	     	        	pos.g.showBoard();
	     	        	String move = "bomb " + pos.g.parseCol(pos.column + 1) + " " + pos.row ;
	     	        	pos.g.writeFile(textFile, move);
	     	        }
	     	        
	     	        
	            }
	        }else{
	        	break;
	        }
        }    
   
		}catch(Exception e){
			
		}

	}

}
