/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;

/**
 * This is Command parser class: Runs the entire Zork Game
 *
 * @author Andrei Ivanovic
 *
 */
public class GameExecution {

	/**
	 * Constructor
	 *
	 * @param startRoom
	 */
	public GameExecution(Room startRoom, Player player, ObservableList history) {
		if (startRoom != null) {
			Zork.currentRoom = startRoom;
		}

		this.historyDataList = history;
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

    	// store the original command in case the user's entry was incorrect
    	command.setOriginalEnteredCommand(entry);

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
			drop(command);
		} else if (commandVerb.startsWith("check")) {
			if (command.getParams().size() > 0) Zork.displayInHistoryPanel("ANG3L: Invalid check command: " + command.getOriginalEnteredCommand());
			else check(commandVerb);
		} else if (commandVerb.startsWith("inventory")) {
			if (command.getParams().size() > 0) Zork.displayInHistoryPanel("ANG3L: Invalid inventory command: " + command.getOriginalEnteredCommand());
			else inventory(commandVerb);
		} else if (commandVerb.startsWith("inspect")) {
			inspect(command);
		} else if (commandVerb.startsWith("print")) {
			//printRoom(commandVerb);
		} else if (commandVerb.startsWith("player")) {
			//printPlayer(commandVerb);
		} else if (commandVerb.startsWith("goto")) {
			//gotoCommand(command);
		}

		else
			Zork.displayInHistoryPanel("ANG3L: Invalid command: " + command.getOriginalEnteredCommand());
	}

	/**
	 * Manage inspect command
	 *
	 * @param command
	 */
	private void inspect(Command command) {

		if (command.getParams().size() == 0){
			Zork.displayInHistoryPanel("ANG3L: Inspect what?");
			return;
		}

		if (Zork.currentRoom.getInspectItems() == null) {
			Zork.displayInHistoryPanel("ANG3L: This room doesn't have anything that needs looking into...");
			return;
		}

		String itemToInspect = command.generateFullCommandNoVerb();

		int foundAtIndex = -1;
		for(int i = 0; i < Zork.currentRoom.getInspectItems().size(); ++i) {
			if (itemToInspect.equalsIgnoreCase(Zork.currentRoom.getInspectItems().get(i))) {
				foundAtIndex = i;
				break;
			}
		}

		if (foundAtIndex > -1) {
			Zork.displayInHistoryPanel(Zork.currentRoom.getInspectResponses().get(foundAtIndex));
		}
		else {
			Zork.displayInHistoryPanel("ANG3L: Item: " + itemToInspect + " doesn't exist!");
		}
	}

	/**
	 * Handle inventory command
	 *
	 * @param commandVerb
	 */
	private void inventory(String commandVerb) {
		String message = null;

		if (this.player.getPlayerInventory().getInventory() == null || this.player.getPlayerInventory().getInventory().size() == 0) message = "ANG3L: There's nothing in your inventory.";
		else{
			message = "ANG3L: You have the following items in your inventory: ";

			for (int i = 0; i < this.player.getPlayerInventory().getInventory().size(); i++){
				if (i == 0) message += this.player.getPlayerInventory().getInventory().get(i).getName() + " (" + (i+1) + ")";
				else message += ", " + this.player.getPlayerInventory().getInventory().get(i).getName() + " (" + (i+1) + ")";
			}
		}

		Zork.displayInHistoryPanel(message);
	}

	/**
	 * Processes drop command
	 *
	 * @param command
	 */
	private void drop(Command command) {

		String message = null;

		if (command.getParams().size() == 0){
			message = "ANG3L: Drop what?";
			Zork.displayInHistoryPanel(message);
			return;
		}

		if (this.player.getPlayerInventory().getInventory() == null || this.player.getPlayerInventory().getInventory().isEmpty()){
			message = "ANG3L: There are no items in your inventory!";
			Zork.displayInHistoryPanel(message);
			return;
		}
		int itemNumber = -1;

		try {
			itemNumber = Integer.parseInt(command.getParam(1))-1;

			Item item = this.player.getPlayerInventory().removeItem(this.player.getPlayerInventory().getInventory().get(itemNumber)); //CHANGED
			Zork.currentRoom.getItems().add(item);

			message = "ANG3L: You dropped: " + item.getName();

		} catch (NumberFormatException e) {

			if ("all".equalsIgnoreCase(command.getParam(1))){
				message = "ANG3L: You dropped: \n";
				for (int i = this.player.getPlayerInventory().getInventory().size()-1; i >= 0; i--){

					Item item = this.player.getPlayerInventory().getInventory().get(i);
					message += (item.toString() + "\n");
					Zork.currentRoom.getItems().add(item);
				}

				// remove all items, assign Weight to 0
				this.player.getPlayerInventory().removeAllInventory();
			}
			else {
				boolean nameNotFound = true;
				int i = 0;

				while(nameNotFound && i < this.player.getPlayerInventory().getInventory().size()) {
					if (this.player.getPlayerInventory().getInventory().get(i).getName().equalsIgnoreCase(command.generateFullCommandNoVerb())) nameNotFound = false;
					else i++;
				}

				if (!nameNotFound) {

					Item item = this.player.getPlayerInventory().getInventory().get(i);
					Zork.currentRoom.getItems().add(this.player.getPlayerInventory().removeItemByIndex(i));

					message = "ANG3L: You dropped: " + item.getName();
				}
				else {
					message = "ANG3L: I'm not sure that item exists...";
				}
			}
 		}

		Zork.displayInHistoryPanel(message);
	}

	/**
	 * Processes GOTO command
	 *
	 * @param command
	 */
	private void gotoCommand(Command command) {

		if (command.getParams().size() == 0){
			String message = "ANG3L: Room ID?";
			Zork.displayInHistoryPanel(message);
			return;
		}

		this.move(command.generateFullCommand());
	}

	/**
	 * Processes player print command
	 *
	 * @param command
	 */
	private void printPlayer(String commandEntered) {

		System.out.println(this.player.toString());
	}

	/**
	 * Processes Room print command
	 *
	 * @param command
	 */
	private void printRoom(String commandEntered) {
		String message = Zork.currentRoom.toString();
		Zork.currentRoom.print();

		//Zork.displayInHistoryPanel(message);
	}

	/**
	 * Processes take command
	 *
	 * @param command
	 */
	private void take(Command command) {
		String message = null;

		if (command.getParams().size() == 0){
			message = "ANG3L: Take what?";
			Zork.displayInHistoryPanel(message);
			return;
		}

		if (Zork.currentRoom.getItems() == null || Zork.currentRoom.getItems().isEmpty()){
			message = "ANG3L: There are no items in this room!";
			Zork.displayInHistoryPanel(message);
			return;
		}
		int itemNumber = -1;

		try {
			itemNumber = Integer.parseInt(command.getParam(1)) - 1;

			int ind = 0;
			for(Item item : Zork.currentRoom.getItems()) {
				System.out.printf("Index: %d, item: %s%n", ind++, item.getName());
			}

			if (itemNumber >= Zork.currentRoom.getItems().size()) {
				message = "ANG3L: Pick the number between 1 and " + Zork.currentRoom.getItems().size() + "!";
			}
			else {
				if (this.player.getPlayerInventory().canIAdd(Zork.currentRoom.getItems().get(itemNumber))) {
					Item item = Zork.currentRoom.getItems().remove(itemNumber);
					this.player.getPlayerInventory().addItem(item);

					message = "ANG3L: You took: " + item.getName();
				}
				else message = "ANG3L: That's too heavy to take!";
			}

		} catch (NumberFormatException e) {

			if ("all".equalsIgnoreCase(command.getParam(1))){
				message = "ANG3L: You took: \n";
				boolean capacityReached = false;
				for (int i = Zork.currentRoom.getItems().size()-1; i >= 0; i--){

					Item item = Zork.currentRoom.getItems().get(i);

					if (this.player.getPlayerInventory().canIAdd(item)) {
						message += (item.toString() + "\n");
						this.player.getPlayerInventory().addItem(Zork.currentRoom.getItems().remove(i));
					}
					else
						capacityReached = true;
				}
				if (capacityReached) message += "ANG3L: You've taken the most items you could take.";

			}
			else {
				boolean nameNotFound = true;
				int i = 0;

				while(nameNotFound && i < Zork.currentRoom.getItems().size()) {
					if (Zork.currentRoom.getItems().get(i).getName().equalsIgnoreCase(command.generateFullCommandNoVerb())) nameNotFound = false;
					else i++;
				}

				if (!nameNotFound) {

					Item item = Zork.currentRoom.getItems().get(i);

					if (this.player.getPlayerInventory().canIAdd(item)) {
						this.player.getPlayerInventory().addItem(Zork.currentRoom.getItems().remove(i));

						message = "ANG3L: You took: " + item.getName();
					}
					else message = "ANG3L: That's too heavy to take!";
				}
				else {
					message = "ANG3L: I'm not sure that item exists...";
				}
			}
 		}

		Zork.displayInHistoryPanel(message);
	}

	/**
	 * Processes room items display command
	 *
	 * @param commandEntered
	 */
	private void check(String commandEntered) {
		String message = null;

		if (Zork.currentRoom.getItems() == null || Zork.currentRoom.getItems().size() == 0) message = "ANG3L: There's nothing to take in this room.";
		else{
			message = "ANG3L: You can take the following items: ";

			for (int i = 0; i < Zork.currentRoom.getItems().size(); i++){
				if (i == 0) message += Zork.currentRoom.getItems().get(i).getName() + " (" + (i+1) + ")";
				else message += ", " + Zork.currentRoom.getItems().get(i).getName() + " (" + (i+1) + ")";
			}
		}
		Zork.displayInHistoryPanel(message);
	}

	/**
	 * Movements control
	 *
	 * @param command
	 */
	private void move(String command) {

		String direction = command.substring(3);

		String message = null;

		// this is used when key is needed
		boolean proceedToNextRoom = true;

		if (command.trim().startsWith("goto ")) {
			String roomId = command.trim().substring(4).trim();

			Room r = Zork.roomsMap.get(roomId.toUpperCase());
			if (r != null) {
				Zork.currentRoom  = r;
				Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_GOTO;
			} else {
				message = "ANG3L: Invalid goto command / room ID not found! {" + command + "}";
				proceedToNextRoom = false;
			}
		}
		else if (direction.equalsIgnoreCase("north")) {

			if (Zork.currentRoom.getNorth() != null) {

				boolean goNorthSuccessful = false;

				// check for the LOCKed door going NORTH
				if(Zork.currentRoom.getLockGoingToRoom() != null && Zork.currentRoom.getLockGoingToRoom().getId().equalsIgnoreCase(Zork.currentRoom.getNorth().getId())) {
					if (!this.player.hasKeyForRoom(Zork.currentRoom.getLockGoingToRoom().getId(), Zork.currentRoom.getTotalKeysRequired())) {
						// display the message
						Zork.displayInHistoryPanel(Zork.currentRoom.getMessage());
						proceedToNextRoom = false;
					}
					else {
						goNorthSuccessful = true;
					}
				}
				else {
					goNorthSuccessful = true;
				}

				if (goNorthSuccessful) {
					Zork.currentRoom  = Zork.currentRoom.getNorth();
					Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_SOUTH;
					message = ">> going north! Entered room: " + Zork.currentRoom.getName();
				}

			} else {
				message = "ANG3L: There's no room to the north of here!";
				proceedToNextRoom = false;
			}
		}
		else if (direction.equalsIgnoreCase("south")) {
			if (Zork.currentRoom.getSouth() != null) {

				// check for the LOCKed door going SOUTH
				// TODO - in the near future

				Zork.currentRoom  = Zork.currentRoom.getSouth();
				Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_NORTH;
				message = ">> going south! Entered room: " + Zork.currentRoom.getName();
			} else {
				message = "ANG3L: There's no room to the south of here!";
				proceedToNextRoom = false;
			}
		}
		else if (direction.equalsIgnoreCase("east")) {
			if (Zork.currentRoom.getEast() != null) {

				// check for the LOCKed door going EAST
				// TODO - in the near future

				Zork.currentRoom  = Zork.currentRoom.getEast();
				Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_WEST;
				message = ">> going east! Entered room: " + Zork.currentRoom.getName();
			} else {
				message = "ANG3L: There's no room to the east of here!";
				proceedToNextRoom = false;
			}
		}
		else if (direction.equalsIgnoreCase("west")) {
			if (Zork.currentRoom.getWest() != null) {

				// check for the LOCKed door going WEST
				if(Zork.currentRoom.getLockGoingToRoom() != null && Zork.currentRoom.getLockGoingToRoom().getId().equalsIgnoreCase(Zork.currentRoom.getWest().getId())) {
					if (!this.player.hasKeyForRoom(Zork.currentRoom.getLockGoingToRoom().getId(), Zork.currentRoom.getTotalKeysRequired())) {
						// display the message
						Zork.displayInHistoryPanel(Zork.currentRoom.getMessage());
						proceedToNextRoom = false;
					}
					else {
						Zork.currentRoom  = Zork.currentRoom.getWest();
						Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_EAST;
						message = ">> going west! Entered room: " + Zork.currentRoom.getName();
					}
				}
				else {
					Zork.currentRoom  = Zork.currentRoom.getWest();
					Zork.roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_FROM_EAST;
					message = ">> going west! Entered room: " + Zork.currentRoom.getName();
				}
			} else {
				message = "ANG3L: There's no room to the west of here!";
				proceedToNextRoom = false;
			}
		}

		// display everything collected above
		if (message != null) Zork.displayInHistoryPanel(message);

		if ( proceedToNextRoom ) {

			if (!Zork.currentRoom.isFirstTimeEntered()) {
				Zork.currentRoom.setFirstTimeEntered(true);
				if (Zork.currentRoom.getStory() != null) {

					// when LastMinute gets triggered, this boolean will be TRUE and no DialogBoxes will be displayed
					if ( !Zork.disableDialogBoxDisplay ) {

						//System.out.println("Just before going to Room: " + Zork.currentRoom.getId() + " dialog box!");
						DialogBox dialogBox = new DialogBox("Room " + Zork.currentRoom.getName(), Zork.currentRoom.getStory(), Zork.currentRoom.getImageFileName());
						dialogBox.display();
						//System.out.println("  Just after the DialogBox for Room: " + Zork.currentRoom.getId());

						// it will wait till dialog box properly closes
						//Zork.waitOnDialog();
					}

					Zork.displayInHistoryPanel(Zork.currentRoom.getStory());
				}

				if (Zork.currentRoom.getCharacters() != null && Zork.currentRoom.getTotalAliveCharacters() > 0) {

					//System.out.println("Just before COMBAT for the Room: " + Zork.currentRoom.getId());

					// COMBAT PANEL
					CombatPanel combat = new CombatPanel(Zork.currentRoom, Zork.player);
					combat.start();
					//System.out.println("  Just after the CombatPanel for Room: " + Zork.currentRoom.getId());

					// NEED to force exit with System.exit() otherwise Platfom.exit() crashes
					if (!Zork.player.isAlive()) System.exit(1);
				}

				if (Zork.currentRoom.getStory2() != null) {

					DialogBox dialogBox = new DialogBox("Room " + Zork.currentRoom.getName(), Zork.currentRoom.getStory2(), Zork.currentRoom.getImageFileName());
					dialogBox.display();

					Zork.displayInHistoryPanel(Zork.currentRoom.getStory2());
				}

				// check if we are at the LAST (endingRoom)?
				if (Zork.currentRoom.getId().equals(Zork.endingRoom.getId())) {

					Zork.disableDialogBoxDisplay = true;

					//System.out.println("Just before LastMinute for the Room: " + Zork.currentRoom.getId());

					LastMinute last = new LastMinute();
					last.start();

					//System.out.println("  Just after LastMinute for the Room: " + Zork.currentRoom.getId());
				}
			}

			// display room description text
			if (Zork.currentRoom.getDescription() != null) {
				Zork.displayInHistoryPanel(Zork.currentRoom.getDescription());
			}
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
   		this.commandsMap.put("i", "Inventory"); this.commandsMap.put("inventory", "Inventory");
   		this.commandsMap.put("inspect", "Inspect");
   		this.commandsMap.put("pp", "Print");
   		this.commandsMap.put("p", "Player");
   		this.commandsMap.put("goto", "goto");
	}

	// ---------- member variables

	private Player player = null;

	private ObservableList historyDataList = null;
	private final Map<String, String> commandsMap = new HashMap<String, String>();

} // end GameExecution