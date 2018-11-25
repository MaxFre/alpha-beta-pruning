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
		bestOption = Integer.MAX_VALUE;																	// starting value
		index = 0;																						// Starting index
		
		

		minMax();
		
		System.out.println();
		System.out.println("bestOption - " + bestOption);
		System.out.println("index - " + index);
		
		int bestMoveY = -1;
		int bestMoveX = -1;		
		boolean notFoundRoot =  true;
		
		
		Node node = whiteAvailableMoves2.get(index);		// finds the best node

		

		
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
		printBoard(node.getCurrentState());
		System.out.println("best move - X:" + bestMoveX + "  Y:" + bestMoveY);
		
		System.out.println("--DONE---");
		
	}
	
	public void minMax(){
		
		Node bestLeafComesFromThisNode = null;
		
		for(int findBestOption = 0;  findBestOption<whiteAvailableMoves2.size(); findBestOption++){			//check what states thats the best.
			if(whiteAvailableMoves2.get(findBestOption).getWhiteCount()<bestOption){						// searching for lower value, cuz black wants as low whiteCount as possible
				bestOption = whiteAvailableMoves2.get(findBestOption).getWhiteCount();
				index = findBestOption;
				bestLeafComesFromThisNode = whiteAvailableMoves2.get(findBestOption).fromNode();
			}
		}
		
		int secondBestFromThisBranch = Integer.MAX_VALUE;	
		int indexOfSecond = 0;
		
		for(int findBestOption = 0;  findBestOption<whiteAvailableMoves2.size(); findBestOption++){			//check what states thats the best.
			if(whiteAvailableMoves2.get(findBestOption).getWhiteCount()<secondBestFromThisBranch && whiteAvailableMoves2.get(findBestOption).fromNode() == bestLeafComesFromThisNode ){	
				secondBestFromThisBranch = whiteAvailableMoves2.get(findBestOption).getWhiteCount();
				indexOfSecond = findBestOption;				
			}
		}
		
		
		Node secondBest = new Node(0, 0, board, false);
		Node bestNode = whiteAvailableMoves2.get(0);
		
	
		// endNode (4)
		for (int findBestOption = 0; findBestOption < whiteAvailableMoves2.size(); findBestOption++) {

			if (whiteAvailableMoves2.get(findBestOption).getWhiteCount() > bestNode.getWhiteCount()) {
				Node tempNode = bestNode.fromNode();
				boolean isEnd =   bestNode.getEndNode();
				int x = bestNode.getX();
				int y  = bestNode.getY();
				String[][] state = bestNode.getCurrentState();
				bestNode = whiteAvailableMoves2.get(findBestOption);
				secondBest = new Node(x, y, state, false);				
				secondBest.setFromNode(bestNode.fromNode());
				secondBest.setEndNode(bestNode.getEndNode());
				
			
			}

//			if (whiteAvailableMoves2.get(findBestOption).getWhiteCount() > secondBest.getWhiteCount()
//					&& secondBest.fromNode() != whiteAvailableMoves2.get(findBestOption).fromNode()) {
//
//				secondBest = whiteAvailableMoves2.get(findBestOption);
//			}

		}
		System.out.println(secondBest + "    " + bestNode );
		
		// Child (3)
		for (int findBestOption = 0; findBestOption < blackAvailableMoves2.size(); findBestOption++) {
			
		}
		
		
		// Child (2)
		for (int findBestOption = 0; findBestOption < whiteAvailableMoves1.size(); findBestOption++) {
			
		}
		
		// Child (1)
		for (int findBestOption = 0; findBestOption < blackAvailableMoves1.size(); findBestOption++) {
			
		}
		
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
							
							
							if (whiteTurn) {
								newState[i][b] = "[W]";
							} else {
								newState[i][b] = "[B]";
							}
							Node newNode = new Node(i, b, newState, false);
							newNode.setFromNode(fromNode);
							if(depthEnd){
								newNode.setEndNode(true);
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
								}
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
