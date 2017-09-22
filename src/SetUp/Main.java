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
		Position pos = new Position(0, 0, 0);
		GomokuModel gm = new GomokuModel();
		MiniMax mmx = new MiniMax();
		gm.setBoard(2, "B", "X");
		gm.setBoard(2, "A", "O");
		gm.showBoard();
		pos = mmx.minimax(gm, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "X", "O", pos);
		gm.setBoard(pos.row + 1, gm.parseCol(pos.column + 1), "X");
		gm.setBoard(1, "C", "O");
		gm.showBoard();
		pos = mmx.minimax(gm, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "X", "O", pos);
		gm.setBoard(pos.row + 1, gm.parseCol(pos.column + 1), "X");
		gm.showBoard();


		try{
			
		URL url = SetUp.Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        System.out.println(parentPath);
        
        String sCurrentLine;
        String fileSeparator = System.getProperty("file.separator");
        String textFile = parentPath + fileSeparator + "end_game.txt";
        File endFile = new File(textFile);
        /*
        while(true){
	        //Check if end_game.txt exists
	        if(!endFile.exists()){
	        	// Check if there exists a .go file
	            LinkedList<String> goFiles = new LinkedList<String>();
	            File dir = new File(parentPath);
	            for (File file : dir.listFiles()) {
	              if (file.getName() == "b.go.txt") {
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
	     	        	if(mystone == null){
	     	        		mystone = "X";
	     	        	}
	     	        }
	     	        while ((sCurrentLine = br.readLine()) != null) {
	     	        	String[] token = sCurrentLine.split("\\s+");
	     	        	if(mystone.equals("X")){
	     	        		otherstone = "O";
	     	        	}else{
	     	        		otherstone = "X";
	     	        	}
	     	        	gm.setBoard(Integer.parseInt(token[2]), token[1], otherstone);
	     	        }
	     	        
	     	        
	            }else{
	            	Thread.sleep(TimeUnit.SECONDS.toMillis(10));
	            }
	        }else{
	        	break;
	        }
        }    
   */
		}catch(Exception e){
			
		}

	}

}
