/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Player's inventory (borrowed from school code)
 *
 * @author Andrei Ivanovic
 *
 */
public class Inventory {

	/**
	 * Constructor
	 *
	 * @param maxCapacity
	 */
	public Inventory(int maxCapacity){
		this.maxCapacityAllowed = maxCapacity;
		inventory = new ArrayList<Item>(); //construct the object
	}

	/**
	 * how to add an item to a inventory
	 *
	 * @param item
	 * @return
	 */
	public int addItem(Item item) {
		if ((weight + item.getWeight()) < this.maxCapacityAllowed){ //IT IS A + NOT A "+="
			inventory.add(item);
			weight += item.getWeight();
			return 0;
		}else{
			return -1;
		}
	}

	/**
	 * Check if item can be added to Player's inventory
	 *
	 * @param item
	 * @return
	 */
	public boolean canIAdd(Item item) {
		if ((weight + item.getWeight()) < this.maxCapacityAllowed){ //IT IS A + NOT A "+="
			return true;
		}else{
			return false;
		}
	}

	/**
	 * How to remove an item
	 *
	 * @param item
	 * @return
	 */
	public Item removeItem(Item item) {
		currentIndex = inventory.indexOf(item);
		inventory.remove(currentIndex);
		weight -= item.getWeight();

		return item;
	}

	/**
	 * Remove an item with the index #
	 *
	 * @param index
	 * @return
	 */
	public Item removeItemByIndex(int index) {
		currentIndex = index;
		Item item = this.inventory.get(index);
		inventory.remove(currentIndex);
		weight -= item.getWeight();

		return item;
	}

	/**
	 * Simple remove entire inventory from the player (drop all)
	 */
	public void removeAllInventory() {
		this.inventory.clear();
		this.weight = 0;
	}

	/**
	 * the weight that you currently are carrying
	 *
	 * @return
	 */
	public int getTotalInventoryWeight(){
		return weight;
	}

	/**
	 * getter for the inventory arraylist
	 *
	 * @return
	 */
	public List<Item> getInventory(){
		return inventory;
	}

	/**
	 * Total inventory size: both active and inactive items
	 *
	 * @return
	 */
	public int getTotalInventorySize() {
		return this.inventory.size();
	}

	/**
	 * Counts only active player's items inventory
	 *
	 * @return
	 */
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

	/**
	 * Print out the inventory you have
	 *
	 * @return
	 */
	public String print(){
		String words = "";
		for (int i = 0; i<inventory.size(); i ++){
			if (inventory.get(i)!=null){
//				words += inventory.get(i).getDescription() + "\n";
			}
		}
		return words;
	}

	/**
	 * Get the max weight you can carry
	 *
	 * @return
	 */
	public int getMaxCapacityAllowed(){
		return this.maxCapacityAllowed;
	}

	// ------------- private fields

	private List<Item> inventory;

	private int weight = 0;
	private int currentIndex;

	private final int maxCapacityAllowed; // the max weight a character can take

} // end Inventory