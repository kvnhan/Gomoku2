package TicTacToe;


public class Main {
	
	public static void main(String args[]){
		Game g = new Game();
		System.out.println("Welcome to Tic-Tac-Toe\n");
		//String[][] aBoard = aTicTacToe.readBoardFromFile("/Users/khoapham/Dropbox/Developer/TicTacToe/Testcase/inputBoard_1.txt");
		g.gamePlay();
		System.out.close();
		main(args);
	}

}
