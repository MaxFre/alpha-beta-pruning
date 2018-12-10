package KnapsackProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import KnapsackProblem.FractionalKnapSack.ItemValue;

public class Greedy {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Knapsack> Knapsacks = new ArrayList<Knapsack>();
	ArrayList<Item> itemsWithBestValue = new ArrayList<Item>();
	
	int value = 3;
	int weight = 1;
	
	
	
	public Greedy(){
		
		
		createItems();
		createKnapsack();
		getValues();
		
		
		
		greedyAlgortihm();
		neighborhoodSearch();
	}
	
	public void getValues(){
		
	
		

		
		while(!(items.isEmpty())){		
			
			double currentBestValue = Integer.MIN_VALUE;;
			int currentBestIndex = 0;
			
			for(int i = 0; i<items.size(); i++){
				
				if(items.get(i).getRelativeValue()>currentBestValue){
					currentBestValue = items.get(i).getRelativeValue();
					currentBestIndex = i;
				}
			}
			
			itemsWithBestValue.add(items.get(currentBestIndex));
			items.remove(currentBestIndex);
		}
		
		
		for(int i = 0; i<itemsWithBestValue.size(); i++){
			System.out.println("item:" + itemsWithBestValue.get(i).getName() + " weight: " + itemsWithBestValue.get(i).getWeight() + "  " + itemsWithBestValue.get(i).getRelativeValue()
					+ " totalValue = " +  itemsWithBestValue.get(i).getValue());
		}
		
		System.out.println();
		
	}
	
	
	public void neighborhoodSearch(){
		int limit  = 15;
		int current  = 0;
		
		ArrayList<Item> first = Knapsacks.get(0).getArrayList();
		ArrayList<Item> second = Knapsacks.get(1).getArrayList();
		
		ArrayList<Item> listbestSofar = Knapsacks.get(0).getArrayList();
		
		int finalWeight = 0;
		double value = 0;
		int nmbrOfItems = first.size();
		
		double bestSofar = Knapsacks.get(0).getValue();
		int weightlimit =   Knapsacks.get(0).getSize();
		
		while(limit>current){
			finalWeight = 0;
			value = 0;
			nmbrOfItems = first.size();
			try {
				int getRidWith = 0;
			
				itemsWithBestValue.add(first.get(getRidWith));
				first.remove(getRidWith);
			
				Random rand = new Random();
				int what = rand.nextInt(itemsWithBestValue.size());
				first.add(itemsWithBestValue.get(what));
				itemsWithBestValue.remove(what);
				
			
				
				for(int i = 0; i<first.size(); i++){
					
					value += first.get(i).getValue();
					finalWeight += first.get(i).getWeight();
				}
				
				if(bestSofar<value && finalWeight<=weightlimit) {
					bestSofar = value;
					listbestSofar = first;
					System.out.println( "new best!");
				}
		
				
				System.out.println();
				System.out.println(String.format("%-10s %-10s %-10s", "finalWeight", "value", "nmbrOfItems"));
				System.out.println(String.format("%-10s %-10s %-10s", finalWeight, value, nmbrOfItems));
				getRidWith++;
				current++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("BEST THING WE FOUND");
		System.out.println(String.format("%-10s %-10s %-10s", "finalWeight", "value", "nmbrOfItems"));
		System.out.println(String.format("%-10s %-10s %-10s", bestSofar, value, nmbrOfItems));
		
	}
	
	
	public void greedyAlgortihm(){

		for(int j = 0; j < Knapsacks.size(); j++){
		for(int i = 0; i < itemsWithBestValue.size(); i++){		
			
				if(Knapsacks.get(j).getCurrentWeight() < Knapsacks.get(j).getSize()){
					if(Knapsacks.get(j).getCurrentWeight() + itemsWithBestValue.get(i).getWeight() <= Knapsacks.get(j).getSize()){
						Knapsacks.get(j).addItem(itemsWithBestValue.get(i));
						System.out.println("adding item: " + itemsWithBestValue.get(i).getName() + "   "
								+ itemsWithBestValue.get(i).getRelativeValue() + "  "
								+ itemsWithBestValue.get(i).getWeight() + " to Knapsacks " + j
								+ " current weight: " + Knapsacks.get(j).getCurrentWeight());
						itemsWithBestValue.remove(i);
					}
				}
			}
		}
			
			
			
		System.out.println();
		for(int i = 0; i<Knapsacks.size(); i++){
//			System.out.println("finalweight:" +	Knapsacks.get(i).getCurrentWeight() + " value " + Knapsacks.get(i).getValue()  + "  current weight:  " + Knapsacks.get(i).getCurrentWeight() +" ITEMS in "+ Knapsacks.get(i).getArrayList().size());
		
			
			int finalWeight = Knapsacks.get(i).getCurrentWeight();
			double value =  Knapsacks.get(i).getValue();
			int nmbrOfItems = Knapsacks.get(i).getArrayList().size();
				
			System.out.println(String.format("%-10s %-10s %-10s", "finalWeight", "value", "nmbrOfItems"));
			System.out.println(String.format("%-10s %-10s %-10s", finalWeight, value, nmbrOfItems));
			System.out.println();
		}
		
		
		System.out.println();
		for(int i = 0; i<itemsWithBestValue.size(); i++){
			System.out.println("REMAINING item:" + itemsWithBestValue.get(i).getName() + " weight: " + itemsWithBestValue.get(i).getWeight() + "  " + itemsWithBestValue.get(i).getRelativeValue()
					+ " totalValue = " +  itemsWithBestValue.get(i).getValue());
		}	
	}	
	
	

	
	
	public void createKnapsack(){
		Knapsack knapsack = new Knapsack(30);
		Knapsack knapsack2 = new Knapsack(15);
//		Knapsack knapsack3 = new Knapsack(20);
		Knapsacks.add(knapsack);
		Knapsacks.add(knapsack2);
//		Knapsacks.add(knapsack3);
		
	}
	
	public void createItems(){
//				item:Item7 weight: 4  2.25 totalValue = 9
//				item:Item6 weight: 4  2.0 totalValue = 8
//				item:Item8 weight: 7  1.4285714285714286 totalValue = 10
//				item:Item3 weight: 4  1.25 totalValue = 5
//				item:Item4 weight: 5  1.2 totalValue = 6
//				item:Item10 weight: 11  1.0909090909090908 totalValue = 12
//				item:Item9 weight: 15  0.7333333333333333 totalValue = 11
//				item:Item2 weight: 7  0.5714285714285714 totalValue = 4
//				item:Item5 weight: 14  0.5 totalValue = 7
//				item:Item1 weight: 13  0.23076923076923078 totalValue = 3
				
			
				
				items.add(new Item("Item7",9, 4));
				items.add(new Item("Item6",8, 4));
				items.add(new Item("Item8",10, 7));
				items.add(new Item("Item3",5, 4));
				items.add(new Item("Item4",6, 5));
				items.add(new Item("Item10",12, 11));
				items.add(new Item("Item9",11, 15));
				items.add(new Item("Item2",4, 7));
				items.add(new Item("Item5",7, 14));
				items.add(new Item("Item1",3, 13));
			
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Greedy run  = new Greedy();
	}
}
