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

	public Inventory(int maxCapacity){
		this.maxCapacityAllowed = maxCapacity;
		inventory = new ArrayList<Item>(); //construct the object
	}

	//how to add an item to a inventory
	public int addItem(Item item){
		if ((weight + item.getWeight()) < this.maxCapacityAllowed){ //IT IS A + NOT A "+="
			inventory.add(item);
			weight += item.getWeight();
			return 0;
		}else{
			return -1;
		}
	}
	public boolean canIAdd(Item item) {
		if ((weight + item.getWeight()) < this.maxCapacityAllowed){ //IT IS A + NOT A "+="
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
	public int getTotalInventoryWeight(){
		return weight;
	}

	//getter for the inventory arraylist
	public List<Item> getInventory(){
		return inventory;
	}

	public int getTotalInventorySize() {
		return this.inventory.size();
	}

	public int getTotalActiveInventorySize() {

		int totalActive = 0;
		for(Item item : this.inventory) {
			if (item.isActive()) ++totalActive;
		}

		return totalActive;
	}

	/**
	 * Get the item - user is counting from #1
	 *
	 * @param index
	 * @return
	 */
	public Item getItem(int index) {
		int ndx = index - 1;
		if (ndx < 0 || ndx >= this.inventory.size()) return null;

		return this.inventory.get(ndx);
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
	public int getMaxCapacityAllowed(){
		return this.maxCapacityAllowed;
	}

	// ------------- private fields

	private List<Item> inventory;

	private int weight = 0;
	private int currentIndex;

	private final int maxCapacityAllowed; // the max weight a character can take

} // end Inventory