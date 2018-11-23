package othello;

public class Board {
	
	Cell[][] cells;
	int ROWS, COLS, numBlack, numWhite;
	
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
	
	public void printBoard() {
		System.out.println();
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private final int WHITE = 0;
	private final int BLACK = 1;
	private final int row = 4;
	private final int col = 4;
	private String mBoard[][] = new String[row][col];

	public Board() {
		initializeBoard();
	}
	
	private void initializeBoard() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				mBoard[i][j] = " ";
			}
		}
		
		mBoard[1][1] = "O";
		mBoard[1][2] = "X";
		mBoard[2][2] = "O";
		mBoard[2][1] = "X";
		
	}
	
	public void updateCell(int row, int col, int player) {
		if(isValid(row,col)) {
			if(player == WHITE) {
				mBoard[row][col] = "O";
			}
			else if (player == BLACK) {
				mBoard[row][col] = "X";
			}
		}
	}
	
	public void printBoard() {
		int numBlacks = 0;
        int numWhites = 0;
		
		System.out.print("   ");
		for (int i = 0; i < mBoard.length; i++) {
			 System.out.print(" " + i + "  ");
        }
		System.out.printf("\n  ");
        for (int i = 0; i < mBoard.length; i++) {
            System.out.printf("----");
        }
        
        System.out.println();
        
        for (int i = 0; i < mBoard.length; i++) {
            System.out.printf(i + " |");
            for (int j = 0; j < mBoard.length; j++) {
                if (mBoard[i][j] == "O") {
                    System.out.printf(" O |");
                    numWhites++;
                } else if (mBoard[i][j] == "X") {
                    System.out.printf(" X |");
                    numBlacks++;
                } else {
                    System.out.printf("   |");
                }
            }
            System.out.println();
            System.out.printf("  ");
            for (int j = 0; j < mBoard.length; j++) {
                System.out.printf("----");
            }
            System.out.println();
 
        }
        System.out.println("Black: " + numBlacks + " - " + "White: " + numWhites);
        System.out.println();  
	}
	
	private boolean isValid(int row, int col) {
		if(row < 0 || row > 4) return false;
		if(col < 0 || row > 4) return false;
		else return true;
	}*/
}
