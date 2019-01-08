package dataRecording;

import pacman.controllers.*;
import pacman.entries.pacman.MyPacMan;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

/**
 * The DataCollectorHumanController class is used to collect training data from playing PacMan.
 * Data about game state and what MOVE chosen is saved every time getMove is called.
 * @author andershh
 *
 */
public class DataCollectorController extends HumanController{
	MyPacMan pacman;
	String filename = "dataset2.txt";
	
	public DataCollectorController(KeyBoardInput input){
		super(input);
	}
	public DataCollectorController(MyPacMan pacman){		
		super(input);
		this.pacman = pacman;
	}
	@Override
	public MOVE getMove(Game game, long dueTime) {
//		MOVE move = super.getMove(game, dueTime);				// if player
		MOVE move = pacman.getMove(game, dueTime);				// if AI
		DataTuple data = new DataTuple(game, move);

		
//		DataSaverLoader.SavePacManData(data,filename);		
		DataSaverLoader.SaveMyPacManData(data,filename);
		return move;
	}

}
