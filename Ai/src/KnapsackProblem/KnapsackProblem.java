package KnapsackProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


public class KnapsackProblem {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Knapsack> Knapsacks = new ArrayList<Knapsack>();
	ArrayList<Item> itemsWithBestValue = new ArrayList<Item>();
	ArrayList<Item> allItems = new ArrayList<Item>();
	ArrayList<Knapsack> currentBestBags = new ArrayList<Knapsack>();
	int currentBestValue = 0;
	int value = 3;
	int weight = 1;
	boolean first = true;
	int threshold = 5;
	
	public KnapsackProblem(){
		
		
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
	
	public void getValues() {

		while (!(items.isEmpty())) {

			double currentBestValue = Integer.MIN_VALUE;
			;
			int currentBestIndex = 0;

			for (int i = 0; i < items.size(); i++) {

				if (items.get(i).getRelativeValue() > currentBestValue) {
					currentBestValue = items.get(i).getRelativeValue();
					currentBestIndex = i;
				}
			}

			itemsWithBestValue.add(items.get(currentBestIndex));
			items.remove(currentBestIndex);
		}

		for (int i = 0; i < itemsWithBestValue.size(); i++) {
			System.out.println("item:" + itemsWithBestValue.get(i).getName() + " weight: "
					+ itemsWithBestValue.get(i).getWeight() + "  " + itemsWithBestValue.get(i).getRelativeValue()
					+ " totalValue = " + itemsWithBestValue.get(i).getValue());
		}

		System.out.println();

	}
	
	public void neighborhoodSearch() {

		System.out.println("\nNBHS");

		ArrayList<Knapsack> originalBags = new ArrayList<>();;

		//Get the value and the lowest amount of items in all of the bags
		int currentKnapsacksValue = 0;
		int nbrOfItemsInKnapsack = Integer.MAX_VALUE;
		
		for(int i = 0; i < Knapsacks.size(); i++) {
			currentKnapsacksValue += Knapsacks.get(i).getValue();
			if(Knapsacks.get(i).getArrayList().size() < nbrOfItemsInKnapsack)
				nbrOfItemsInKnapsack = Knapsacks.get(i).getArrayList().size();		// VF GÖRS DETTA?
		}
		ArrayList<Knapsack> copyKnapsacks;
		ArrayList<Item> swapItems;
		
		
		
		// To get the original values into the model.
		if(first){
			for(int i = 0; i < Knapsacks.size(); i++) {
				Knapsack originalOne = new Knapsack(Knapsacks.get(i));
				originalBags.add(originalOne);
			}
			first = false;
			int check = 0;			
			currentBestBags.addAll(originalBags);
			for(int i = 0; i < originalBags.size(); i++) {
				check += originalBags.get(i).getValue();
			}			
			System.out.println("first bag values: " + check  + " OGbags size: " + currentBestBags.size());
			currentBestValue = check;
		}
		
		
		System.out.println("Original Knapsacks - best value = " + currentKnapsacksValue);
		//Looping through possible neighbors
		for(int gItem = 0; gItem < nbrOfItemsInKnapsack; gItem++) {
			copyKnapsacks = new ArrayList<>();
			swapItems = new ArrayList<>();
			//Copying the original knapsack and adding an item on index i to the swap list
			for(int i = 0; i < Knapsacks.size(); i++) {
				System.out.println("gITEM = " + gItem);
				Knapsack knapsack = new Knapsack(Knapsacks.get(i));
				copyKnapsacks.add(knapsack);
				Item item = copyKnapsacks.get(i).getItem(0);
				System.out.println("Adding item to swapList " + item.getName());
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
				else{
					// returns the item to list of the remaining items when not used in bags.
					itemsWithBestValue.add(itemToSwitch);

				}
				index--;
			}
			//Going through every knapsack and trying to add a brand new item from the remaining item list if there is room
			for (int j = 0; j < copyKnapsacks.size(); j++) {
				for (int i = 0; i < itemsWithBestValue.size(); i++) {
					Item item = itemsWithBestValue.get(i);
					if(copyKnapsacks.get(j).getCurrentWeight() + item.getWeight() <= copyKnapsacks.get(j).getSize()) {
						copyKnapsacks.get(j).addItem(item);
						
						// removes item from list of remaining items when that said item is placed inside a bag.
						itemsWithBestValue.remove(i);	
					}
				}
			}
			
			//Calculating the total value of the neighbor
			int thisTurnsValue = 0;
			
			for(int i = 0; i < copyKnapsacks.size(); i++) {
				thisTurnsValue += copyKnapsacks.get(i).getValue();
			
			}
			
			System.out.println("thisTurnsValue - " + thisTurnsValue);

			// prints this turns bags
			for(int i = 0; i < copyKnapsacks.size(); i++) {
				System.out.println("Value and Size of copy Knapsack - " + copyKnapsacks.get(i).getValue() + " - " + copyKnapsacks.get(i).getCurrentWeight());
			}
			
			
			//Checking if the this neighbor is the best neighbor at the moment			
			if(thisTurnsValue > currentBestValue ) {			
				System.out.println("NEW BEST!");
				currentBestValue = thisTurnsValue;
				currentBestBags.clear();
						
				// having to do this else, it will just be a copy of other lists and when they change, this one does aswell............
				for(int i = 0; i < copyKnapsacks.size(); i++) {
					Knapsack newOne = new Knapsack((copyKnapsacks.get(i).getSize()));
					for(int b = 0; b < copyKnapsacks.get(i).getArrayList().size(); b++) {
					newOne.addItem(copyKnapsacks.get(i).getItem(b));
					}
					currentBestBags.add(newOne);
				}
				
				System.out.println("currentBestBags: " + currentBestBags.get(0).getValue()  +"  best:  " + currentBestBags.get(1).getValue());
			}
			
			System.out.println("current: " + thisTurnsValue  +"  best:  " + currentBestValue);
		}
		
		//When we are done going through the neighbors 
		//We are checking if the best neighbor has better overall value than our current solution
		//If it has, we are making that neighbor our current solution and then recursively calling this method again
		if(currentBestValue > currentKnapsacksValue )	{			
			if(threshold<10){
				threshold++;
				System.out.println("Threshold: " + threshold);
				neighborhoodSearch();
			}	
			else{
				// ends the search and prints the results
				endOfNBHS();
			}
		}
		//Else we have found the best solution
		//Can be a local optima, fix with taboo search|
		else {
			if(threshold<10){
				threshold++;
				System.out.println("Threshold: " + threshold);
				neighborhoodSearch();
			}	
			else {
			endOfNBHS();
			}
		}
	}
	

	public void endOfNBHS(){
		
		System.out.println();
		System.out.println("No better solution");
		System.out.println("Best knapsack value = " + currentBestValue);
		
		for(int i = 0; i < currentBestBags.size(); i++) {
			System.out.println("Value and Size of the best Knapsack - " + currentBestBags.get(i).getValue() + " - " + currentBestBags.get(i).getCurrentWeight());
		}
	
		System.out.println("Items remainaing = " + itemsWithBestValue.size());	
		for(int i = 0; i<itemsWithBestValue.size(); i++){
			System.out.println("reamining items names "+ itemsWithBestValue.get(i).getName());
		}
		
	
		for(int i = 0; i<currentBestBags.size(); i++){
			printBag(currentBestBags.get(i));
		}			
	}
	
	
	// prints out the specific bags content
	public void printBag(Knapsack toPrint){
		System.out.println("---");
		for(int i = 0; i<toPrint.howManyItems(); i++){		
			System.out.println("Items in this bag "+ toPrint.getItem(i).getName());
		}
		System.out.println("---");
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
				
			System.out.println(String.format("%-3s %-3s %-3s", "finalWeight", "value", "nmbrOfItems"));
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
//		Knapsacks.add(knapsack3);
		
	}
	
	public void createItems() {

		Random rand = new Random();
		for(int i = 9; i>0; i--){
			items.add(new Item("Item"+i, rand.nextInt(15)+1, rand.nextInt(15)+1));
		}
		
//		items.add(new Item("Item1", 9, 4));
//		items.add(new Item("Item2", 8, 4));
//		items.add(new Item("Item3", 10, 7));
//		items.add(new Item("Item4", 5, 4));
//		items.add(new Item("Item5", 6, 5));
//		items.add(new Item("Item6", 12, 11));
//		items.add(new Item("Item7", 11, 15));
//		items.add(new Item("Item8", 4, 7));
//		items.add(new Item("Item9", 7, 14));
		// items.add(new Item("Item10",10, 7));
		// items.add(new Item("Item11",2, 2));
		// items.add(new Item("Item12",6, 10));
	}
	

	
	public static void main(String[] args) {
		KnapsackProblem run  = new KnapsackProblem();
	}
}
