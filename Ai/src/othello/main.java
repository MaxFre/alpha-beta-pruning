package othello;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.LinkedList;

public class main {

	ArrayList<Node> blackAvailableMoves = new ArrayList<Node>();	
	ArrayList<Node> whiteAvailableMoves = new ArrayList<Node>();
	ArrayList<Node> endNodes = new ArrayList<Node>();
	Node node;
	String board[][] = new String[4][4];
	int maxDepth = 2;
	boolean first = true;
	boolean whiteTurn = false;
	boolean depthEnd = false;
	
	public main() {

		startingBoard();
		aiFindMove();
		//printBoard(board);

	}

	public void aiFindMove() {

	//	LinkedList<Node> tree = null;

		blackAvailableMoves.clear();
		// board, startX, startY, whatColor
		Node root = new Node(0,0, board);
		addNewMove(board, 0, 0, whiteTurn, root);	         	//Sends current board to find available moves
		System.out.println(blackAvailableMoves.size());		// how many moves available

		
		
		for (int b = 0; b < blackAvailableMoves.size(); b++) {
//			System.out.println("---------");
//			System.out.println("Turn1");
//			printBoard(blackAvailableMoves.get(b).getCurrentState());
//			System.out.println(b + 1);	
			b++;
		}
		
		addNewMove(blackAvailableMoves.get(0).getCurrentState(), 0, 0, true, blackAvailableMoves.get(0));	
		for (int b = 0; b < whiteAvailableMoves.size(); b++) {
//			System.out.println("---------");
//			System.out.println("Turn2");
//			printBoard(whiteAvailableMoves.get(b).getCurrentState());
//			System.out.println(b + 1);		
			b++;
		}
		blackAvailableMoves.clear();
		addNewMove(whiteAvailableMoves.get(0).getCurrentState(), 0, 0, false, whiteAvailableMoves.get(0));	
		for (int b = 0; b < blackAvailableMoves.size(); b++) {
			System.out.println("---------");
			System.out.println("Turn3");
			printBoard(blackAvailableMoves.get(b).getCurrentState());
			System.out.println(b + 1);		
			b++;
		}
		depthEnd = true;
		whiteAvailableMoves.clear();
		addNewMove(blackAvailableMoves.get(0).getCurrentState(), 0, 0, true, blackAvailableMoves.get(0));	
		for (int b = 0; b < whiteAvailableMoves.size(); b++) {
			System.out.println("---------");
			System.out.println("Turn4");
			System.out.println("End: " + whiteAvailableMoves.get(b).getEndNode());
			System.out.println("whiteCount: " + whiteAvailableMoves.get(b).getWhiteCount());
			printBoard(whiteAvailableMoves.get(b).getCurrentState());
			if(whiteAvailableMoves.get(b).getEndNode()){
				endNodes.add(whiteAvailableMoves.get(b));
			}
		
			System.out.println(b + 1);	
		}
//		whiteAvailableMoves.clear();
//		for (int b = 0; b < blackAvailableMoves.size(); b++) {
//			System.out.println("---------");
//			System.out.println("Turn2");
//		addNewMove(blackAvailableMoves.get(b).getCurrentState(), 0, 0, true);
//		printBoard(whiteAvailableMoves.get(b).getCurrentState());
//		}
//		blackAvailableMoves.clear();
//		for (int b = 0; b < whiteAvailableMoves.size(); b++) {		
//			addNewMove(whiteAvailableMoves.get(b).getCurrentState(), 0, 0, false);
//			System.out.println("---------");
//			System.out.println("Turn3");
//			printBoard(blackAvailableMoves.get(b).getCurrentState());
//			System.out.println(b + 1);		
//		}
	}
	
	public void addNewMove(String[][] state, int x, int y, boolean whiteTurn, Node fromNode) {
		// For loop to check ok moves, create list.
//		 System.out.println("finding new move " + x + " " + y);
		 
		
		String[][] newState = new String[4][4];
		
				for (int i = 0; i < state.length; i++) {       // copy of current state to use later
					for (int b = 0; b < state.length; b++) {
						newState[i][b] = state[i][b];
					}
				}
				System.out.println("test");
				printBoard(state);
				
				for (int i = 0; i < state.length; i++) { // find first empty and add new symbol
					for (int b = 0; b < state.length; b++) {
						if (newState[i][b].equals("[ ]")) {
							if (whiteTurn) {
								newState[i][b] = "[W]";
							} else {
								newState[i][b] = "[B]";
							}
							Node newNode = new Node(i, b, newState);
							if(depthEnd){
								newNode.setEndNode(true);
							}
							newNode.setFromNode(fromNode);
							
							if(whiteTurn){
								whiteAvailableMoves.add(newNode);						
								newState[i][b] = "[ ]";
							}
							else{
							blackAvailableMoves.add(newNode);						
							newState[i][b] = "[ ]";
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
