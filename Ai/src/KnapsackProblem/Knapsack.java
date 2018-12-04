package KnapsackProblem;

import java.util.ArrayList;

public class Knapsack {

	private int size;
	private ArrayList<Item> contains = new ArrayList<Item>();
	private int currentWeight;
	
	public Knapsack(int size){
		this.size = size;
	}
	
	public int getCurrentWeight(){
		return currentWeight;
	}
	public int getSize(){
		return size;
	}
	
	public void removeItem(int index){
		Item item = contains.get(index);
		contains.remove(index);
		currentWeight -= item.getWeight();
	}
	
	public void addItem(Item item){
		contains.add(item);
		currentWeight += item.getWeight();
	}
	
	
	public ArrayList<Item> getArrayList(){
		return contains;
	}
}
