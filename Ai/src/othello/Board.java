package othello;

public class Board {
	Cell[][] cells;
	int ROWS,COLS, numBlack, numWhite;
	
	public Board(int row, int col) {
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
	}
	
	public Board(Board board) {
		this.ROWS = board.ROWS;
		this.COLS = board.COLS;
		cells = null;
		cells = new Cell[ROWS][COLS];
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				cells[r][c] = new Cell(r,c);
				cells[r][c].set(board.cells[r][c].get());
			}
		}
	}
	
	public void printBoard() {
		System.out.print("    ");
		for (int c = 0; c < COLS; c++)
			System.out.print(c + (c > 8 ? " " : "  ")); 
		System.out.println();
		
		for (int r = 0; r < ROWS; r++) {
			System.out.print(r + (r > 8 ? " " : "  "));
			for (int c = 0; c < COLS; c++)
				cells[r][c].draw();
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
				if (cells[r][c].value == Value.BLACK)
					count++;
		return count;
	}
	
	public int countWhites() {
		int count = 0;
		for (int r = 0; r < ROWS; r++) 
			for (int c = 0; c < COLS; c++)
				if (cells[r][c].value == Value.WHITE)
					count++;
		return count;
	}
	
	public void putDisc(int row, int col, Value val) {
		cells[row][col].set(val);
	}
	
	public boolean canPlay(Value turn) {
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (cells[r][c].value == Value.BLANK && tryToFlip(r, c, true, turn))
					return true;
		return false;
	}
	
	public boolean tryToFlip(int row, int col, boolean dontFlip, Value turn) {
		boolean hasFlipped = false;
		Value opposite = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
		Value next;
		
		if (cells[row][col].value == Value.BLANK) {
			
			// try to flip north direction
			if (row > 1 && cells[row-1][col].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = cells[--currentRow][col].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) {
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row; r > currentRow ; r--)  
							putDisc(r, col, turn);
					} 
				} while (currentRow-1 >= 0 && next != Value.BLANK);
			}
			
			// try to flip northeast direction
			if (row > 1 && col < COLS-2 && cells[row-1][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = cells[--currentRow][++currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c < currentCol ; r--, c++)  
							putDisc(r, c, turn);
					}
				} while (currentRow-1 >= 0 && currentCol < COLS-1 && next != Value.BLANK);
			}
			
			// try to flip east direction
			if (col < COLS-2 && cells[row][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = cells[row][++currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int c = col; c < currentCol; c++)  
							putDisc(row, c, turn);
					}
				} while (currentCol < COLS-1 && next != Value.BLANK);
			}
			
			// try to flip southeast direction
			if (row < ROWS-2 && col < COLS-2 && cells[row+1][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = cells[++currentRow][++currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c < currentCol ; r++, c++)  
							putDisc(r, c, turn);
					}
				} while (currentRow < ROWS-1 && currentCol < COLS-1 && next != Value.BLANK);
			}
			
			// try to flip south direction
			if (row < ROWS-2 && cells[row+1][col].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = cells[++currentRow][col].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row; r < currentRow; r++)  
							putDisc(r, col, turn);
					}
				} while (currentRow < ROWS-1 && next != Value.BLANK);
			}
			
			// try to flip southwest direction
			if (row < ROWS-2 && col > 1 && cells[row+1][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = cells[++currentRow][--currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c > currentCol; r++, c--) 
							putDisc(r, c, turn);
					}
				} while (currentRow < ROWS-1 && currentCol > 0 && next != Value.BLANK);
			}
			
			// try to flip west direction
			if (col > 1 && cells[row][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = cells[row][--currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int c = col; c > currentCol; c--) 
							putDisc(row, c, turn);
					}
				} while (currentCol > 0 && next != Value.BLANK);
			}
			
			// try to flip northwest direction
			if (row > 1 && col > 1 && cells[row-1][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = cells[--currentRow][--currentCol].value;
					if (next == opposite) { 
						neighborIsOpposite= true;
					} else if (next == turn && neighborIsOpposite) { 
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c > currentCol; r--, c--) 
							putDisc(r, c, turn);
					}
				} while (currentRow > 0 && currentCol > 0 && next != Value.BLANK);
			}
		}
		return hasFlipped;
	}
	
	public GameState checkForWinner(Value player) {
		GameState state;
		Value opponent = (player == Value.BLACK ? Value.WHITE : Value.BLACK);
		if(!canPlay(player)) {
			if(!canPlay(opponent)) {
				if (countBlacks() > countWhites()) {
					state = GameState.BLACK_WIN;
					return state;
				} else if (countBlacks() < countWhites()) {
					state = GameState.WHITE_WIN;
					return state;
				} else {
					state = GameState.DRAW;
					return state;
				}
			}
			return state = GameState.IN_PROGRESS;
		}
		return state = GameState.IN_PROGRESS;
	}
	
	public int getRows() {
		return ROWS;
	}
	public int getCols() {
		return COLS;
	}
	
	public Cell[][] getCurrentBoardState(){
		return this.cells;
	}
	
	public void setBoardState(Cell[][] state) {
		cells = null;
		cells = new Cell[ROWS][COLS];
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				cells[r][c] = new Cell(r,c);
				cells[r][c].set(state[r][c].get());
			}
		}
	}
}
