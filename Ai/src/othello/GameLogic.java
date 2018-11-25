package othello;

public class GameLogic {
	
	
	private Board board;
	//private Value turn;
	private String turn;		
	private int ROWS = 4;
	private int COLS = 4;

	
	
	
	public void putDisc(int row, int col, String value) {
		board.cells[row][col] = value;
	}
	
	public boolean seeIfallowedMove(int row, int col, String[][] boardState, boolean whiteTurn){
		board = new Board(ROWS, COLS, "TEST");
		board.setState(boardState);
		boolean hasFlipped = false;
		String opposite = (!whiteTurn ? "[W]" : "[B]"); 
		turn = (whiteTurn ? "[W]" : "[B]"); 
		String next;
		
		
		//Value opposite = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
		//Value next;
		
	if (board.cells[row][col].equals("[ ]")) {
			
			// try to flip north direction
			if (row > 1 && board.cells[row-1][col].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = board.cells[--currentRow][col];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) {
						
						hasFlipped = true;
						return true;
						
					} 
				} while (currentRow-1 >= 0 && !next.equals("[ ]"));
			}
			
			// try to flip northeast direction
			if (row > 1 && col < COLS-2 && board.cells[row-1][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[--currentRow][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentRow-1 >= 0 && currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip east direction
			if (col < COLS-2 && board.cells[row][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = board.cells[row][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip southeast direction
			if (row < ROWS-2 && col < COLS-2 && board.cells[row+1][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[++currentRow][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentRow < ROWS-1 && currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip south direction
			if (row < ROWS-2 && board.cells[row+1][col].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = board.cells[++currentRow][col];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentRow < ROWS-1 && !next.equals("[ ]"));
			}
			
			// try to flip southwest direction
			if (row < ROWS-2 && col > 1 && board.cells[row+1][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[++currentRow][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentRow < ROWS-1 && currentCol > 0 && !next.equals("[ ]"));
			}
			
			// try to flip west direction
			if (col > 1 && board.cells[row][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = board.cells[row][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentCol > 0 && !next.equals("[ ]"));
			}
			
			// try to flip northwest direction
			if (row > 1 && col > 1 && board.cells[row-1][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[--currentRow][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						
						hasFlipped = true;
						return true;
					}
				} while (currentRow > 0 && currentCol > 0 && !next.equals("[ ]"));
			}
		}
		return hasFlipped;

	}
	
	
	public String[][] tryToFlip(int row, int col, String[][] boardState, boolean whiteTurn) {
		
		board.setState(boardState);
		
		String tempTurn = turn;
		boolean hasFlipped = true;
		String opposite = (!whiteTurn ? "[W]" : "[B]");
		turn = (whiteTurn ? "[W]" : "[B]");
		String next;
		
		String[][] state = board.getState();
			
		//Value opposite = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
		//Value next;
		
		if (state[row][col].equals("[ ]")) {
			
			// try to flip north direction
			if (row > 1 && state[row-1][col].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = state[--currentRow][col];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) {
						hasFlipped = true;
						for (int r = row; r > currentRow ; r--)  
							state[r][col] = tempTurn;
					} 
				} while (currentRow-1 >= 0 && !next.equals("[ ]"));
			}
			
			// try to flip northeast direction
			if (row > 1 && col < COLS-2 && state[row-1][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = state[--currentRow][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c < currentCol ; r--, c++)  
							state[r][c] = tempTurn;
					}
				} while (currentRow-1 >= 0 && currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip east direction
			if (col < COLS-2 && state[row][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = state[row][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int c = col; c < currentCol; c++)  
							state[row][c] = tempTurn;
					}
				} while (currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip southeast direction
			if (row < ROWS-2 && col < COLS-2 && state[row+1][col+1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = state[++currentRow][++currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c < currentCol ; r++, c++)  
							state[r][c] = tempTurn;
					}
				} while (currentRow < ROWS-1 && currentCol < COLS-1 && !next.equals("[ ]"));
			}
			
			// try to flip south direction
			if (row < ROWS-2 && state[row+1][col].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = state[++currentRow][col];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int r = row; r < currentRow; r++)  
							state[r][col] = tempTurn;
					}
				} while (currentRow < ROWS-1 && !next.equals("[ ]"));
			}
			
			// try to flip southwest direction
			if (row < ROWS-2 && col > 1 && state[row+1][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = state[++currentRow][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c > currentCol; r++, c--) 
							state[r][c] = tempTurn;
					}
				} while (currentRow < ROWS-1 && currentCol > 0 && !next.equals("[ ]"));
			}
			
			// try to flip west direction
			if (col > 1 && state[row][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = state[row][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int c = col; c > currentCol; c--) 
							state[row][c] = tempTurn;
					}
				} while (currentCol > 0 && !next.equals("[ ]"));
			}
			
			// try to flip northwest direction
			if (row > 1 && col > 1 && state[row-1][col-1].equals(opposite)) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = state[--currentRow][--currentCol];
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == tempTurn && neighborIsOpposite) { 
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c > currentCol; r--, c--) 
							state[r][c] = tempTurn;
					}
				} while (currentRow > 0 && currentCol > 0 && !next.equals("[ ]"));
			}
		}
		

		return state;

	}
	
}
