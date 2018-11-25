package othello;

public class Board {
	
	//Cell[][] cells;
	String[][] cells;
	int ROWS, COLS, numBlack, numWhite;
	
	/*public Board(int row, int col) {
		this.ROWS = row;
		this.COLS = col;
		cells = new Cell[ROWS][COLS];
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				cells[r][c] = new Cell(row, col);
		
		cells[1][1].set(Value.WHITE);
		cells[1][2].set(Value.BLACK);
		cells[2][2].set(Value.WHITE);
		cells[2][1].set(Value.BLACK);	
	}*/
	
	public Board(int row, int col, String message) {
		this.ROWS = row;
		this.COLS = col;
		
		cells = new String[ROWS][COLS];
		
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				cells[r][c] = "[ ]";
			}
		}
		
		cells[1][1] = "[W]";
		cells[1][2] = "[B]";
		cells[2][2] = "[W]";
		cells[2][1] = "[B]";	
	}
	
	public void printBoard() {
		System.out.println();
		System.out.print("    ");
		for (int c = 0; c < COLS; c++)
			System.out.print(c + (c > 8 ? " " : "  ")); 
		System.out.println();
		
		for (int r = 0; r < ROWS; r++) {
			System.out.print(r + (r > 8 ? " " : "  "));
			for (int c = 0; c < COLS; c++)
				System.out.print(cells[r][c]);
				//cells[r][c].draw();
			System.out.println();
		}
		
		numBlack = countBlacks();
		numWhite = countWhites();
		System.out.format("%nBlack: %d%nWhite: %d%n%n", numBlack, numWhite);
	}
	
	public int countBlacks() {
		int count = 0;
		for (int r = 0; r < ROWS; r++) 
			for (int c = 0; c < COLS; c++)
				if (cells[r][c].equals("[B]"))
				//if (cells[r][c].value == Value.BLACK)
					count++;
		return count;
	}
	
	public int countWhites() {
		int count = 0;
		for (int r = 0; r < ROWS; r++) 
			for (int c = 0; c < COLS; c++)
				if (cells[r][c].equals("[W]"))
				//if (cells[r][c].value == Value.WHITE)
					count++;
		return count;
	}
}
