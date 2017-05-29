/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Keys extends Item {

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

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), this.room, 0, 0,0, 0, 1, 0, 0, 0);
	}

	public String toString() {
		return String.format("Grenade - %s, weight: %s, room to open: %s",
				super.getName(), super.getWeight(), this.room);
	}

	private String room = null;

} //end Keys