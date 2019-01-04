package pacman.entries.pacman;

import dataRecording.DataTuple;

public class Node {

	
	boolean isLeaf;
	DataTuple dataTuples;
	
	public Node(boolean isLeaf, DataTuple dataTuples) {
		this.isLeaf = isLeaf;
		this.dataTuples = dataTuples;
	}
	
	public Node() {
		
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	
	
	public DataTuple getDataTuples() {
		return dataTuples;
	}
}
