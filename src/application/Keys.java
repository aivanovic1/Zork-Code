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
		super(name, weight);
		this.room = room;
	}
	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	public String toString() {
		return String.format("Grenade - %s, weight: %s, room to open: %s",
				super.getName(), super.getWeight(), this.room);
	}

	private String room = null;

} //end Keys