package othello;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.LinkedList;

public class main {


	ArrayList<Node> blackAvailableMoves1 = new ArrayList<Node>();	
	ArrayList<Node> blackAvailableMoves2 = new ArrayList<Node>();
	ArrayList<Node> whiteAvailableMoves1 = new ArrayList<Node>();
	ArrayList<Node> whiteAvailableMoves2 = new ArrayList<Node>();
	ArrayList<Node> endNodes = new ArrayList<Node>();
	Node node;
	String board[][] = new String[4][4];
	int maxDepth = 2;
	boolean whiteTurn = false;
	boolean depthEnd = false;
	int depth = 0;
	boolean first = true;
	boolean second = false;
	boolean third = false;
	boolean fourth = false;
	int bestOption;
	int index;
	GameLogic gameLogic = new GameLogic();
	
	public main() {

		startingBoard();
		aiFindMove();
		//printBoard(board);

	}

	public void aiFindMove() {

	//	LinkedList<Node> tree = null;


		// board, startX, startY, whatColor
		Node root = new Node(0,0, board, true);
		
		first = true;
		addNewMove(board, 0, 0, whiteTurn, root); // gets black1
		first = false;

		for (int whiteFirst = 0; whiteFirst < blackAvailableMoves1.size(); whiteFirst++) {
			second = true;
			whiteTurn = true;
			addNewMove(blackAvailableMoves1.get(whiteFirst).getCurrentState(), 0, 0, whiteTurn, blackAvailableMoves1.get(whiteFirst)); // gets white1
			second = false;
			whiteTurn = false;
		}
		for (int blackSecond = 0; blackSecond < whiteAvailableMoves1.size(); blackSecond++) {

			third = true;
			whiteTurn = false;
			addNewMove(whiteAvailableMoves1.get(blackSecond).getCurrentState(), 0, 0, whiteTurn, whiteAvailableMoves1.get(blackSecond)); // gets black2
			third = false;
			whiteTurn = true;
		}
		for (int whiteSecond = 0; whiteSecond < blackAvailableMoves2.size(); whiteSecond++) {

			fourth = true;
			whiteTurn = true;
			addNewMove(blackAvailableMoves2.get(whiteSecond).getCurrentState(), 0, 0, whiteTurn, blackAvailableMoves2.get(whiteSecond)); // gets white2
			fourth = false;
			whiteTurn = false;
		}
								

		
		System.out.println("blackAvailableMoves1 - " + blackAvailableMoves1.size());		// how many moves available
		System.out.println("whiteAvailableMoves1 - " + whiteAvailableMoves1.size());
		System.out.println("blackAvailableMoves2 - " + blackAvailableMoves2.size());
		System.out.println("whiteAvailableMoves2 - " + whiteAvailableMoves2.size());
	
		

		
		
		whiteTurn = false;
		
		
		

		minMax();
		
		System.out.println();
		System.out.println("bestOption - " + bestOption);
		System.out.println("index - " + index);
		
		int bestMoveY = -1;
		int bestMoveX = -1;		
		boolean notFoundRoot =  true;
		
		
		Node node = blackAvailableMoves1.get(index);		// finds the best node
		printBoard(node.getCurrentState());
		

		
		while(notFoundRoot){								// walks up the tree to find from the best node until root is found, 
			if(!node.fromNode().isRoot()){					// then takes the first node on the path to best node
				 node = node.fromNode();
			}
						
			if(node.fromNode().isRoot()){		
				bestMoveX = node.getX();
				bestMoveY = node.getY();
				notFoundRoot = false;
			}
		}
		
		System.out.println("-----");
//		printBoard(node.getCurrentState());
		System.out.println("best move - X:" + bestMoveX + "  Y:" + bestMoveY);
		
		System.out.println("--DONE---");
		
	}
	
	public void minMax(){
					
		bestOption = Integer.MIN_VALUE;																	// starting value
		index = 0;																						// Starting index
		
		Node whiteSecondBest = new Node(0, 0, board, false);
		Node whitesBestNode = whiteAvailableMoves2.get(0);
		
		int secondbestValue = Integer.MIN_VALUE;
	
		// endNode (4)
		for (int findBestOption = 1; findBestOption < whiteAvailableMoves2.size(); findBestOption++) {
						
			if (whiteAvailableMoves2.get(findBestOption).getWhiteCount() > whitesBestNode.getWhiteCount()) {		// WHITES BEST
				if(secondbestValue<whitesBestNode.getWhiteCount() && whitesBestNode.fromNode() != whiteAvailableMoves2.get(findBestOption).fromNode()){
					whiteSecondBest = whiteAvailableMoves2.get(findBestOption);
					secondbestValue = whiteSecondBest.getWhiteCount();
					System.out.println("--this---");
				}
				bestOption = whiteAvailableMoves2.get(findBestOption).getWhiteCount();
				whitesBestNode = whiteAvailableMoves2.get(findBestOption);
				index = findBestOption;
				
				
				if (whiteAvailableMoves2.get(findBestOption).getWhiteCount() > secondbestValue && whitesBestNode.fromNode() != whiteAvailableMoves2.get(findBestOption).fromNode()) {	
					whiteSecondBest = whiteAvailableMoves2.get(findBestOption);
					System.out.println("--this---");
				}
			}


		}
		
		
		printBoard(whitesBestNode.getCurrentState());
		System.out.println("-----");
		printBoard(whiteSecondBest.getCurrentState());
		System.out.println("whites best last");
		
		
		bestOption = Integer.MAX_VALUE;																	// starting value
		index = 0;			
		int BlacksSecondbestValue = Integer.MAX_VALUE;		
		Node blacksSecondBest = new Node(0, 0, board, false);
		Node blacksBestNode = blackAvailableMoves2.get(0);
		
		// Child (3)
		for (int findBestOption = 1; findBestOption < blackAvailableMoves2.size(); findBestOption++) {		// blacks best
			
			if (blackAvailableMoves2.get(findBestOption).getWhiteCount() < blacksBestNode.getWhiteCount() 
					&& whitesBestNode.fromNode() == blackAvailableMoves2.get(findBestOption) 
					|| whiteSecondBest.fromNode() == blackAvailableMoves2.get(findBestOption)) {
				
				
				if(blacksSecondBest.getWhiteCount()>blacksBestNode.getWhiteCount() && blacksBestNode.fromNode() != blackAvailableMoves2.get(findBestOption).fromNode()){
					blacksSecondBest = blackAvailableMoves2.get(index);
					BlacksSecondbestValue = blacksSecondBest.getWhiteCount();
				}
				
				
				blacksBestNode = blackAvailableMoves2.get(findBestOption);
				index = findBestOption;
			}
	
		}
		
		printBoard(blacksBestNode.getCurrentState());
		System.out.println("-----");
		printBoard(blacksSecondBest.getCurrentState());
		System.out.println("Blacks best last");
		
		
		bestOption = Integer.MIN_VALUE;																	// starting value
		index = 0;			
		int whitesSecondbestValue = Integer.MIN_VALUE;		
		Node whitesSecondBest = new Node(0, 0, board, false);
		Node whitessBestNode = whiteAvailableMoves1.get(0);
		
		// Child (2)
		for (int findBestOption = 0; findBestOption < whiteAvailableMoves1.size(); findBestOption++) {		// whites best
			if (whiteAvailableMoves1.get(findBestOption).getWhiteCount() > whitessBestNode.getWhiteCount() 
					&& blacksBestNode.fromNode() == whiteAvailableMoves1.get(findBestOption) 
					|| whiteSecondBest.fromNode() == whiteAvailableMoves1.get(findBestOption)) {
				
				
				if(whitesSecondbestValue<whitessBestNode.getWhiteCount() && whitessBestNode.fromNode() != whiteAvailableMoves1.get(findBestOption).fromNode()){
					whitesSecondBest = whiteAvailableMoves1.get(findBestOption);
					whitesSecondbestValue = whitesSecondBest.getWhiteCount();
					System.out.println("--asdasds---");
				}
				
				
				blacksBestNode = blackAvailableMoves2.get(findBestOption);
				index = findBestOption;
			}
		}
		
		
		printBoard(whitessBestNode.getCurrentState());
		System.out.println("-----");
		printBoard(whitesSecondBest.getCurrentState());
		System.out.println("Whites best first");
		
		Node toTake = blackAvailableMoves1.get(0);
		int finalBestMove = Integer.MAX_VALUE;		
		
		// Child (1)
		for (int findBestOption = 1; findBestOption < blackAvailableMoves1.size(); findBestOption++) {		// blacks best
			if(finalBestMove<blackAvailableMoves1.get(findBestOption).getWhiteCount() && blackAvailableMoves1.get(findBestOption).fromNode() == whitessBestNode 
					||blackAvailableMoves1.get(findBestOption).fromNode() == whitessBestNode );
						toTake = blackAvailableMoves1.get(findBestOption);
						index = findBestOption;
		}
		
		printBoard(toTake.getCurrentState());
		System.out.println("Blacks best first");
		
	}
	
	public void addNewMove(String[][] state, int x, int y, boolean whiteTurn, Node fromNode) {
		
		
		
		// For loop to check ok moves, create list.

	
		
		String[][] newState = new String[4][4];		
				for (int i = 0; i < state.length; i++) {     	  // copy of current state to use later
					for (int b = 0; b < state.length; b++) {
						newState[i][b] = state[i][b];
					}
				}

				for (int i = 0; i < state.length; i++) { 		// find first empty and add new symbol
					for (int b = 0; b < state.length; b++) {
						if (newState[i][b].equals("[ ]")) {
							
							// SEE IF FLIP I,B = X,Y
							// sends XY and see if its a okey move.
							
				
//							System.out.println(gameLogic.seeIfallowedMove(i, b, newState, whiteTurn));
							
							if(gameLogic.seeIfallowedMove(i, b, newState, whiteTurn)){
								newState = gameLogic.tryToFlip(i, b, newState, whiteTurn);
			
								
						
							
//							if (whiteTurn) {
//								newState[i][b] = "[W]";
//							} else {
//								newState[i][b] = "[B]";
//							}
							Node newNode = new Node(i, b, newState, false);
							newNode.setFromNode(fromNode);
							if(depthEnd){
								newNode.setEndNode(true);
							}
							
							for (int x1 = 0; x1 < state.length; x1++) {     	  // copy of current state to use later
								for (int y1 = 0; y1 < state.length; y1++) {
									newState[x1][y1] = state[x1][y1];
								}
							}
							
							if(whiteTurn){
								if(second  && !fourth){
									whiteAvailableMoves1.add(newNode);						
									newState[i][b] = "[ ]";
								}
								if(fourth){
									whiteAvailableMoves2.add(newNode);						
									newState[i][b] = "[ ]";
								}
								
							}
							else{
								if(first && !third){
									blackAvailableMoves1.add(newNode);						
									newState[i][b] = "[ ]";
								}
								if(third && !fourth){
									blackAvailableMoves2.add(newNode);						
									newState[i][b] = "[ ]";
//									System.out.println(blackAvailableMoves2.size());
								}}
							}
						}

					}
				}
	}

	
	
	
	public void startingBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int b = 0; b < board.length; b++) {
				board[i][b] = "[ ]";
				if (i == 1 && b == 1) {
					board[i][b] = "[W]";
				}
				if (i == 1 && b == 2) {
					board[i][b] = "[B]";
				}
				if (i == 2 && b == 1) {
					board[i][b] = "[B]";
				}
				if (i == 2 && b == 2) {
					board[i][b] = "[W]";
				}
			}
		}
	}
	
	public void printBoard(String[][] state){
		for(int i = 0; i<state.length; i++){		
			for(int b = 0; b<state.length; b++){
				System.out.print(state[i][b]);
				if(b == 3){
					System.out.println(" ");
				}
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		main s = new main();
	}
}
