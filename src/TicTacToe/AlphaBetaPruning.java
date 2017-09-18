package TicTacToe;

import java.util.*;


public class AlphaBetaPruning {
	
	public static int INFINITY = 100;
	public AlphaBetaPruning() {}
	Vector<Node> possibleNextMoveNodes = new Vector<Node>();
	public static Board b = new Board();
	public static Node n = new Node();
	public static WinCondition w = new WinCondition();
	public static Minimax m = new Minimax();

	public int initializeAlpha(Node currentNode) {
		if(n.isLeafNode(currentNode)==true) {
			return w.evaluateHeuristicValue(currentNode);
		}
		else return -INFINITY;
	}
	public int initializeBeta(Node currentNode) {
		if(n.isLeafNode(currentNode)==true) {
			return w.evaluateHeuristicValue(currentNode);
		}
		else return INFINITY;
	}
	public int minimaxAlphaBetaPruning(Node currentNode, int alpha, int beta) {
		if (n.isLeafNode(currentNode)==true) {
			return m.miniMaxLeafNode(currentNode);
		}
		else if (currentNode.nextPlayer == "O") {
			return this.minimaxAlpha_CurrentMaxNode(currentNode, alpha, beta);
		}
		else return this.minimaxBeta_CurrentMinNode(currentNode, alpha, beta);
	}
	public int minimaxAlpha_CurrentMaxNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
	
		Vector<Node> allSuccessors = b.getAllSuccessors(currentNode);
		
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			int currentMin = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
			betaOfCurrentNode = n.getMinTwoIntegers(betaOfCurrentNode, currentMin);
			currentNode.heuristicValue = n.getMinTwoIntegers(currentNode.heuristicValue, betaOfCurrentNode);
			if(alphaOfCurrentNode >= betaOfCurrentNode) {
				break;
			}
		}
		if(n.possibleNextMoveNodes(currentNode)!=null) {
			possibleNextMoveNodes.add(currentNode);
		}
		return betaOfCurrentNode;	
	}
	public int minimaxBeta_CurrentMinNode(Node currentNode, int alphaOfCurrentNode, int betaOfCurrentNode) {
		Vector<Node> allSuccessors = b.getAllSuccessors(currentNode);
	
		for(int atIndex = 0; atIndex < allSuccessors.size(); atIndex++) {
			Node aSuccessor = allSuccessors.get(atIndex);
			int currentMax = this.minimaxAlphaBetaPruning(aSuccessor, alphaOfCurrentNode, betaOfCurrentNode);
			alphaOfCurrentNode = n.getMaxTwoIntegers(alphaOfCurrentNode, currentMax);
			currentNode.heuristicValue = n.getMaxTwoIntegers(currentNode.heuristicValue,alphaOfCurrentNode);
			if(alphaOfCurrentNode >= betaOfCurrentNode) {
				break;
			}
		}
		if(n.possibleNextMoveNodes(currentNode)!=null) {
			possibleNextMoveNodes.add(currentNode);
		}
		return alphaOfCurrentNode;
	}

	public Node nextNodeToMove(Node currentNode) {

		//this.getMinimax(currentNode);		// Change this to see how different between two algorithms.
		minimaxAlphaBetaPruning(currentNode, initializeAlpha(currentNode), initializeBeta(currentNode));
		Node newNode = n.getMaxNodeInList(possibleNextMoveNodes);
		possibleNextMoveNodes.removeAllElements();
		return newNode;
	}	
}