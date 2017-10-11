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
		
		GomokuModel gm = new GomokuModel();
		Position pos = new Position(0, 0, 0, " ", gm);
		MiniMax mmx = new MiniMax();
		
		Testing test = new Testing();
		TitlePage t = new TitlePage();
		MakeMove m = new MakeMove();
		
		
		t.getTitle();
		
		//test.startBoard(pos, mmx, gm);
	
     	
     	m.play(pos, mmx, gm);
     	t.getTitle();
     	t.getEnd();

	}
}