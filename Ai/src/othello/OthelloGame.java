package othello;

import java.util.Scanner;

public class OthelloGame {
	private Board board;
	private GameState state;
	private GameMode mode;
	//private Value turn;
	private String turn;
	
	
	private int ROWS = 4;
	private int COLS = 4;
	private int roundCounter = 0;

	private static Scanner input = new Scanner(System.in);
	
	public OthelloGame() {
		//board = new Board(ROWS, COLS);
		board = new Board(ROWS, COLS, "TEST");
		chooseGamemode();
		setStartingPlayer();
		state = GameState.IN_PROGRESS;
		playGame();
	}
	
	public void playGame() {
		do {
			roundCounter++;
			System.out.print("\nRound: " + roundCounter);
			board.printBoard();
			if (!canPlay()) {
				System.out.println(turn + " cannot make a move!");
				changeTurn();
				if (!canPlay()) {
					determineWinner();
					return;
				} else { 
					System.out.println("It is " + turn + "'s turn again.");
				}
			} else {
				System.out.format("%s's turn.%n", turn);
			}
			makeMove();		
			changeTurn();
		} while (state == GameState.IN_PROGRESS);
	}
	
	public void determineWinner() {
		if(mode == GameMode.HumanVSHuman) {
			if (board.countBlacks() > board.countWhites()) {
				state = GameState.BLACK_WIN;
				System.out.println("Black wins!");
			} else if (board.countBlacks() < board.countWhites()) {
				state = GameState.WHITE_WIN;
				System.out.println("White wins!");
			} else {
				state = GameState.DRAW;
				System.out.println("It's a draw!");
			}
		}
		else if(mode == GameMode.HumanVSAi) {
			if (board.countBlacks() > board.countWhites()) {
				state = GameState.AI_WIN;
				System.out.println("Agent wins!");
			} else if (board.countBlacks() < board.countWhites()) {
				state = GameState.WHITE_WIN;
				System.out.println("White wins!");
			} else {
				state = GameState.DRAW;
				System.out.println("It's a draw!");
			}
		}
	}
	
	public void makeMove() {
		boolean isInputValid = false;
		
		if(mode == GameMode.HumanVSAi) {
			if(turn.equals("[B]")) {
			//if(turn == Value.BLACK) {
				//AGENTS TURN 
				//DO STUFF
				
				//Får row och col från agent
				//if (tryToFlip(row, col, false))
				//isInputValid = true;		
			}
			if(turn.equals("[W]")) {
			//else if (turn == Value.WHITE) {
				while (!isInputValid) {
					int row = getBoundedNumber("Row", 0, ROWS);
					int col = getBoundedNumber("Column", 0, COLS);
					
					if (tryToFlip(row, col, false)) 
						isInputValid = true;
					else 
						System.out.println("Illegal move.");
				}		
			}
		}
		
		else if (mode == GameMode.HumanVSHuman) {
			while (!isInputValid) {
				int row = getBoundedNumber("Row", 0, ROWS);
				int col = getBoundedNumber("Column", 0, COLS);
				
				if (tryToFlip(row, col, false)) 
					isInputValid = true;
				else 
					System.out.println("Illegal move.");
			}		
		}
	}
	
	// determines if the current player can make a move somewhere.
	public boolean canPlay() {
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (board.cells[r][c].equals("[ ]") && tryToFlip(r, c, true))
				//if (board.cells[r][c].value == Value.BLANK && tryToFlip(r, c, true))
					return true;
		return false;		
	}
	
	public void putDisc(int row, int col, Value val) {
		//board.cells[row][col].set(val);
	}
	
	public void putDisc(int row, int col, String value) {
		board.cells[row][col] = value;
	}
	
	public void changeTurn() {
		turn = (turn.equals("[B]") ? "[W]" : "[B]");
		//turn = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
	}
	//Riktiga koden med Cell klassen
	/*public boolean tryToFlip(int row, int col, boolean dontFlip) {
		boolean hasFlipped = false;
		Value opposite = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
		Value next;
		
		if (board.cells[row][col].value == Value.BLANK) {
			
			// try to flip north direction
			if (row > 1 && board.cells[row-1][col].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = board.cells[--currentRow][col].value;
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
			if (row > 1 && col < COLS-2 && board.cells[row-1][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[--currentRow][++currentCol].value;
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
			if (col < COLS-2 && board.cells[row][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = board.cells[row][++currentCol].value;
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
			if (row < ROWS-2 && col < COLS-2 && board.cells[row+1][col+1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[++currentRow][++currentCol].value;
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
			if (row < ROWS-2 && board.cells[row+1][col].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row;
				do {
					next = board.cells[++currentRow][col].value;
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
			if (row < ROWS-2 && col > 1 && board.cells[row+1][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[++currentRow][--currentCol].value;
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
			if (col > 1 && board.cells[row][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentCol = col;
				do {
					next = board.cells[row][--currentCol].value;
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
			if (row > 1 && col > 1 && board.cells[row-1][col-1].value == opposite) {
				boolean neighborIsOpposite = false;
				int currentRow = row, currentCol = col;
				do {
					next = board.cells[--currentRow][--currentCol].value;
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

	}*/
	
	public boolean tryToFlip(int row, int col, boolean dontFlip) {
		boolean hasFlipped = false;
		String opposite = (turn.equals("[B]") ? "[W]" : "[B]"); 
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row; r > currentRow ; r--)  
							putDisc(r, col, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c < currentCol ; r--, c++)  
							putDisc(r, c, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int c = col; c < currentCol; c++)  
							putDisc(row, c, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c < currentCol ; r++, c++)  
							putDisc(r, c, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row; r < currentRow; r++)  
							putDisc(r, col, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r < currentRow && c > currentCol; r++, c--) 
							putDisc(r, c, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int c = col; c > currentCol; c--) 
							putDisc(row, c, turn);
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
						if (dontFlip)	
							return true;
						hasFlipped = true;
						for (int r = row, c = col; r > currentRow && c > currentCol; r--, c--) 
							putDisc(r, c, turn);
					}
				} while (currentRow > 0 && currentCol > 0 && !next.equals("[ ]"));
			}
		}
		return hasFlipped;

	}
	
	public int getBoundedNumber(String message, int min, int max) {
		boolean isValid = false;
		
		do {
			System.out.print(message + ": ");
			while(!input.hasNextInt()) {
				System.out.println("Enter a number");
				input.next();
			}
			int num = input.nextInt();
			if (num < min || num > max)
				System.out.format("Invalid input. %s must be between 0 and %d inclusive.%n", message, max);
			else
				return num;
		
		} while (!isValid);
		return -1; // never reached
	}
	
	public void chooseGamemode() {
		boolean isInputValid = false;
		System.out.print("Welcome to Othello\nPlease Choose a Game mode\nEnter 'HvH' for Human versus Human, Or enter 'HvA' for Human versus AI");
		while(!isInputValid) {
			String gameMode = input.next();
			if(gameMode.equalsIgnoreCase("hvh") || gameMode.equalsIgnoreCase("hva")) {
				isInputValid = true;
				mode = (gameMode.equalsIgnoreCase("hvh") ? GameMode.HumanVSHuman : GameMode.HumanVSAi);
				System.out.println(gameMode.equalsIgnoreCase("hva") ? "You are white" : "");
			}
			else {
				System.out.println("Input not valid");
			}
		}
	}
	
	public void setStartingPlayer() {
		boolean isInputValid = false;
		System.out.print("Who starts? ");
		while (!isInputValid) {
			String starting = input.next();
			if (starting.equalsIgnoreCase("b") || starting.equalsIgnoreCase("w")) {
				isInputValid = true;
				turn = (starting.equalsIgnoreCase("b") ? "[B]" : "[W]");
				//turn = (starting.equalsIgnoreCase("b") ? Value.BLACK : Value.WHITE);
			} else {
				System.out.println("Please type 'b' or 'w'.");
			}
		}			
	}
	
	public static void main(String[] args) {
		new OthelloGame();
	}
}
