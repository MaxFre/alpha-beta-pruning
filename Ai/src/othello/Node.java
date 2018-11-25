package othello;

public class Node {

	private Boolean endNode = false;
	private int whiteCount = 0;
	private Node fromNode;
	private int x;
	private int y;
	private String[][] savedCurrentState;
	private boolean root = false;
	
	public Node(int x, int y, String[][] currentState, boolean root){
		this.x = x;
		this.y = y;
		this.root = root;
		
		savedCurrentState =  new String[4][4];
		
		for (int i = 0; i < currentState.length; i++) {       // copy of current state to use later
			for (int b = 0; b < currentState.length; b++) {
				savedCurrentState[i][b] = currentState[i][b];
			}
		}
	}
	
	public boolean isRoot(){
		return root;
	}
	
	public String[][] getCurrentState(){
		return savedCurrentState;
	}
	
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setEndNode(boolean end){
		endNode = end;
	}
	
	public void setFromNode(Node fromNode){
	this.fromNode = fromNode;	
	}
	
	public boolean getEndNode(){
		return endNode;
	}
	
	public int getWhiteCount(){
		whiteCount = 0;
		for (int i = 0; i < savedCurrentState.length; i++) {       // copy of current state to use later
			for (int b = 0; b < savedCurrentState.length; b++) {
				if(savedCurrentState[i][b].equals("[W]")){
					whiteCount++;
				}				
			}
		}
		return whiteCount;
	}
	
	public Node fromNode(){
		return fromNode;
	}
}
