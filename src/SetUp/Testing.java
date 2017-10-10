package SetUp;

public class Testing {
	

	
	void startBoard(Position pos, MiniMax mmx, GomokuModel gm) {
		
		
     	pos.g.setBoard(5, "F", "O");
     	pos.g.setBoard(6, "G", "O");
     	pos.g.setBoard(7, "H", "O");
     	pos.g.setBoard(3, "C", "X");
     	pos.g.setBoard(6, "D", "X");
     	pos.g.showBoard("X", "MetzKhanWilder");
     	pos = mmx.minimax(gm, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "X", false);
     	pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), "X");
     	pos.g.showBoard("X", "MetzKhanWilder");
     	
     	pos.g.setBoard(4, "E", "O");
     	pos.g.setBoard(6, "G", "O");
     	pos.g.setBoard(7, "H", "O");
     	pos.g.setBoard(3, "C", "X");
     	pos.g.setBoard(6, "D", "X");
     	pos.g.showBoard("X", "MetzKhanWilder");
     	pos = mmx.minimax(gm, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, "X", false);
     	pos.g.setBoard(pos.row + 1, pos.g.parseCol(pos.column + 1), "X");
     	pos.g.showBoard("X", "MetzKhanWilder");
		
	}

}
