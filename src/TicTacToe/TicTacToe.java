package TicTacToe;

import java.io.*;
import java.util.*;


public class TicTacToe {
	
	public static int INFINITY = 100;
	public TicTacToe() {}
	public CheckWin c = new CheckWin();
	public Node n = new Node();
	

	/*
	 * Input from file, Output board to console.
	 */
	public String[][] readBoardFromFile(String inputFileName) throws IOException{
		File inputFile = new File(inputFileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		String[][] board = new String[3][3];
		String inputLine;
		for(int line = 0; ((inputLine = bufferedReader.readLine()) != null); line++) 
			board = this.addInputLineToRow(line, board, inputLine);
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
		System.out.println("Current Board is: ");
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
	}	
	//Check leaf node
	public boolean isLeafNode(Node currentNode) {
		return c.checkWin(currentNode) || (this.scanEmptySquareOnBoard(currentNode)==null);
	}
	// Only evaluate heuristicValue at leafNode.
	public int evaluateHeuristicValue(Node currentNode) {
		if(currentNode.nextPlayer == "X" && c.checkWin(currentNode)==true) return -1;
		if(currentNode.nextPlayer == "O" && c.checkWin(currentNode)==true) return 1;
		return 0;
	}
	
	public int[] scanEmptySquareOnBoard(Node currentNode) {
		int boardSize = currentNode.board.length;
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) {
				if(currentNode.board[row][column] == null) return this.addValueToArray(row, column);
			}
		}
		return null;
	}
	public ArrayList<int[]> scanAllEmptySquareOnBoard(Node currentNode) {
		int boardSize = currentNode.board.length;
		ArrayList<int[]> anArrayList = new ArrayList<int[]>();
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) {
				if(currentNode.board[row][column] == null) anArrayList.add(this.addValueToArray(row, column));
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
	public String[][] copyBoard(String[][] aBoard) {
		int boardSize = aBoard.length;
		String[][] newBoard = new String[boardSize][boardSize];
		for(int row = 0; row < boardSize; row++) {
			for(int column = 0; column < boardSize; column++) 
				newBoard[row][column] = aBoard[row][column];
		}
		return newBoard;
	}
	public String[][] updateBoard(Node currentNode, int[] emptySquareOnBoard) {
		String[][] newBoard = this.copyBoard(currentNode.board);
		newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentNode.nextPlayer;
		return newBoard;
	}
	public Node getSuccessor(Node currentNode, int[] emptySquareOnBoard) {
		if(this.isLeafNode(currentNode) == true) return null;
		else {
			if(currentNode.nextPlayer=="X") return new Node(this.updateBoard(currentNode,emptySquareOnBoard),currentNode,this.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,"O");
			else return new Node(this.updateBoard(currentNode,emptySquareOnBoard),currentNode,this.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,"X");
		}
	}
	public Vector<Node> getAllSuccessors(Node currentNode) {
		Vector<Node> allSuccessors = new Vector<Node>();
		ArrayList<int[]> allEmptySquareOnBoard = this.scanAllEmptySquareOnBoard(currentNode);
		int numberOfEmptySquareOnBoard = allEmptySquareOnBoard.size();
		for(int i = 0; i < numberOfEmptySquareOnBoard; i++) { 
			allSuccessors.add(this.getSuccessor(currentNode, allEmptySquareOnBoard.get(i))); 
		}
		return allSuccessors;
	}
	/*
	 * Get max/min.
	 */
	public int getMaxTwoIntegers(int anInteger, int anotherInteger) {
		if(anInteger < anotherInteger) return anotherInteger;
		else return anInteger;
	}
	public int getMinTwoIntegers(int anInteger, int anotherInteger) {
		if(anInteger > anotherInteger) return anotherInteger;
		else return anInteger;
	}
	public Node getMinNodeInList(Vector<Node> aVectorNode) {
		Node minNode = aVectorNode.get(0);
		int listSize = aVectorNode.size();
		for(int index = 0; index < listSize; index++) 
			if(minNode.heuristicValue > aVectorNode.get(index).heuristicValue) minNode = aVectorNode.get(index);
		return minNode;
	}
	public Node getMaxNodeInList(Vector<Node> aVectorNode) {
		Node maxNode = aVectorNode.get(0);
		int listSize = aVectorNode.size();
		for(int index = 0; index < listSize; index++) 
			if(maxNode.heuristicValue < aVectorNode.get(index).heuristicValue) maxNode = aVectorNode.get(index);
		return maxNode;
	}
	/*
	 * Minimax
	 */
	Vector<Node> possibleNextMoveNodes = new Vector<Node>();
	public int getMinimax(Node currentNode) {
		if(this.isLeafNode(currentNode)==true) return this.miniMaxLeafNode(currentNode);
		else return this.miniMaxNonLeafNode(currentNode);
	}
	public int miniMaxNonLeafNode(Node currentNode) {
		Vector<Node> allSuccessors = this.getAllSuccessors(currentNode);
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			if(currentNode.nextPlayer == "O") currentNode.heuristicValue = this.getMinTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));
			else currentNode.heuristicValue = this.getMaxTwoIntegers(currentNode.heuristicValue, this.getMinimax(aSuccessor));	
		}
		if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return currentNode.heuristicValue;
	}
	public int miniMaxLeafNode(Node currentNode){
		if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return this.evaluateHeuristicValue(currentNode);
	}
	
	/*
	 * Alpha-beta pruning.
	 */
	public int initializeAlpha(Node currentNode) {
		if(this.isLeafNode(currentNode)==true) return this.evaluateHeuristicValue(currentNode);
		else return -INFINITY;
	}
	public int initializeBeta(Node currentNode) {
		if(this.isLeafNode(currentNode)==true) return this.evaluateHeuristicValue(currentNode);
		else return INFINITY;
	}
	public int minimaxAlphaBetaPruning(Node currentNode, int alpha, int beta) {
		int alphaOfCurrentNode = alpha; int betaOfCurrentNode = beta;
		if (this.isLeafNode(currentNode)==true)  return this.miniMaxLeafNode(currentNode);
		else if (currentNode.nextPlayer == "O") return this.minimaxAlpha_CurrentMaxNode(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
		else return this.minimaxBeta_CurrentMinNode(currentNode, alphaOfCurrentNode, betaOfCurrentNode);
	}
	public int minimaxAlpha_CurrentMaxNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
		Vector<Node> allSuccessors = this.getAllSuccessors(currentNode);
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			int currentMin = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
			betaOfCurrentNode = this.getMinTwoIntegers(betaOfCurrentNode, currentMin);
			currentNode.heuristicValue = this.getMinTwoIntegers(currentNode.heuristicValue, betaOfCurrentNode);
			if(alphaOfCurrentNode >= betaOfCurrentNode) break;
		}
		if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return betaOfCurrentNode;	
	}
	public int minimaxBeta_CurrentMinNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
		Vector<Node> allSuccessors = this.getAllSuccessors(currentNode);
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			int currentMax = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
			alphaOfCurrentNode = this.getMaxTwoIntegers(alphaOfCurrentNode, currentMax);
			currentNode.heuristicValue = this.getMaxTwoIntegers(currentNode.heuristicValue,alphaOfCurrentNode);
			if(alphaOfCurrentNode >= betaOfCurrentNode) break;
		}
		if(this.possibleNextMoveNodes(currentNode)!=null) possibleNextMoveNodes.add(currentNode);
		return alphaOfCurrentNode;
	}
	
	// Get next move
	public Node possibleNextMoveNodes (Node currentNode) {
		if(currentNode.atDepth == 1) return currentNode;
		else return null;
	}
	public Node nextNodeToMove(Node currentNode) {
		//this.getMinimax(currentNode);		// Change this to see how different between two algorithms.
		this.minimaxAlphaBetaPruning(currentNode, this.initializeAlpha(currentNode), this.initializeBeta(currentNode));
		Node newNode = this.getMaxNodeInList(possibleNextMoveNodes);
		possibleNextMoveNodes.removeAllElements();
		return newNode;
	}	
	/*
	 * Game playing
	 */
	public void humanMove(Node currentNode) {
		Node newNode = new Node();
		int[] humanInput = this.getCorrectInputFromHumanMove(currentNode);
		newNode = this.getSuccessor(currentNode, humanInput);
		this.outputBoard(newNode.board);
		if(c.checkWin(newNode)==true) System.out.println("Congratulations. You won!");
		else if(this.isLeafNode(newNode) == true)  System.out.println("The game is draw");
		else this.machineMove(newNode);
	}
	public void machineMove(Node currentNode) {
		Node newNode = n.initializeNodeWithInput(currentNode.board);
		newNode = this.nextNodeToMove(newNode);
		this.outputBoard(newNode.board);
		if(c.checkWin(newNode) == true) System.out.println("Computer won!");
		else if(this.isLeafNode(newNode) == true)  System.out.println("The game is draw");
		else this.humanMove(newNode);
	}
	public void gamePlay() {
		Node root = n.initializeNode();
		this.outputBoard(root.board);
		int player = this.getPlayer();
		if(player == 1) { root.nextPlayer = "O"; this.humanMove(root); }
		else { root.nextPlayer = "X"; this.machineMove(root); }
	}
	/*
	 * Extra-method
	 */
	public int getPlayer(){
		Scanner player = new Scanner(System.in);
		System.out.print("Do you want to play first? Yes (1). No (0): ");
		return player.nextInt();
	}
	public int[] getCorrectInputFromHumanMove(Node currentNode) {
		int[] humanInput = this.readHumanInput();
		while(humanInput[0] >= 3 || humanInput[0] < 0
				|| humanInput[1] >= 3 || humanInput[1] < 0
				|| this.checkEmptySquareFromHumanInput(currentNode, humanInput)==false) {
			System.out.println("Sorry. Your move is not correct.");
			humanInput = this.readHumanInput();
		}
		return humanInput;
	}
	public boolean checkEmptySquareFromHumanInput(Node currentNode, int[] humanInput) {
		return currentNode.board[humanInput[0]][humanInput[1]] == null;
	}
	public int[] modifyHumanInput(int[] humanInput) {
		int[] returnFromHumanInput = new int[2];
		returnFromHumanInput[0] = humanInput[0]-1;
		returnFromHumanInput[1] = humanInput[1]-1;
		return returnFromHumanInput;
	}
	public int[] readHumanInput() {
		int[] humanInput = new int[2];
		Scanner row = new Scanner(System.in); Scanner column = new Scanner(System.in);
		System.out.print("Enter which row you want to check: "); 
		humanInput[0] = row.nextInt();
		System.out.print("Please enter column you want to check: "); 
		humanInput[1] = column.nextInt();
		return this.modifyHumanInput(humanInput);
	}
	
	public static void main(String args[]) throws IOException {
		TicTacToe aTicTacToe = new TicTacToe();
		//String[][] aBoard = aTicTacToe.readBoardFromFile("/Users/khoapham/Dropbox/Developer/TicTacToe/Testcase/inputBoard_1.txt");
		aTicTacToe.gamePlay();
	}
}