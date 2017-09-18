package TicTacToe;
import java.util.Scanner;

public class Game {

	AlphaBetaPruning t = new AlphaBetaPruning();
	Node n = new Node();
	WinCondition w = new WinCondition();
	Board b = new Board();
	String draw = new String("\n-__- The game is draw\n");

	/*
	 * Game playing
	 */
	
	public int getPlayer(){
		Scanner player = new Scanner(System.in);
		System.out.print("Would you like to go first? Yes (1) No (0): ");

		return player.nextInt();
		
	}
	
	public void humanMove(Node currentNode) {
		Board b = new Board();
		Node newNode = new Node();
		int[] humanInput = getCorrectInputFromHumanMove(currentNode);
		newNode = b.getSuccessor(currentNode, humanInput);
		b.outputBoard(newNode.board);
		if (w.checkWin(newNode) == true) {
			System.out.println("\n *** Congratulations. You won! *** \n");
		}
		else if (n.isLeafNode(newNode) == true) {
			System.out.println(draw);
		}
		else
			machineMove(newNode);
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
		System.out.print("Select your turn (Row : Columns)\n   ROW #: "); 
		humanInput[0] = row.nextInt();
		System.out.print("COLUMN #: "); 
		humanInput[1] = column.nextInt();
		return modifyHumanInput(humanInput);
		
	}

	// TODO: Machine Win does not work; Machine does not want to choose winning condition
	public void machineMove(Node currentNode) {
		Node newNode = n.initializeNodeWithInput(currentNode.board);
		newNode = t.nextNodeToMove(newNode);
		b.outputBoard(newNode.board);
		if (w.checkWin(newNode) == true)
			System.out.println("\nXXX YOU LOST XXX\n");
		else if (n.isLeafNode(newNode) == true)
			System.out.println(draw);
		else
			humanMove(newNode);
	}

	public void gamePlay() {
		Node root = n.initializeNode();
		int player = getPlayer();
		b.outputBoard(root.board);
		
		
		if (player == 1) {
			root.nextPlayer = "O";
			humanMove(root);
		} else {
			root.nextPlayer = "X";
			machineMove(root);
		}
	}

}
