package TicTacToe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Board {
	
	int turn = 0;
	
	/*
	 * Input from file, Output board to console.
	 */
	public String[][] readBoardFromFile(String inputFileName) throws IOException{
		File inputFile = new File(inputFileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		String[][] board = new String[2][2];
		String inputLine;
		for(int line = 0; ((inputLine = bufferedReader.readLine()) != null); line++) {
			board = addInputLineToRow(line, board, inputLine);
		}
		bufferedReader.close();
		return board;
	}
	public String[][] addInputLineToRow (int row, String[][] boardAtRow, String inputLine){
		char aChar; int column = 0;
		for(int index = 0; index < inputLine.length();index= index+2) {
			aChar = inputLine.charAt(index);
			if(aChar == ' ') { boardAtRow[row][column] = null; column++;}
			else { boardAtRow[row][column] = Character.toString(aChar); column++;}
		}
		return boardAtRow;
	}
	// Output game board
	public void outputBoard(String[][] board) {
		int boardSize = board.length;
		String p;
		System.out.println("\n================\n");
		if (turn % 2 == 0) {
			p = "Your Turn\n";
		} else {
			p = ("Computer's Turn\n");
		}
		System.out.println("Round: " + turn/3 + "\nTurn:  "+ p);
		System.out.print("\nColumns\n");
		System.out.println("  1   2   3");
		System.out.print("-------------");
		System.out.println();
		for(int row = 0; row < boardSize; row++) {
			System.out.print("|");
			for(int column = 0; column < boardSize; column++) {
				if(board[row][column] == null) System.out.print("  "+" |");
				else System.out.print(" "+board[row][column] +" |");
			}
			
			System.out.println();
			System.out.print("-------------"); 
			System.out.println();
		}
		turn++;	
		System.out.print("               Rows\n\n"); 
	}
	
	public String[][] copyBoard(String[][] aBoard) {
		int boardSize = aBoard.length;
		String[][] newBoard = new String[boardSize][boardSize];
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) {
				newBoard[row][column] = aBoard[row][column];
			}
		}
		return newBoard;
	}
	public String[][] updateBoard(Node currentNode, int[] emptySquareOnBoard) {
		String[][] newBoard = this.copyBoard(currentNode.board);
		newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentNode.nextPlayer;
		return newBoard;
	}
	public int[] scanEmptySquareOnBoard(Node currentNode) {
		int boardSize = currentNode.board.length;
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) {
				if(currentNode.board[row][column] == null) {
					return addValueToArray(row, column);
				}
			}
		}
		return null;
	}
	public ArrayList<int[]> scanAllEmptySquareOnBoard(Node currentNode) {
		int boardSize = currentNode.board.length;
		ArrayList<int[]> anArrayList = new ArrayList<int[]>();
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) {
				if(currentNode.board[row][column] == null) anArrayList.add(addValueToArray(row, column));
			}
		}
		return anArrayList;
	}
	
	public int[] addValueToArray(int aNumber, int anotherNumber) {
		int[] anArray = new int[2];
		anArray[0] = aNumber;
		anArray[1] = anotherNumber;
		return anArray;
	}
	
	public Node getSuccessor(Node currentNode, int[] emptySquareOnBoard) {
		Node n = new Node();
		Board b = new Board();
		WinCondition w = new WinCondition();
		if(n.isLeafNode(currentNode) == true) return null;
		else {
			if(currentNode.nextPlayer=="X") {
				return new Node(b.updateBoard(currentNode,emptySquareOnBoard),currentNode,w.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,"O");
			}
			else return new Node(b.updateBoard(currentNode,emptySquareOnBoard),currentNode,w.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,"X");
		}
	}
	public Vector<Node> getAllSuccessors(Node currentNode) {
		Vector<Node> allSuccessors = new Vector<Node>();
		Board b = new Board();
		ArrayList<int[]> allEmptySquareOnBoard = b.scanAllEmptySquareOnBoard(currentNode);
		int numberOfEmptySquareOnBoard = allEmptySquareOnBoard.size();
		for(int i = 0; i < numberOfEmptySquareOnBoard; i++) { 
			allSuccessors.add(this.getSuccessor(currentNode, allEmptySquareOnBoard.get(i))); 
		}
		return allSuccessors;
	}
	

}
