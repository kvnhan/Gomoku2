package TicTacToe;

public class WinCondition {
	
	// Checking winning node
		public boolean checkWin(Node currentNode) {
			return (this.checkWinOnRow(currentNode)
					||this.checkWinOnColumn(currentNode)
					||this.checkWinOnDiagonal(currentNode));
		}
		public boolean checkWinOnRow(Node currentNode) {
			for(int row = 0; row < currentNode.board.length; row++) {
				int timesOfNodeRepeated = 0;
				String scanForElement = currentNode.board[row][0];
				if (scanForElement == null) break;
				for(int column = 1; column < currentNode.board.length; column ++) {
					String nextString = currentNode.board[row][column];
					if(nextString == null) break;
					else if (scanForElement.contentEquals(nextString) == false) break;
						else timesOfNodeRepeated++;
				}
				if(timesOfNodeRepeated == 2) return true;
			}
			return false;
		}
		public boolean checkWinOnColumn(Node currentNode) {
			for(int column = 0; column < currentNode.board.length; column++) {
				int timesOfNodeRepeated = 0;
				String scanForElement = currentNode.board[0][column];
				if (scanForElement == null) break;
				for(int row = 1; row < currentNode.board.length; row ++) {
					String nextString = currentNode.board[row][column];
					if(nextString == null) break;
					else if (scanForElement.contentEquals(nextString) == false) break;
						else timesOfNodeRepeated++;
				}
				if(timesOfNodeRepeated == 2) return true;
			}
			return false;
		}
		public boolean checkWinOnDiagonal(Node currentNode) {
			String[][] aBoard = currentNode.board;
			if(aBoard[1][1] != null) {
				if(aBoard[0][0] != null && aBoard[2][2] != null) return this.checkWinOnLeftDiagonal(currentNode);
				else if(aBoard[0][2] != null && aBoard[2][0] != null) return this.checkWinOnRightDiagonal(currentNode);	
				else return false;
			}
			else return false;
		}
		public boolean checkWinOnLeftDiagonal(Node currentNode) {
			String[][] aBoard = currentNode.board;
			return (aBoard[1][1].contentEquals(aBoard[0][0]) && aBoard[1][1].contentEquals(aBoard[2][2]));
		}
		public boolean checkWinOnRightDiagonal(Node currentNode) {
			String[][] aBoard = currentNode.board;
			return (aBoard[1][1].contentEquals(aBoard[0][2]) && aBoard[1][1].contentEquals(aBoard[2][0]));
		}
		
		// Only evaluate heuristicValue at leafNode.
		public int evaluateHeuristicValue(Node currentNode) {
			if(currentNode.nextPlayer == "X" && checkWin(currentNode)==true) return -1;
			if(currentNode.nextPlayer == "O" && checkWin(currentNode)==true) return 1;
			return 0;
		}

}
