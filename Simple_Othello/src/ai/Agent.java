package ai;

import java.util.ArrayList;
import java.util.List;

import othello.Board;
import othello.Game;
import othello.GameState;
import othello.Value;

public class Agent {
	
	Game othelloGame;
	Board boardCopy;
	int depth = 0;
	public Agent(Game othelloGame) {
		this.othelloGame = othelloGame;
	}
	
	public void makeMove(Board board, Value player) {
		boardCopy = new Board(board);
		Value currentPlayer = player;
		long start = System.currentTimeMillis();
		AiMove bestMove = getBestMove(boardCopy, player, 1000);
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println("Time elapsed: " + timeElapsed + " in milliseconds");
		//AiMove bestMove = minimax(boardCopy, currentPlayer, 0);
		if(board.tryToFlip(bestMove.row, bestMove.col, false, currentPlayer));
	}
	
	private List<AiMove> getAvailableMoves(Board board, Value player) {
		List<AiMove> availableCells = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board.tryToFlip(i, j, true, player)) {
					AiMove move = new AiMove();
					move.row = i;
					move.col = j;
					availableCells.add(move);
				}
					
			}
		}
		return availableCells;
	}
	
//	private AiMove minimax(Board board, Value player, int depth) {
//		// Base case check for end state
//		GameState state = board.checkForWinner(player);
//		System.out.println("STATE: " + state);
//		if (state == GameState.BLACK_WIN) {
//			return new AiMove(board.countBlacks());
//		} else if (state == GameState.WHITE_WIN) {
//			return new AiMove(board.countBlacks());
//		} else if (state == GameState.DRAW) {
//			return new AiMove(board.countBlacks());
//		}
//		
//		List<AiMove> availableCells = getAvailableMoves(board, player);
//		
//		int min = Integer.MAX_VALUE;
//		int max = Integer.MIN_VALUE;
//		int bestMove = 0;
//		
//		for(int i = 0; i < availableCells.size(); i++) {
//			AiMove move = availableCells.get(i);
//			
//			if(player == Value.BLACK) {
//				if(board.tryToFlip(move.row, move.col, false, player));
//				int currentScore = minimax(board, player, depth +1).score;
//				if(currentScore > max) {
//					max = currentScore;
//					bestMove = i;
//				}
//				
//				if(board.tryToFlip(move.row, move.col, false, Value.BLANK));
//						
//			}
//			else if(player == Value.WHITE) {
//				if(board.tryToFlip(move.row, move.col, false, player));
//				int currentScore = minimax(board, player, depth +1).score;
//				if(currentScore < min) {
//					min = currentScore;
//					bestMove = i;
//				}
//				
//				if(board.tryToFlip(move.row, move.col, false, Value.BLANK));
//			}
//		}
//		
//		return availableCells.get(bestMove);
//	}
	
	private AiMove getBestMove(Board board, Value player, int depth) {
		Board boardCopy = new Board(board);

		// Base case check for end state
		GameState state = board.checkForWinner(player);

		if(depth == 0) {
			return new AiMove(board.countBlacks());
		}
		else if (state == GameState.BLACK_WIN) {
			return new AiMove(board.countBlacks());
		} else if (state == GameState.WHITE_WIN) {
			return new AiMove(board.countBlacks());
		} else if (state == GameState.DRAW) {
			return new AiMove(board.countBlacks());
		}
		
		ArrayList<AiMove> moves = new ArrayList<AiMove>();

		// Do the recursive function calls and construct the moves arraylist
		for (int row = 0; row < board.getRows(); row++) {
			for (int col = 0; col < board.getCols(); col++) {
				if (board.tryToFlip(row, col, true, player)) {
					AiMove move = new AiMove();
					move.row = row;
					move.col = col;
					
					if(board.tryToFlip(row, col, false, player));
//					board.printBoard();
					if (player == Value.BLACK) {
							move.score = getBestMove(board, Value.WHITE, depth-1).score;
					} else {
							move.score = getBestMove(board, Value.BLACK, depth-1).score;
					}
					moves.add(move);
					
					board.setBoardState(boardCopy.getCurrentBoardState());
					//board.tryToFlip(row, col, false, (player == Value.BLACK) ? Value.WHITE : Value.BLACK); 
					//board.tryToFlip(row, col, false, Value.BLANK); 
				}
			}
		}
//		System.out.println("---------------------------------------------------------------------------------------");
//		System.out.println("Size of movesList: " + moves.size() + "\nPlayer: " + player + "\nDepth: " + depth + "\nPrinting the movesList");
//		
		if(moves.size() <= 0) {
			return new AiMove(board.countBlacks());
		}
		else {
//			for(int i = 0; i < moves.size(); i++) {
//				AiMove move = moves.get(i);
//				board.tryToFlip(move.row, move.col, false, player);
//				board.printBoard();
//				board.setBoardState(boardCopy.getCurrentBoardState());
//			}
			
			// Pick the best move for the current player
			int bestMove = 0;
			if (player == Value.BLACK) {
				int bestScore = Integer.MIN_VALUE;
				for (int i = 0; i < moves.size(); i++) {
					if (moves.get(i).score > bestScore) {
						bestMove = i;
						bestScore = moves.get(i).score;
					}
				}
			} else if (player == Value.WHITE) {
				int bestScore = Integer.MAX_VALUE;
				for (int i = 0; i < moves.size(); i++) {
					if (moves.get(i).score < bestScore) {
						bestMove = i;
						bestScore = moves.get(i).score;
					}
				}
			}

			// Printing the board with all of the moves
			// Return the best move
			return moves.get(bestMove);
		}
	}
}
