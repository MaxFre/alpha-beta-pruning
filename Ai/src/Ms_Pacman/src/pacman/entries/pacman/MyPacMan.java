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
public class MyPacMan extends Controller<MOVE>
{
	//Original
	//private MOVE myMove=MOVE.NEUTRAL;
		
	
	String[] attribute_list = {"mazeIndex", "currentLevel", "pacmanPosition", "currentScore", "numOfPillsLeft", "numOfPowerPillsLeft",
								"isBlinkyEdible", "isInkyEdible", "isPinkyEdible", "isSueEdible", "blinkyDist", "inkyDist", "pinkyDist", "sueDist",
								"blinkyDir", "inkyDir", "pinkyDir", "sueDir"};
	
	
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
	
	int testBliInteSurJulle = -1;
	
	AlgoID3 id3;
	DecisionTree tree;
	boolean firstAgentsTurn;
	
	public MyPacMan(boolean firstAgentsTurn) {
		this.firstAgentsTurn = firstAgentsTurn;
		
		id3 = new AlgoID3();
		try {
			if(firstAgentsTurn){
				tree = id3.runAlgorithm("myData/dataset.txt", "DIRECTIONCHOSEN", ";");
				System.out.println("pacman using dataset");
			}
			else{
				tree = id3.runAlgorithm("myData/dataset2.txt", "DIRECTIONCHOSEN", ";");
				System.out.println("pacman using dataset2");
			}
			tree.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		
//		CURRENTLEVEL;ISINKYEDIBLE;ISBLINKYEDIBLE;ISSUEEDIBLE;ISPINKYEDIBLE;BLINKYDIST;INKYDIST;PINKYDIST;SUEDIST;PACMANPOSITION;CURRENTSCORE;DIRECTIONCHOSEN;
			
		
		String currentlevel = String.valueOf(game.getCurrentLevel());
		
		// Ghost edible
		String blinkyED = String.valueOf(game.isGhostEdible(GHOST.BLINKY));
		String INKYED = String.valueOf(game.isGhostEdible(GHOST.INKY));
		String PINKYED =  String.valueOf(game.isGhostEdible(GHOST.PINKY));
		String SUEED =  String.valueOf(game.isGhostEdible(GHOST.SUE));
		
		
		// Ghost dist
		String blinkyDist = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY)));
		String INKYDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY)));
		String PINKYDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY)));
		String SUEDIST = String.valueOf(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE)));
		
		// pacman pos
		String pacmanPos = String.valueOf(game.getPacmanCurrentNodeIndex());
		
		// currentScore
		String currentScore = String.valueOf (game.getScore());
		
		
//		String[] toFindMove = {blinkyDist ,INKYDIST,PINKYDIST,SUEDIST, pacmanPos,currentScore};
		String[] toFindMove = {currentlevel,INKYED,blinkyED,PINKYED,SUEED, blinkyDist ,INKYDIST,PINKYDIST,SUEDIST, pacmanPos,currentScore};
		
		String direction = tree.predictTargetAttributeValue(toFindMove);
		
//		System.out.println("MOVE: " + direction);
		
		
		if(direction == null){
			return allMoves[rand.nextInt(allMoves.length)];
		}
		
		if(direction.equals("NEUTRAL")){
			return allMoves[rand.nextInt(allMoves.length)];
		}
		
		if(direction.equals("LEFT")){
			return MOVE.LEFT;
		}
		if(direction.equals("RIGHT")){
			return MOVE.RIGHT;
		}
		if(direction.equals("UP")){
			return MOVE.UP;
		}
		if(direction.equals("DOWN")){
			return MOVE.DOWN;
		}
		
		
		
		
		
		
		
//		this.mazeIndex = game.getMazeIndex();
//		this.currentLevel = game.getCurrentLevel();
//		this.pacmanPosition = game.getPacmanCurrentNodeIndex();
//		this.pacmanLivesLeft = game.getPacmanNumberOfLivesRemaining();
//		this.currentScore = game.getScore();
//		this.totalGameTime = game.getTotalTime();
//		this.currentLevelTime = game.getCurrentLevelTime();
//		this.numOfPillsLeft = game.getNumberOfActivePills();
//		this.numOfPowerPillsLeft = game.getNumberOfActivePowerPills();
//
//		if (game.getGhostLairTime(GHOST.BLINKY) == 0) {
//			this.isBlinkyEdible = game.isGhostEdible(GHOST.BLINKY);
//			this.blinkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY));
//		}
//
//		if (game.getGhostLairTime(GHOST.INKY) == 0) {
//			this.isInkyEdible = game.isGhostEdible(GHOST.INKY);
//			this.inkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY));
//		}
//
//		if (game.getGhostLairTime(GHOST.PINKY) == 0) {
//			this.isPinkyEdible = game.isGhostEdible(GHOST.PINKY);
//			this.pinkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY));
//		}
//
//		if (game.getGhostLairTime(GHOST.SUE) == 0) {
//			this.isSueEdible = game.isGhostEdible(GHOST.SUE);
//			this.sueDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE));
//		}
//
//		this.blinkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH);
//		this.inkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY), DM.PATH);
//		this.pinkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH);
//		this.sueDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE), DM.PATH);
//
//		this.numberOfNodesInLevel = game.getNumberOfNodes();
//		this.numberOfTotalPillsInLevel = game.getNumberOfPills();
//		this.numberOfTotalPowerPillsInLevel = game.getNumberOfPowerPills();
		
		return null;
	}

	
	/*
	 * 
	 * The algorithm can be coded inside one single function called Generate_Tree (D, attribute list):
	 * 
	 * Create node N. 
	 * If every tuple in D has the same class C, return N as a leaf node labeled as C.
	 * Otherwise, if the attribute list is empty, return N as a leaf node labeled with the majority class in D.
	 * Otherwise:
	 * 1. Call the attribute selection method on D and the attribute list, in order to choose the current  attribute A: S(D, attribute list) -> A. 
	 * 2. Label N as A and remove A from the attribute list.
	 * 3. For each value aj in attribute A:
	 * 		a) Separate all tuples in D so that attribute A takes the value aj, creating the subset Dj. 
	 * 		b) If Dj is empty, add a child node to N labeled with the majority class in D.
	 * 		c) Otherwise, add the resulting node from calling Generate_Tree(Dj, attribute) as a child node  to N. 
	 * 4. Return N.		
	 */
	
	private Node Generate_Tree(DataTuple[] dataTuples, String[] attribute_list) {
		// TODO Auto-generated method stub
		
		
		boolean hasSameClass = true;
		DataTuple compare;
		if(dataTuples.length != 0)
			compare = dataTuples[0];

		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		
		
		String data = IO.loadFile("trainingData.txt");
		String[] dataLine = data.split("\n");
		DataTuple[] dataTuples = new DataTuple[dataLine.length];
		
	
		
		for(int i = 0; i < dataLine.length; i++)
		{
			dataTuples[i] = new DataTuple(dataLine[i]);

			
		}
		
		
		int cutOff = (int) Math.round(dataTuples.length*0.75);
		System.out.println("storlek: " + cutOff);
		
//		return allMoves[rand.nextInt(allMoves.length)];	
		
		testBliInteSurJulle = testBliInteSurJulle+1;
		
		if(testBliInteSurJulle>1111) {
			testBliInteSurJulle = 0;
		}
		return dataTuples[testBliInteSurJulle].DirectionChosen;
	}
	
	public void extractData() {
		
		
		String data = IO.loadFile("trainingData.txt");
		String[] dataLine = data.split("\n");
		 dataTuples = new DataTuple[dataLine.length];
		
		int cutOff = (int) Math.round(dataTuples.length*0.75);
		
		for(int i = 0; i < dataLine.length; i++)
		{
			dataTuples[i] = new DataTuple(dataLine[i]);

			
		}
		
	

		this.DirectionChosen = dataTuples[0].DirectionChosen;
		this.mazeIndex = dataTuples[0].mazeIndex;
		this.currentLevel = dataTuples[0].currentLevel;
		this.pacmanPosition = dataTuples[0].pacmanPosition;
		this.currentScore = dataTuples[0].currentScore;
		this.numOfPillsLeft = dataTuples[0].numOfPillsLeft;
		this.numOfPowerPillsLeft = dataTuples[0].numOfPowerPillsLeft;
		this.isBlinkyEdible = dataTuples[0].isBlinkyEdible;
		this.isInkyEdible = dataTuples[0].isInkyEdible;
		this.isPinkyEdible = dataTuples[0].isPinkyEdible;
		this.isSueEdible = dataTuples[0].isSueEdible;
		this.blinkyDir = dataTuples[0].blinkyDir;
		this.inkyDir = dataTuples[0].inkyDir;
		this.pinkyDir = dataTuples[0].pinkyDir;
		this.sueDir = dataTuples[0].sueDir;
		
		//System.out.println("test: " + dataTuples[2] + " " + dataTuples[3]);
		
		
	}
	
	
	
	public Node Generate_Tree(DataTuple[] dataTuples, String[] attribute_list) {
		
		Node newNode = new Node();
		
		
		return newNode;
	}*/
	}