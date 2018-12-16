package KnapsackProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import KnapsackProblem.FractionalKnapSack.ItemValue;

public class Greedy {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Knapsack> Knapsacks = new ArrayList<Knapsack>();
	ArrayList<Item> itemsWithBestValue = new ArrayList<Item>();
	ArrayList<Item> allItems = new ArrayList<Item>();
	
	int value = 3;
	int weight = 1;
	
	
	
	public Greedy(){
		
		
		createItems();
		createKnapsack();
		getValues();
		
		
		
		greedyAlgortihm();
		neighborhoodSearch();
	}
	
	
	
	public void printList(Knapsack current){
		
		ArrayList<Item> list =  current.getArrayList();
				
		System.out.println("printing ");
		for(int i = 0; i<list.size(); i++){
			System.out.println("item: " + list.get(i).getName() + "   weight: " + list.get(i).getWeight() + "   value: " + list.get(i).getValue());
		}
			
		System.out.println("bag value: " + current.getValue()  + " bag current weight: " + current.getCurrentWeight() + " bag limit: " + current.getSize());
		
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
	
	public void neighborhoodSearch() {

		System.out.println("NBHS");
		
		//Get the value and the lowest amount of items in all of the bags
		int currentKnapsacksValue = 0;
		int nbrOfItemsInKnapsack = Integer.MAX_VALUE;
		for(int i = 0; i < Knapsacks.size(); i++) {
			currentKnapsacksValue += Knapsacks.get(i).getValue();
			if(Knapsacks.get(i).getArrayList().size() < nbrOfItemsInKnapsack)
				nbrOfItemsInKnapsack = Knapsacks.get(i).getArrayList().size();
		}
		ArrayList<Knapsack> copyKnapsacks;
		ArrayList<Item> swapItems;
		ArrayList<Knapsack> bestNewKnapsacks = new ArrayList<>();
		int bestNewKnapsacksValue = 0;
		
		System.out.println("Original Knapsacks - best value = " + currentKnapsacksValue);
		//Looping through possible neighbors
		for(int gItem = 0; gItem < nbrOfItemsInKnapsack; gItem++) {
			copyKnapsacks = new ArrayList<>();
			swapItems = new ArrayList<>();
			//Copying the original knapsack and adding an item on index i to the swap list
			for(int i = 0; i < Knapsacks.size(); i++) {
				Knapsack knapsack = Knapsacks.get(i);
				copyKnapsacks.add(knapsack);
				Item item = copyKnapsacks.get(i).getArrayList().get(gItem);
				swapItems.add(item);
				copyKnapsacks.get(i).removeItem(0);
			}
			//Swaping items in the bags, i starts at 0 but index for getting the items starts at swapItems size -1
			//This way we are going forward on the knapsacks but backwards on the items thus shuffling the items
			//And not just adding it to the same knapsack it was taken from
			int index = swapItems.size()-1;
			for(int i = 0; i < copyKnapsacks.size() ; i++) {
				Item itemToSwitch = swapItems.get(index);
				if(copyKnapsacks.get(i).getCurrentWeight() + itemToSwitch.getWeight() <= copyKnapsacks.get(i).getSize()) {
					copyKnapsacks.get(i).addItem(itemToSwitch);
				}
				index--;
			}
			//Going through every knapsack and trying to add a brand new item from the remaining item list if there is room
			for (int j = 0; j < copyKnapsacks.size(); j++) {
				for (int i = 0; i < itemsWithBestValue.size(); i++) {
					Item item = itemsWithBestValue.get(i);
					if(copyKnapsacks.get(j).getCurrentWeight() + item.getWeight() <= copyKnapsacks.get(j).getSize()) {
						copyKnapsacks.get(j).addItem(item);
						
						//DENNA VET JAG INTE OM MAN SKA HA MED??
						//itemsWithBestValue.remove(i);
	
					}
				}
			}
			//Calculating the total value of the neighbor
			int copyKnapsacksValue = 0;
			for(int i = 0; i < copyKnapsacks.size(); i++) {
				copyKnapsacksValue += copyKnapsacks.get(i).getValue();
			}
			
			System.out.println("Copy Knapsacks - best value = " + copyKnapsacksValue);

			
			for(int i = 0; i < copyKnapsacks.size(); i++) {
				System.out.println("Value and Size of copy Knapsack - " + copyKnapsacks.get(i).getValue() + " - " + copyKnapsacks.get(i).getCurrentWeight());
			}
			//Checking if the this neighbor is the best neighbor at the moment
			if(copyKnapsacksValue > bestNewKnapsacksValue && copyKnapsacksValue > currentKnapsacksValue) {
				bestNewKnapsacksValue = copyKnapsacksValue;
				bestNewKnapsacks = copyKnapsacks;
			}
		}
		//When we are done going through the neighbors 
		//We are checking if the best neighbor has better overall value than our current solution
		//If it has, we are making that neighbor our current solution and then recursively calling this method again
		if(bestNewKnapsacksValue > currentKnapsacksValue )	{
			Knapsacks = bestNewKnapsacks;
			neighborhoodSearch();
		}
		//Else we have found the best solution
		//Can be a local optima, fix with taboo search|
		else {
			System.out.println();
			System.out.println("No better solution");
			System.out.println("Best knapsack value = " + currentKnapsacksValue);
			
			for(int i = 0; i < Knapsacks.size(); i++) {
				System.out.println("Value and Size of the best Knapsack - " + Knapsacks.get(i).getValue() + " - " + Knapsacks.get(i).getCurrentWeight());
			}
		
			System.out.println("Items remainaing = " + itemsWithBestValue.size());
		}
		
//		ArrayList<Item> first = Knapsacks.get(0).getArrayList();
//		ArrayList<Item> second = Knapsacks.get(1).getArrayList();
//
//		
//		
////		Knapsack first1 = new Knapsack(Knapsacks.get(0).getSize());
////		Knapsack second1 = new Knapsack(Knapsacks.get(1).getSize());
//		
//		Knapsack newKnapsacK1 = new Knapsack(Knapsacks.get(0));
//		Knapsack newKnapsack2 = new Knapsack(Knapsacks.get(1));
//		
//		
//		for (int i = 0; i < first.size(); i++) {
//			
//			Item swapFromFirst = newKnapsacK1.getItem(i);
//			
//			for (int b = 0; b < second.size(); b++) {
//							
//				Item swapFromSecond = newKnapsack2.getItem(b);
//				
//				
//				int firstBagsCurrentSize = newKnapsacK1.getCurrentWeight();
//				int SecondBagsCurrentSize = newKnapsack2.getCurrentWeight();
//
//				int newFirstWeight = firstBagsCurrentSize+swapFromSecond.getWeight()- swapFromFirst.getWeight();
//				int newSecondWeight = SecondBagsCurrentSize+swapFromFirst.getWeight()- swapFromSecond.getWeight();
//				
//				if(newFirstWeight<newKnapsacK1.getSize() && newSecondWeight<newKnapsack2.getSize()){
//					
//					newKnapsacK1.removeItem(i);
//					newKnapsack2.removeItem(b);
//					
//					newKnapsacK1.addItem(swapFromSecond);
//					newKnapsack2.addItem(swapFromFirst);
//										
//					System.out.println("\nchange took place");
//					System.out.println("newFirst size: " + newKnapsacK1.getCurrentWeight() + "  value: " + newKnapsacK1.getValue());
//					System.out.println("newSecond size: " + newKnapsack2.getCurrentWeight() + "  value: " + newKnapsack2.getValue());
//					
//					
//					for (int j = 0; j < itemsWithBestValue.size(); j++) {
//						
//						Item currentFromList = itemsWithBestValue.get(j);
//						
//						if(newKnapsacK1.getCurrentWeight()+currentFromList.getWeight()<newKnapsacK1.getSize()){
//							System.out.println("\nAdding new Item to 1, item: " + currentFromList.getName());		
//							System.out.println("newKnapsacK1 size: " + newKnapsacK1.getCurrentWeight() + "  value: " + newKnapsacK1.getValue());
//							newKnapsacK1.addItem(currentFromList);
//							printList(newKnapsacK1);
//						}
//						
//						if(newKnapsack2.getCurrentWeight()+currentFromList.getWeight()<newKnapsack2.getSize()){
//							System.out.println("\nAdding new Item to , item: " + currentFromList.getName());
//							System.out.println("newKnapsack2 size: " + newKnapsack2.getCurrentWeight() + "  value: " + newKnapsack2.getValue());
//							newKnapsack2.addItem(currentFromList);
//							printList(newKnapsack2);
//						}
//					}
//					
//				}
//			}	
//		}
	}
	
	public void greedyAlgortihm() {

		for (int j = 0; j < Knapsacks.size(); j++) {
			for (int i = 0; i < itemsWithBestValue.size(); i++) {

				if (Knapsacks.get(j).getCurrentWeight() + itemsWithBestValue.get(i).getWeight() <= Knapsacks.get(j)
						.getSize()) {
					Knapsacks.get(j).addItem(itemsWithBestValue.get(i));
					System.out.println("adding item: " + itemsWithBestValue.get(i).getName() + "   "
							+ itemsWithBestValue.get(i).getRelativeValue() + "  "
							+ itemsWithBestValue.get(i).getWeight() + " to Knapsacks " + j + " current weight: "
							+ Knapsacks.get(j).getCurrentWeight());
					itemsWithBestValue.remove(i);
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
		Knapsack knapsack3 = new Knapsack(20);
		Knapsacks.add(knapsack);
		Knapsacks.add(knapsack2);
		Knapsacks.add(knapsack3);
		
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
				
			
				
				items.add(new Item("Item1",9, 4));
				items.add(new Item("Item2",8, 4));
				items.add(new Item("Item3",10, 7));
				items.add(new Item("Item4",5, 4));
				items.add(new Item("Item5",6, 5));
				items.add(new Item("Item6",12, 11));
				items.add(new Item("Item7",11, 15));
				items.add(new Item("Item8",4, 7));
				items.add(new Item("Item9",7, 14));
				items.add(new Item("Item10",10, 7));
				items.add(new Item("Item11",2, 2));
				items.add(new Item("Item12",6, 10));
				
			
				
				
				
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Greedy run  = new Greedy();
	}
}
