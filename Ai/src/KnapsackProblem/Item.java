package KnapsackProblem;

public class Item {

	private int value;
	private String name;
	private int weight;
	
	public Item(String name, int value, int weight){
		this.value = value;
		this.name = name;
		this.weight = weight;	
	}
		
	public int getWeight(){
		return weight;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
	public double getRelativeValue(){
		return (double)weight/value;
	}
	
}
