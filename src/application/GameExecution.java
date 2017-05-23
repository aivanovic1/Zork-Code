/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * This is Commands Parser (runs the game)
 *
 * @author Andrei Ivanovic
 *
 */
public class GameExecution {

	/**
	 * C-tor
	 *
	 * @param startRoom
	 */
	public GameExecution(Room startRoom, Player player, ObservableList history) {
		if (startRoom != null) {
			this.currentRoom = startRoom;
			this.path.add(this.currentRoom);
		}

		this.history = history;
		this.player = player;

		this.initializeCommands();
	}

	/**
	 * Validate if user input is valid command (does the mapping using the commandsMap)
	 *
	 * @param command
	 * @return
	 */
	public Command validateCommand(String entry) {

		if (entry == null || entry.trim().isEmpty()) return null;

    	String cleanedCommand = "";
    	String[] words = entry.trim().split(" ");

    	List<String> tempArray = new ArrayList<String>();

    	for(String w : words) {
    		if (w.isEmpty()) continue;
    		if (!cleanedCommand.isEmpty()) cleanedCommand += " ";
    		cleanedCommand += w.trim();
    		tempArray.add(w.trim());
    	}

    	Command command = null;
    	if (this.commandsMap.get(cleanedCommand.toLowerCase()) != null){
    		command = new Command(this.commandsMap.get(cleanedCommand.toLowerCase()));
    	}
    	else {

    		if (this.commandsMap.get(tempArray.get(0).toLowerCase()) != null){
        		command = new Command(this.commandsMap.get(tempArray.get(0).toLowerCase()));
        	}
    		else
    			command = new Command(tempArray.get(0));

    		for (int i = 1; i < tempArray.size(); i++){
    			command.addParam(tempArray.get(i));
    		}
    	}

    	return command;
	}

	/**
	 * Play the game
	 *
	 * @param command
	 */
	public void playGame(Command command) {

		if (command == null) return;
		String commandVerb = command.getVerb().toLowerCase();

		if (commandVerb.startsWith("go ")) {
			move(commandVerb);
		} else if (commandVerb.startsWith("take")) {
			take(command);
		} else if (commandVerb.startsWith("drop")) {

		} else if (commandVerb.startsWith("check")) {
			check(commandVerb);
		} else if (commandVerb.startsWith("print")) {
			print(commandVerb);
		} else if (commandVerb.startsWith("player")) {
			printPlayer(commandVerb);
		}
	}

	/**
	 * Processes playerPrint command
	 *
	 * @param command
	 */
	private void printPlayer(String commandEntered) {
		System.out.println(this.player.toString());
	}

	/**
	 * Processes print command
	 *
	 * @param command
	 */
	private void print(String commandEntered) {
		String message = currentRoom.toString();
		currentRoom.print();

		this.history.add(message);
	}

	/**
	 * Processes take command
	 *
	 * @param command
	 */
	private void take(Command command) {
		String message = null;

		if (this.currentRoom.getItems() == null || this.currentRoom.getItems().isEmpty()){
			message = "ANG3L: There are no items in this room!";
			this.history.add(message);
			return;
		}
		int itemNumber = -1;

		try {
			itemNumber = Integer.parseInt(command.getParam(1))-1;

			if (this.player.getPlayerInventory().canIAdd(this.currentRoom.getItems().get(itemNumber))) {
				Item item = this.currentRoom.getItems().remove(itemNumber);
				this.player.getPlayerInventory().addItem(item);

				message = "ANG3L: You took: " + item.getName();
			}
			else message = "ANG3L: That's too heavy to take!";

		} catch (NumberFormatException e) {

			if ("all".equalsIgnoreCase(command.getParam(1))){
				message = "ANG3L: You took: \n";
				boolean capacityReached = false;
				for (int i = this.currentRoom.getItems().size()-1; i >= 0; i--){

					Item item = this.currentRoom.getItems().get(i);

					if (this.player.getPlayerInventory().canIAdd(item)) {
						message += (item.toString() + "\n");
						this.player.getPlayerInventory().addItem(this.currentRoom.getItems().remove(i));
					}
					else
						capacityReached = true;
				}
				if (capacityReached) message += "ANG3L: You've taken the most items you could take.";

			}
			else {
				boolean nameNotFound = true;
				int i = 0;

				while(nameNotFound && i < this.currentRoom.getItems().size()) {
					if (this.currentRoom.getItems().get(i).getName().equalsIgnoreCase(command.generateFullCommandNoVerb())) nameNotFound = false;
					else i++;
				}

				if (!nameNotFound) {

					Item item = this.currentRoom.getItems().get(i);

					if (this.player.getPlayerInventory().canIAdd(item)) {
						this.player.getPlayerInventory().addItem(this.currentRoom.getItems().remove(i));

						message = "ANG3L: You took: " + item.getName();
					}
					else message = "ANG3L: That's too heavy to take!";
				}
				else {
					message = "ANG3L: I'm not sure that item exists...";
				}
			}
 		}

		this.history.add(message);
	}

	/**
	 * Processes check command
	 *
	 * @param commandEntered
	 */
	private void check(String commandEntered) {
		String message = null;

		if (this.currentRoom.getItems() == null || this.currentRoom.getItems().size() == 0) message = "ANG3L: There's nothing to take in this room.";
		else{
			message = "ANG3L: You can take the following items: ";

			for (int i = 0; i < this.currentRoom.getItems().size(); i++){
				if (i == 0) message += this.currentRoom.getItems().get(i).getName() + " (" + (i+1) + ")";
				else message += ", " + this.currentRoom.getItems().get(i).getName() + " (" + (i+1) + ")";
			}
		}
		this.history.add(message);
	}

	/**
	 * Movements control
	 *
	 * @param command
	 */
	private void move(String command) {
		String direction = command.substring(3);

		String message = null;

		if (direction.equalsIgnoreCase("north")) {
			if (this.currentRoom.getNorth() != null) {
				this.currentRoom = this.currentRoom.getNorth();
				message = ">> going north! Entered room: " + this.currentRoom.getName();
			} else {
				message = ">> going north! No room found! Back to: " + this.currentRoom.getName();
			}
		}
		else if (direction.equalsIgnoreCase("south")) {
			if (this.currentRoom.getSouth() != null) {
				this.currentRoom = this.currentRoom.getSouth();
				message = ">> going south! Entered room: " + this.currentRoom.getName();
			} else {
				message = ">> going south! No room found! Back to: " + this.currentRoom.getName();
			}
		}
		else if (direction.equalsIgnoreCase("east")) {
			if (this.currentRoom.getEast() != null) {
				this.currentRoom = this.currentRoom.getEast();
				message = ">> going east! Entered room: " + this.currentRoom.getName();
			} else {
				message = ">> going east! No room found! Back to: " + this.currentRoom.getName();
			}
		}
		else if (direction.equalsIgnoreCase("west")) {
			if (this.currentRoom.getWest() != null) {
				this.currentRoom = this.currentRoom.getWest();
				message = ">> going west! Entered room: " + this.currentRoom.getName();
			} else {
				message = ">> going west! No room found! Back to: " + this.currentRoom.getName();
			}
		}

		this.history.add(message);

		// display room description text
		if (this.currentRoom.getDescription() != null) {
			this.history.add(this.currentRoom.getDescription());
		}
	}

	/**
	 * Initialize main data structures for playing the game
	 */
	private void initializeCommands() {
   		this.commandsMap.put("go north", "Go North"); this.commandsMap.put("go n", "Go North"); this.commandsMap.put("north", "Go North"); this.commandsMap.put("n", "Go North");
   		this.commandsMap.put("go south", "Go South"); this.commandsMap.put("go s", "Go South"); this.commandsMap.put("south", "Go South"); this.commandsMap.put("s", "Go South");
   		this.commandsMap.put("go west", "Go West"); this.commandsMap.put("go w", "Go West"); this.commandsMap.put("west", "Go West"); this.commandsMap.put("w", "Go West");
   		this.commandsMap.put("go east", "Go East"); this.commandsMap.put("go e", "Go East"); this.commandsMap.put("east", "Go East"); this.commandsMap.put("e", "Go East");
   		this.commandsMap.put("check", "Check"); this.commandsMap.put("c", "Check");
   		this.commandsMap.put("drop", "Drop"); this.commandsMap.put("d", "Drop");
   		this.commandsMap.put("take", "Take"); this.commandsMap.put("t", "Take");
   		this.commandsMap.put("inspect", "Inspect"); this.commandsMap.put("i", "Inspect");
   		this.commandsMap.put("p", "Print");
   		this.commandsMap.put("pp", "Player");
	}

	// ---------- member variables

	private Player player = null;
	private Room currentRoom = null;
	private List<Room> path = new LinkedList<Room>();

	private ObservableList history = null;
	private final Map<String, String> commandsMap = new HashMap<String, String>();

}	// end GamePlay