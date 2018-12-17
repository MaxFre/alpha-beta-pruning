package KnapsackProblem;

public class test {
	
//	items.add(new Item("Item7",9, 4));
//	items.add(new Item("Item6",8, 4));
//	items.add(new Item("Item8",10, 7));
//	items.add(new Item("Item3",5, 4));
//	items.add(new Item("Item4",6, 5));
//	items.add(new Item("Item10",12, 11));
//	items.add(new Item("Item9",11, 15));
//	items.add(new Item("Item2",4, 7));
//	items.add(new Item("Item5",7, 14));
//	items.add(new Item("Item1",3, 13));

	
	static int[] values = new int[] {9, 8, 10, 5, 6, 12, 11, 4, 7, 3};
	static int[] weights = new int[] {4,4,7,4,5,11,15,7,14,13};
	static int W = 30;

	private static int knapsack(int i, int W) {
	    if (i < 0) {
	        return 0;
	    }
	    if (weights[i] > W) {
	    	 
	        return knapsack(i-1, W);
	      
	    } else {
	    	System.out.println("add " + W);
	        return Math.max(knapsack(i-1, W), knapsack(i-1, W - weights[i]) + values[i]);
	    }
	}

	public static void main(String[] args) {
	System.out.println(knapsack(values.length - 1, W));}
}
