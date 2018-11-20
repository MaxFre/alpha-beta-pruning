package main;

import java.util.LinkedList;
import java.util.List;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.capsules.MoveWrapper;
import com.eudycontreras.othello.capsules.ObjectiveWrapper;
import com.eudycontreras.othello.controllers.Agent;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardCell;
import com.eudycontreras.othello.models.GameBoardState;
import com.eudycontreras.othello.threading.ThreadManager;
import com.eudycontreras.othello.threading.TimeSpan;
import com.eudycontreras.othello.utilities.GameTreeUtility;
import com.eudycontreras.othello.utilities.TraversalUtility;

public class TestMinimaxAgent extends Agent{

	public TestMinimaxAgent() {
		this(PlayerTurn.PLAYER_ONE);
	}
	
	public TestMinimaxAgent(String name) {
		super(name, PlayerTurn.PLAYER_ONE);
	}
	
	public TestMinimaxAgent(PlayerTurn playerTurn) {
		super(playerTurn);
	
	}

	/**
	 * Delete the content of this method and Implement your logic here!
	 */
	@Override
	public AgentMove getMove(GameBoardState gameState) {
		
		List<ObjectiveWrapper> moves = getAvailableMoves(gameState, playerTurn);
		//ObjectiveWrapper move = getBestMove(moves);  // WHY USE THIS?!
		
		ObjectiveWrapper move = null;
		
		if(!(moves.isEmpty())){
		 move = moves.get(0);
		}
		
		ThreadManager.pause(TimeSpan.millis(0)); // Pauses execution for the wait time to cause delay
		
		return new MoveWrapper(move);
	}
	
	public static List<ObjectiveWrapper> getAvailableMoves(GameBoardState state, PlayerTurn player){

		List<GameBoardCell> cells = state.getGameBoard().getGameBoardCells(player); //gets cells that are available for the current player?
		
		List<ObjectiveWrapper> moves = new LinkedList<>();
		
		 // checks each cell to see if its greater than the current "best move", at beginning just adding first cell.
		// "best" (longest path) move is placed first in the list.
		for(GameBoardCell cell : cells){ 
			
			for(ObjectiveWrapper move: TraversalUtility.getAvailableCells(cell, 4)){
				
				if(moves.isEmpty()){
					moves.add(move);
				}else{
					if(moves.get(0).getPath().size() < move.getPath().size()){
						moves.add(0,move);
					}else{
						moves.add(move);
					}
				}
			}
		}	
		
		return moves;
	}
	
	public static ObjectiveWrapper getBestMove(List<ObjectiveWrapper> moves) {

		if(moves.isEmpty()){
			return ObjectiveWrapper.NONE;
		}
		
		ObjectiveWrapper longest = moves.get(0);

		// checks all available moves, takes the one that is the longest? takes longest cuz more options?
		
		for (int i = 0; i<moves.size(); i++) {

			if (moves.get(i).getPath().size() > longest.getPath().size()) {		
				

				longest = moves.get(i);
			}
		}
		
		return longest;
	}
	
	
	
	
	/**
	 * Default template move which serves as an example of how to implement move
	 * making logic. Note that this method does not use Alpha beta pruning and
	 * the use of this method can disqualify you
	 * 
	 * @param gameState
	 * @return
	 */
	private AgentMove getExampleMove(GameBoardState gameState){
		
		int waitTime = UserSettings.MIN_SEARCH_TIME; // 1.5 seconds
		
		ThreadManager.pause(TimeSpan.millis(waitTime)); // Pauses execution for the wait time to cause delay
		
		return AgentController.getExampleMove(gameState, playerTurn); // returns an example AI move Note: this is not AB Pruning
	}

}
