package pacman.entries.pacman;

import java.util.Random;

import dataRecording.DataTuple;
import pacman.controllers.Controller;
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
	
	public MyPacMan() {
		String data = IO.loadFile(FileName);
		System.out.println(data);
	}
	
	public MOVE getMove(Game game, long timeDue) 
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
	}
	
	
	
	
	
	
	
	
	
}