/**
 *
 */
package application;

/**
 * Key - item type, unlocking the rooms in the game
 *
 * @author Andrei Ivanovic
 *
 */
public class Keys extends Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param room
	 */
	public Keys(String name, int weight, String room) {
		super(name, weight, ItemType.ITEM_TYPE_KEYS);
		this.room = room;
	}
	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), this.room, 0, 0,0, 0, 1, 0, 0, 0);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("Keys - %s, weight: %s, room to open: %s",
				super.getName(), super.getWeight(), this.room);
	}

	private String room = null;

} //end Keys