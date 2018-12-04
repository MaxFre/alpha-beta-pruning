package KnapsackProblem;

import java.util.ArrayList;

public class Greedy {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Knapsack> Knapsacks = new ArrayList<Knapsack>();
	
	int dimensions = 3;
	int weight = 1;
	
	
	public Greedy(){
		
		createItems();
		createKnapsack();
		
		System.out.println("Knapsack - Size:" + Knapsacks.get(0).getSize());
		for(int i = 0; i<items.size(); i++){
			System.out.println(items.get(i).getName() + " weight: " + items.get(i).getWeight()  + " value: " + items.get(i).getValue()+ " relativeValue: " + items.get(i).getRelativeValue());
		}
	
	}
	
	public void createKnapsack(){
		Knapsack knapsack = new Knapsack(30);
		Knapsacks.add(knapsack);
	}
	
	public void createItems(){
		
		for(int i = 1; i<11; i++){
			Item item = new Item("Item"+i,dimensions, weight);
			items.add(item);
			dimensions++;
			weight++;
		}
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Greedy run  = new Greedy();
	}
}
