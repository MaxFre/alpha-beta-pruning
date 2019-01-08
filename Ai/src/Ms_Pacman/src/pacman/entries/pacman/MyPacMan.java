package pacman.entries.pacman;

import java.io.IOException;
import java.util.Random;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.util.IO;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE> {
	public MOVE DirectionChosen;

	// General game state this - not normalized!
	public int mazeIndex;
	public int currentLevel;
	public int pacmanPosition;
	public int currentScore;
	public int numOfPillsLeft;
	public int numOfPowerPillsLeft;

	// Ghost this, dir, dist, edible - BLINKY, INKY, PINKY, SUE
	public boolean isBlinkyEdible = false;
	public boolean isInkyEdible = false;
	public boolean isPinkyEdible = false;
	public boolean isSueEdible = false;

	public int blinkyDist = -1;
	public int inkyDist = -1;
	public int pinkyDist = -1;
	public int sueDist = -1;

	public MOVE blinkyDir;
	public MOVE inkyDir;
	public MOVE pinkyDir;
	public MOVE sueDir;

	private MOVE[] allMoves = MOVE.values();
	private Random rand = new Random();

	private static String FileName = "trainingData.txt";
	DataTuple[] dataTuples;

	AlgoID3 id3;
	DecisionTree tree;
	boolean firstAgentsTurn;

	public MyPacMan() {

		id3 = new AlgoID3();
		try {
			tree = id3.runAlgorithm("myData/dataset2.txt", "DIRECTIONCHOSEN", ";");
			System.out.println("pacman using dataset2");

//			tree.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub

//		CURRENTLEVEL;ISINKYEDIBLE;ISBLINKYEDIBLE;ISSUEEDIBLE;ISPINKYEDIBLE;BLINKYDIST;INKYDIST;PINKYDIST;SUEDIST;PACMANPOSITION;NumberOfNodes;DIRECTIONCHOSEN;

		String currentlevel = String.valueOf(game.getCurrentLevel());

		// Ghost edible
		String blinkyED = String.valueOf(game.isGhostEdible(GHOST.BLINKY));
		String INKYED = String.valueOf(game.isGhostEdible(GHOST.INKY));
		String PINKYED = String.valueOf(game.isGhostEdible(GHOST.PINKY));
		String SUEED = String.valueOf(game.isGhostEdible(GHOST.SUE));

		// Ghost dist
		String blinkyDist = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(GHOST.BLINKY)));
		String INKYDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(GHOST.INKY)));
		String PINKYDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(GHOST.PINKY)));
		String SUEDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(GHOST.SUE)));

		// pacman pos
		String pacmanPos = String.valueOf(game.getPacmanCurrentNodeIndex());

		// currentScore
		String currentScore = String.valueOf(game.getScore());

//		String[] toFindMove = {blinkyDist ,INKYDIST,PINKYDIST,SUEDIST, pacmanPos,currentScore};
		String[] toFindMove = { currentlevel, INKYED, blinkyED, PINKYED, SUEED, blinkyDist, INKYDIST, PINKYDIST,
				SUEDIST, pacmanPos, currentScore };

		String direction = tree.predictTargetAttributeValue(toFindMove);

//		System.out.println("MOVE: " + direction);

		if (direction == null) {
			return allMoves[rand.nextInt(allMoves.length)];
		}

		if (direction.equals("NEUTRAL")) {
			return allMoves[rand.nextInt(allMoves.length)];
		}

		if (direction.equals("LEFT")) {
			return MOVE.LEFT;
		}
		if (direction.equals("RIGHT")) {
			return MOVE.RIGHT;
		}
		if (direction.equals("UP")) {
			return MOVE.UP;
		}
		if (direction.equals("DOWN")) {
			return MOVE.DOWN;
		}
		return null;
	}
}