/**
 *
 */
package application;

/**
 * Keep the player info as the game is played
 *
 * @author Andrei Ivanovic
 *
 */
public class Player {

	public Player(int health, int capacity) {
		this.health = health;
		this.capacity = capacity;
		this.playerInventory = new Inventory();
	}
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	/**
	 * @return the weight
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the playerInventory
	 */
	public Inventory getPlayerInventory() {
		return playerInventory;
	}
	/**
	 * @param playerInventory the playerInventory to set
	 */
	public void setPlayerInventory(Inventory playerInventory) {
		this.playerInventory = playerInventory;
	}

	public String toString(){

		String print = String.format("Health: %d, Capacity: %d%n", health, capacity);

		for (Item i : this.playerInventory.getInventory()){
			print += (i.toString() + "\n");
		}

		return print;
	}

	// ------- fields

	private int health = 0;
	private int capacity;
	private Inventory playerInventory;

} // end Player