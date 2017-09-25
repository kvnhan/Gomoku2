package Eval;


// Class to represent individual pieces on the board
public class Cell {
	public Integer horiz; // Horizontal position on the board
	public Integer vert; // Vertical position on the board
	public String occupiedBy; // Whose cell is on this piece
	
	
	// Constructor
	public Cell(Integer vert, Integer horiz){
		this.horiz = horiz;
		this.vert = vert;
	}
}
