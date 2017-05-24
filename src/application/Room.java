/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Room object
 *
 * @author Andrei Ivanovic
 *
 */
public class Room {

	/**
	 * C-tor
	 *
	 * @param name
	 */
	public Room(String id) {
		this.id = id;
		this.items = new ArrayList<Item>();
	}

	/**
	 * Get id of the room
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return the north
	 */
	public Room getNorth() {
		return north;
	}
	/**
	 * @param north the north to set
	 */
	public void setNorth(Room north) {
		this.north = north;
	}
	/**
	 * @return the south
	 */
	public Room getSouth() {
		return south;
	}
	/**
	 * @param south the south to set
	 */
	public void setSouth(Room south) {
		this.south = south;
	}
	/**
	 * @return the east
	 */
	public Room getEast() {
		return east;
	}
	/**
	 * @param east the east to set
	 */
	public void setEast(Room east) {
		this.east = east;
	}
	/**
	 * @return the west
	 */
	public Room getWest() {
		return west;
	}
	/**
	 * @param west the west to set
	 */
	public void setWest(Room west) {
		this.west = west;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void makeEntranceRoom() {
		this.entranceRoom = true;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the story
	 */
	public String getStory() {
		return story;
	}
	/**
	 * @param story the story to set
	 */
	public void setStory(String story) {
		this.story = story;
	}

	/**
	 * @return the story2
	 */
	public String getStory2() {
		return story2;
	}

	/**
	 * @param story2 the story2 to set
	 */
	public void setStory2(String story2) {
		this.story2 = story2;
	}

	/**
	 * @return the aiText
	 */
	public String getAiText() {
		return aiText;
	}
	/**
	 * @param aiText the aiText to set
	 */
	public void setAiText(String aiText) {
		this.aiText = aiText;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the entranceRoom
	 */
	public boolean isEntranceRoom() {
		return entranceRoom;
	}
	/**
	 * @return the firstTimeEntered
	 */
	public boolean isFirstTimeEntered() {
		return firstTimeEntered;
	}
	/**
	 * @param firstTimeEntered the firstTimeEntered to set
	 */
	public void setFirstTimeEntered(boolean firstTimeEntered) {
		this.firstTimeEntered = firstTimeEntered;
	}

	//construct the item you want in a room (you can add more if it is needed. e.g, Item itemFour)
	public void setItems(Item itemOne, Item ItemTwo, Item itemThree){
		items = new ArrayList<Item>();
		items.add(itemOne);
		items.add(ItemTwo);
		items.add(itemThree);
	}

	//remove an item from the room
	public void removeItem(Item item){
		int currentIndex = items.indexOf(item);
		items.remove(currentIndex);
	}

	// print the item you have in a room
	public String printItems(){
		String itemsDescription = "";
		for (int i = 0; i < items.size(); i++){
			if (items.get(i)!= null){
//				itemsDescription += items.get(i).getDescription()+" ";
			}
		}
		return itemsDescription;
	}

	/**
	 * @return the inspectItems
	 */
	public List<String> getInspectItems() {
		return inspectItems;
	}

	/**
	 * @param inspectItems the inspectItems to set
	 */
	public void setInspectItems(List<String> inspectItems) {
		this.inspectItems = inspectItems;
	}

	/**
	 * @return the inspectResponses
	 */
	public List<String> getInspectResponses() {
		return inspectResponses;
	}

	/**
	 * @param inspectResponses the inspectResponses to set
	 */
	public void setInspectResponses(List<String> inspectResponses) {
		this.inspectResponses = inspectResponses;
	}

	/**
	 * @return the characters
	 */
	public List<Character> getCharacters() {
		return characters;
	}

	//getter for the items list
	public List<Item> getItems(){
		return items;
	}

	// setter for the items list
	public void setItems(List<Item> itemsList) {
		this.items = itemsList;
	}

	// setter for the items list
	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	//check whether a room has item
	public boolean hasItems(){
		boolean hasItems = true;
		int count = 0;
		for(int i = 0; i <items.size();i++){
			if (items.get(i)==null){
				count++;
			}
		}
		if (count == items.size()){
			hasItems = false;
		}
		return hasItems;
	}

	/**
	 * toString() method
	 */
	public String toString() {

		String d = "";
		String s = "";

		if (this.description != null) {
			if (this.description.length() > 10) {
				d = this.description.substring(0, 10) + "...";
			} else
				d = this.description;
		}

		if (this.story != null) {
			if (this.story.length() > 10) {
				s = this.story.substring(0, 10) + "...";
			} else
				s = this.story;
		}

		return String.format("Room: (%s) %s - N: (%s) S: (%s) E: (%s) W: (%s) %s ==> desc [%s], story [%s]",
				this.id, this.name,
				this.north != null ? this.north.getName() : "",
				this.south != null ? this.south.getName() : "",
				this.east != null ? this.east.getName() : "",
				this.west != null ? this.west.getName() : "",
				this.entranceRoom ? ">> entrance room!" : "",
				d, s );
	}

	/**
	 * Print the state of the room (all details)
	 */
	public void print() {
		System.out.printf("---- ROOM: ID(%s), name: %s ----%n", this.id, this.name );
		System.out.printf("  Is this a starting room? - %s%n", this.entranceRoom ? "true" : "false");
		System.out.printf("  North exit to: %s%n", this.north != null ? this.north.getName() : "not an exit!");
		System.out.printf("  West exit to: %s%n", this.west != null ? this.west.getName() : "not an exit!");
		System.out.printf("  East exit to: %s%n", this.east != null ? this.east.getName() : "not an exit!");
		System.out.printf("  South exit to: %s%n", this.south != null ? this.south.getName() : "not an exit!");

		if (this.items != null) {
			System.out.printf("  Room inventory:%n");
			for (Item i : this.items){
				System.out.printf("    Item: %s%n", i.toString());
			}
		}
		else
			System.out.printf("  No room inventory!%n");

		if (this.characters != null) {
			System.out.printf("  Room characters:%n");
			for (Character c : this.characters){
				System.out.printf("    Character: %s%n", c.toString());
			}
		}
		else
			System.out.printf("  No characters in the room!%n");

		if (this.inspectItems != null) {
			System.out.printf("  Room inspect items:%n");
			int i = 0;
			for (String s : this.inspectItems){
				System.out.printf("    Inspect item: %s, response text size: %d%n", s, this.inspectResponses.get(i).length());
				++i;
			}
		}
		else
			System.out.printf("  No inspect items in the room!%n");

		String text = "";
		if (this.description != null) {
			if (this.description.length() > 16) {
				text = this.description.substring(0, 16) + "...";
			} else
				text = this.description;
		}
		System.out.printf("  Description text: [%s]%n", text);

		text = "";
		if (this.story != null) {
			if (this.story.length() > 16) {
				text = this.story.substring(0, 16) + "...";
			} else
				text = this.story;
		}
		System.out.printf("  Story text: [%s]%n", text);

		text = "";
		if (this.story2 != null) {
			if (this.story2.length() > 16) {
				text = this.story2.substring(0, 16) + "...";
			} else
				text = this.story2;
		}
		System.out.printf("  Story2 text: [%s]%n", text);

		text = "";
		if (this.message != null) {
			if (this.message.length() > 16) {
				text = this.message.substring(0, 16) + "...";
			} else
				text = this.message;
		}
		System.out.printf("  Message text: [%s]%n", text);

		text = "";
		if (this.aiText != null) {
			if (this.aiText.length() > 16) {
				text = this.aiText.substring(0, 16) + "...";
			} else
				text = this.aiText;
		}
		System.out.printf("  AI text: [%s]%n", text);
	}

	// ---------------------- fields

	private String id = null;
	private String name = null;

	private List<Item> items = null;
	private List<String> inspectItems = null;
	private List<String> inspectResponses = null;
	private List<Character> characters = null;

	private boolean entranceRoom = false;

	private Room north = null;
	private Room south = null;
	private Room east = null;
	private Room west = null;

	private boolean firstTimeEntered = false;

	private String description = null;
	private String story = null;
	private String story2 = null;
	private String aiText = null;
	private String message = null;

}	// end Room