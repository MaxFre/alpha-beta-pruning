package othello;

import java.util.Scanner;

import ai.Agent;

public class Game {
	private Board board;
	private Agent ai;
	private Agent black;
	private Agent white;
	private GameState state;
	private GameMode mode;
	private Value turn;
	
	private int ROWS = 8;
	private int COLS = 8;
	private int roundCounter = 0;

	private static Scanner input = new Scanner(System.in);
	
	public Game() {
		board = new Board(ROWS, COLS);
		//chooseGamemode();
		mode = GameMode.AiVSAi;
		black = new Agent(this);
		white = new Agent(this);
		turn = Value.WHITE;
		state = GameState.IN_PROGRESS;
		playGame();
	}
	
	public void chooseGamemode() {
		boolean isInputValid = false;
		System.out.print("Welcome to Othello\nPlease Choose a Game mode\nEnter 'HvH' for Human versus Human, Or enter 'HvA' for Human versus AI");
		while(!isInputValid) {
			String gameMode = input.next();
			if(gameMode.equalsIgnoreCase("hvh") || gameMode.equalsIgnoreCase("hva")) {
				isInputValid = true;
				mode = (gameMode.equalsIgnoreCase("hvh") ? GameMode.HumanVSHuman : GameMode.HumanVSAi);
				if(mode == GameMode.HumanVSAi)
					ai = new Agent(this);
				System.out.println(gameMode.equalsIgnoreCase("hva") ? "You are white" : "");
			}
			else {
				System.out.println("Input not valid");
			}
		}
		turn = Value.BLACK;
	}
	
	public void playGame() {
		do {
			roundCounter++;
			System.out.print("\nRound: " + roundCounter + "\n");
			board.printBoard();
			if (!board.canPlay(turn)) {
				System.out.println(turn + " cannot make a move!");
				changeTurn();
				if (!board.canPlay(turn)) {
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
	
	public void makeMove() {
		boolean isInputValid = false;
		if(mode == GameMode.AiVSAi) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(turn == Value.BLACK) {
				black.makeMove(board, turn);
			}
			if(turn == Value.WHITE) {
				white.makeMove(board, turn);
			}
		}
		else if(mode == GameMode.HumanVSAi) {
			if(turn == Value.BLACK) {
				
				// AGENTS TURN
				// DO STUFF
				ai.makeMove(board, turn);
//				int[] aiMove = ai.makeMove(board, turn);
//				int row = aiMove[0];
//				int col = aiMove[1];
//
//				if (board.tryToFlip(row, col, false, turn))
//					isInputValid = true;
		
			}
			if(turn == Value.WHITE) {
			//else if (turn == Value.WHITE) {
				while (!isInputValid) {
					int row = getBoundedNumber("Row", 0, ROWS);
					int col = getBoundedNumber("Column", 0, COLS);
					
					if (board.tryToFlip(row, col, false, turn)) 
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
				
				if (board.tryToFlip(row, col, false, turn)) 
					isInputValid = true;
				else 
					System.out.println("Illegal move.");
			}		
		}
	}	
	
	public void changeTurn() {
		turn = (turn == Value.BLACK ? Value.WHITE : Value.BLACK);
		//System.out.format("%s's turn.%n", turn);
	}
	
	public int getBoundedNumber(String what, int min, int max) {
		boolean isValid = false;
		
		do {
			System.out.print(what + ": ");
			int num = input.nextInt();
			if (num < min || num > max)
				System.out.format("Invalid input. %s must be between 1 and %d inclusive.%n", what, max);
			else
				return num;
		
		} while (!isValid);
		return -1; // never reached
	}
	
	public GameState checkVictory() {
		return this.state;
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
