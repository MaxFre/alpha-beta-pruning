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
	int depth = 12;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	
	public Agent(Game othelloGame) {
		this.othelloGame = othelloGame;
	}
	
	public void makeMove(Board board, Value player) {
		boardCopy = new Board(board);
		Value currentPlayer = player;
		long start = System.currentTimeMillis();
		AiMove bestMove = getBestMove(boardCopy, player, depth);
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println("Time elapsed: " + timeElapsed + " milliseconds" + "\nSearch depth: " + depth);
		if(board.tryToFlip(bestMove.row, bestMove.col, false, currentPlayer)); 
	}
	
	private AiMove getBestMove(Board board, Value player, int depth) {
		Board boardCopy = new Board(board);
		
		GameState state = board.checkForWinner(player);
		// Base case check for end state
		if(depth == 0) { // if depth == 0 return what move the ai will do.
			return new AiMove(board.countBlacks());   // leaf, all aiMoves wihtput XY is leafs
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
				if (board.tryToFlip(row, col, true, player)) { 	   // check if possible.
					AiMove move = new AiMove();
					move.row = row;
					move.col = col;
					
					if(board.tryToFlip(row, col, false, player));  // make the move.
					if (player == Value.BLACK) {
							move.score = getBestMove(board, Value.WHITE, depth-1).score;
							if(alpha>=move.score){			
								return move;
							}
					} else {
							move.score = getBestMove(board, Value.BLACK, depth-1).score;
							if(beta<=move.score){			
								return move;
							}
					}
					
					
					moves.add(move);
					
					board.setBoardState(boardCopy.getCurrentBoardState());
				}
			}
		}	
		if(moves.size() <= 0) {
			return new AiMove(board.countBlacks());
		}
		else {		
			return evaluate(moves, player);
		}
	}
	
	
	
	public AiMove evaluate(ArrayList<AiMove> moves, Value player){
		
		// minMax
		int bestMove = 0;
		if (player == Value.BLACK) {		
			int bestScore = Integer.MIN_VALUE;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score > bestScore) {
					bestMove = i;
					bestScore = moves.get(i).score;
					if(bestScore<beta){
						beta = bestScore;
					}
				}
			}
		} else if (player == Value.WHITE) {		
			int bestScore = Integer.MAX_VALUE;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score < bestScore) {
					bestMove = i;
					bestScore = moves.get(i).score;
					if(bestScore>alpha){
						alpha = bestScore;
					}
				}
			}
		}
		// Return the best move
		return moves.get(bestMove);
	}
	
	
	
}
