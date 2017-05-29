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

		this.playerInventory = new Inventory(this.capacity);
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

	/**
	 * Check if the player has the key for the specified room
	 *
	 * @param roomID
	 * @return
	 */
	public boolean hasKeyForRoom(String roomID) {
		for (Item item : this.playerInventory.getInventory()) {
			if (item.getType() == ItemType.ITEM_TYPE_KEYS) {
				Keys keys = (Keys)item;
				if (keys.getRoom().equalsIgnoreCase(roomID)) {
					return true;
				}
			}
		}

		return false;
	}

	public void incrementHealth(int heal) {
		this.health += heal;
	}

	public void decrementHealth(int damage) {
		Armour armour = this.getUsableArmour();
		if (armour != null) this.health -= armour.reduce(damage);
		else this.health -= damage;
	}

	public Armour getUsableArmour() {
		for (Item i : this.getPlayerInventory().getInventory()) {
			if (i.getType() == ItemType.ITEM_TYPE_ARMOUR && i.isActive()) return (Armour) i;
		}
		return null;
	}

	public boolean isAlive() {
		return this.health > 0;
	}

	/**
	 * toString()
	 */
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