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
	
	public Knapsack(Knapsack knapsack){
		this.size = knapsack.size;
		this.contains = knapsack.getArrayList();
	}
	
	public int getCurrentWeight(){
		currentWeight = 0;
		for (int i = 0; i < contains.size(); i++) {
			currentWeight += contains.get(i).getWeight();
		}
		return currentWeight;
	}
	public int getSize(){
		return size;
	}
	
	public double getValue(){
		
		value = 0;
		
		for (int i = 0; i < contains.size(); i++) {
			value += contains.get(i).getValue();
		}
		return value;
	}
	
	
	public int howManyItems(){
		int howMany = 0;
		
		howMany =  contains.size();
		return	howMany;
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
	
	public Item getItem(int index){
		return contains.get(index);
	}
	
	
	public void addList(ArrayList<Item> list){		
		contains = list;
	}
	
	
	public ArrayList<Item> getArrayList(){
		return contains;
	}
}
