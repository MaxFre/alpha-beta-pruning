package KnapsackProblem;

import java.util.ArrayList;

public class Greedy {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Knapsack> Knapsacks = new ArrayList<Knapsack>();
	
	int value = 3;
	int weight = 1;
	
	
	
	public Greedy(){
		
		createItems();
		createKnapsack();
//		printLayout();
		greedyAlgortihm();
	
	}
	
	
	
	public void greedyAlgortihm(){
		
			while(true){
				int bestValueIndex = 0;
				double bestValue = 0;
				
				if(items.isEmpty() || Knapsacks.get(0).getCurrentWeight() == Knapsacks.get(0).getSize()){
					break;
				}
				
				for(int i = 0; i<items.size(); i++){
					int totalWeight = Knapsacks.get(0).getCurrentWeight() + items.get(i).getWeight();
					if(totalWeight<Knapsacks.get(0).getSize()){
						if(items.get(i).getRelativeValue()>bestValue){
						bestValueIndex = i;
						bestValue = items.get(i).getRelativeValue();						
						}
					}
					
				}
				
				System.out.println("\n");
				System.out.println("Adding item: " + items.get(bestValueIndex).getWeight());			
				Knapsacks.get(0).addItem(items.get(bestValueIndex));
				items.remove(bestValueIndex);
				System.out.println("current weight: " + Knapsacks.get(0).getCurrentWeight());
			}
			System.out.println("Done\n");
			printLayout();
	}	
	
	
	public void printLayout(){
		System.out.println("Knapsack - Size:" + Knapsacks.get(0).getSize() + " current weight: " + Knapsacks.get(0).getCurrentWeight());
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
			Item item = new Item("Item"+i,value, weight);
			items.add(item);
			value++;
			weight++;
		}
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Greedy run  = new Greedy();
	}
}
