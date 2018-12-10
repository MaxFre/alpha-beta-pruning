package KnapsackProblem;

import java.util.ArrayList;

public class Knapsack {

	private int size;
	private ArrayList<Item> contains = new ArrayList<Item>();
	private int currentWeight;
	private double value;
	
	
	public Knapsack(int size){
		this.size = size;
	}
	
	public int getCurrentWeight(){
		return currentWeight;
	}
	public int getSize(){
		return size;
	}
	
	public double getValue(){
		return value;
	}
	
	public void removeItem(int index){
		Item item = contains.get(index);
		contains.remove(index);
		currentWeight -= item.getWeight();
		value -= item.getValue();
	}
	
	public void addItem(Item item){
		contains.add(item);
		currentWeight += item.getWeight();
		value += item.getValue();
	}
	
	
	public ArrayList<Item> getArrayList(){
		return contains;
	}
}
