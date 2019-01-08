package pacman.entries.pacman;


public class DecisionNode extends Node {
	/** the id of the attribute that this node represents */
	public int attribute;
	/** a list of child node */
	public Node[] nodes;
	/** the list of values for the attribute that correspond to the child nodes*/
	public String[] attributeValues;
}
