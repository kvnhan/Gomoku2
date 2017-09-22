package SetUp;

public class Position {
	int row;
	int column;
	double score;
	String stone;
	GomokuModel g;
	Position move;
	
	public Position(int row, int column, double score, String stone, GomokuModel g){
		this.row = row;
		this.column = column;
		this.score = score;
		this.stone = stone;
		this.g = g;
		
	}

	
}
