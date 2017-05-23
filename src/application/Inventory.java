/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Ivanovic
 *
 */
public class Inventory {

	/**
	 * C-tor
	 */
	public Inventory(){
		inventory = new ArrayList<Item>(); //construct the object
	}

	//how to add an item to a inventory
	public int addItem(Item item){
		if ((weight + item.getWeight())<MAXVALUE){ //IT IS A + NOT A "+="
			inventory.add(item);
			weight += item.getWeight();
			return 0;
		}else{
			return -1;
		}
	}
	public boolean canIAdd(Item item) {
		if ((weight + item.getWeight())<MAXVALUE){ //IT IS A + NOT A "+="
			return true;
		}else{
			return false;
		}
	}
	//how to remove an item
	public void removeItem(Item item){
		currentIndex = inventory.indexOf(item);
		inventory.remove(currentIndex);
		weight -= item.getWeight();
	}

	//the weight that you currently are carrying
	public int getWeight(){
		return weight;
	}

	//getter for the inventory arraylist
	public List<Item> getInventory(){
		return inventory;
	}

	//getter for the number of inventory
	public int getNumItems(){
		return inventory.size();
	}

	//print out the inventory you have
	public String print(){
		String words = "";
		for (int i = 0; i<inventory.size(); i ++){
			if (inventory.get(i)!=null){
//				words += inventory.get(i).getDescription() + "\n";
			}
		}
		return words;
	}

	//get the max weight you can carry
	public int getCapacity(){
		return MAXVALUE;
	}

	// ------------- private fields

	private List<Item> inventory;

	private int weight = 0;
	private int currentIndex;

	private final int MAXVALUE = 10; // the max weight a character can take

} // end Inventory