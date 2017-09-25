package Eval;


// Class to represent individual pieces on the board
public class Cell {
	Integer horiz; // Horizontal position on the board
	Integer vert; // Vertical position on the board
	String occupiedBy; // Whose cell is on this piece
	
	
	// Constructor
	public Cell(Integer vert, Integer horiz){
		this.horiz = horiz;
		this.vert = vert;
	}
}
