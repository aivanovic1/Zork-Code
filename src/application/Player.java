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

	/**
	 * Simple player constructor
	 *
	 * @param health
	 * @param capacity
	 */
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
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	/**
	 * Check if the player has the required number of keys for the specified room
	 *
	 * @param roomID
	 * @return
	 */
	public boolean hasKeyForRoom(String roomID, int totalKeysRequired) {

		int countKeys = 0;

		for (Item item : this.playerInventory.getInventory()) {
			if (item.getType() == ItemType.ITEM_TYPE_KEYS) {
				Keys keys = (Keys)item;
				if (keys.getRoom().equalsIgnoreCase(roomID)) {
					++countKeys;
				}
			}
		}

		return countKeys >= totalKeysRequired;
	}

	/**
	 * Heal - add some health points
	 *
	 * @param heal
	 */
	public void incrementHealth(int heal) {
		this.health += heal;
	}

	/**
	 * Decrement health (you got shot!)
	 *
	 * @param damage
	 */
	public void decrementHealth(int damage) {
		Armour armour = this.getUsableArmour();
		if (armour != null) this.health -= armour.reduce(damage);
		else this.health -= damage;
	}

	/**
	 * Pick the armour
	 *
	 * @return
	 */
	public Armour getUsableArmour() {
		for (Item i : this.getPlayerInventory().getInventory()) {
			if (i.getType() == ItemType.ITEM_TYPE_ARMOUR && i.isActive()) return (Armour) i;
		}
		return null;
	}

	/**
	 * Check if player is still alive
	 *
	 * @return
	 */
	public boolean isAlive() {
		return this.health > 0;
	}

	/**
	 * toString()
	 */
	public String toString(){

		String print = String.format("Health: %d, weight: %d, capacity: %d%n", health, this.playerInventory.getTotalInventoryWeight(), capacity);

		for (Item i : this.playerInventory.getInventory()){
			print += (i.toString() + "\n");
		}

		if (this.imageFileName != null) print += String.format("  Player's image file: %s%n", this.imageFileName);
		else print += String.format("  Player's image file: N/A%n");

		return print;
	}

	// ------- fields

	private int health = 0;
	private int capacity;

	private String imageFileName = null;

	private Inventory playerInventory;

} // end Player