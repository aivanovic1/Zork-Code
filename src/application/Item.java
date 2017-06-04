/**
 *
 */
package application;

/**
 * Base class for Item
 *
 * @author Andrei Ivanovic
 *
 */

public class Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param type
	 */
	public Item(String name, int weight, ItemType type) {
		this.name = name;
		this.weight = weight;
		this.type = type;
		this.active = true;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Get the Item type
	 * @return
	 */
	public ItemType getType() {
		return type;
	}

	/**
	 * De-activate the Item
	 */
	public void deactivate() {
		this.active = false;
	}

	/**
	 * Activate the Item
	 */
	public void activate() {
		this.active = true;
	}

	/**
	 * Check if item is active
	 *
	 * @return
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Base funcstion that is overwritten by extending item classes (weapons, keys, medicine, ...)
	 *
	 * @param player
	 * @return
	 */
	public Damage use(Player player) {
		return new Damage(0);
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, 0, 0, 1, 0, 0, 0);
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return String.format("Item - %s, weight: %d, type: %s, active: %s", this.name, this.weight, this.type, Boolean.toString(this.active));
	}

	private boolean active = true;
	private String name = null;
	private int weight = 0;
	private ItemType type = ItemType.ITEM_TYPE_NONE;

} // end Item