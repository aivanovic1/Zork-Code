/**
 *
 */
package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Main parser for the Game Map, Room Layouts, room descriptions, story telling and AI comments
 *
 * @author Andrei Ivanovic
 *
 */
public class RoomsAndMapParser {

	private static final int PARSE_NONE = 0;
	private static final int PARSE_MAP = 1;
	private static final int PARSE_NAMES = 2;
	private static final int PARSE_DESC = 3;
	private static final int PARSE_STORY = 4;
	private static final int PARSE_STORY2 = 5;
	private static final int PARSE_AI = 6;
	private static final int PARSE_MESSAGE = 7;
	private static final int PARSE_ENDING = 8;
	private static final int PARSE_INVENTORY = 9;
	private static final int PARSE_CHARACTER = 10;
	private static final int PARSE_PLAYER = 11;
	private static final int PARSE_INSPECT = 12;
	private static final int PARSE_INSPECT_RESPONSE = 13;
	private static final int PARSE_LOCK = 14;

	public RoomsAndMapParser(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Parsing rooms, return the Map of all rooms (key is the short-form Room Id)
	 *
	 * @param fileName
	 * @return
	 */
	public Map<String, Room> loadMap() {

    	BufferedReader br = null;

    	int parsingSection = PARSE_NONE;
    	char[][] charMap = new char[64][];

    	String currentRoom = null;

    	try
		{
			// open text file for parsing
    		br = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName), "UTF-8"));
            //br = new BufferedReader(new FileReader(this.fileName));

            int lineNo = 0;
            String line = null;

            int countMapLines = 0;

            List<String> textBuffer = new ArrayList<String>();

            while ((line = br.readLine()) != null) {

            	++lineNo;

            	// skip comments
            	if (line.trim().startsWith("#")) continue;

            	// skip empty lines at the start
            	if (parsingSection == PARSE_NONE && line.trim().isEmpty()) continue;

            	// parse room naming [visual map only accepts Room IDs of 1 character length - convert to human readable names)
            	if (line.trim().startsWith("$Room:")) {

            		parsingSection = PARSE_NAMES;
            		currentRoom = whatRoom(line);

            		int index = -1;
            		if ((index = line.indexOf("-")) > -1) {
            			// map with names
                		this.namingMap.put(currentRoom.toUpperCase(), line.substring(index + 1).trim());
            		}
            	}
            	else if (line.trim().startsWith("$PlayerInfo")) {

            		parsingSection = PARSE_PLAYER;

            		int index = -1;
            		if ((index = line.indexOf(":")) > -1) {
            			this.player = createPlayer(line.substring(index+1));
            		}
            	}
            	else if (line.trim().startsWith("$PlayerItem")) {

            		parsingSection = PARSE_PLAYER;

            		int index = -1;
            		if ((index = line.indexOf(":")) > -1) {
            			Item item = inventoryParsing(line.substring(index+1));
            			this.player.getPlayerInventory().addItem(item);
            		}
            	}
            	else if (line.trim().startsWith("$Inspect ")) {

            		parsingSection = PARSE_INSPECT;
            		currentRoom = whatRoom(line);

            		int index = -1;
            		if ((index = line.indexOf(":")) > -1) {
            			this.inspectParsing(line.substring(index+1), currentRoom);
            		}
            	}
            	else if (line.trim().startsWith("$InspectResponse ")) {

            		// this is just to avoid repeating similar code; only one of the collections will be populated by parsed text (textBuffer)
            		this.fillTextBuffer(parsingSection, currentRoom, textBuffer);

            		parsingSection = PARSE_INSPECT_RESPONSE;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$Inventory ")) {

            		parsingSection = PARSE_INVENTORY;
            		currentRoom = whatRoom(line);

            		int index = -1;
            		if ((index = line.indexOf(":")) > -1) {
            			Item item = inventoryParsing(line.substring(index+1));
            			storeInventory(currentRoom.toUpperCase(), item);
            		}
            	}
            	else if (line.trim().startsWith("$Character ")) {

            		parsingSection = PARSE_CHARACTER;
            		currentRoom = whatRoom(line);

            		int index = -1;
            		if ((index = line.indexOf(":")) > -1) {
            			Character character = characterParsing(line.substring(index+1));
            			placeCharacter(currentRoom.toUpperCase(), character);
            		}
            	}
            	else if (line.trim().startsWith("$LockFrom ")) {

            		parsingSection = PARSE_LOCK;
            		currentRoom = whatRoom(line);

            		int index = -1;
            		if ((index = line.indexOf(" to ")) > -1) {
            			String goToRoom = line.substring(index + 4).trim();
            			this.lockRoomMap.put(currentRoom.toUpperCase(), goToRoom.toUpperCase());
            		}
            	}
            	else if (line.trim().startsWith("$Desc ")) {

            		// this is just to avoid repeating similar code; only one of the collections will be populated by parsed text (textBuffer)
            		this.fillTextBuffer(parsingSection, currentRoom, textBuffer);

            		parsingSection = PARSE_DESC;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$Story ")) {

            		fillTextBuffer(parsingSection, currentRoom, textBuffer);
            		textBuffer.clear();

            		parsingSection = PARSE_STORY;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$Story2 ")) {

            		fillTextBuffer(parsingSection, currentRoom, textBuffer);
            		textBuffer.clear();

            		parsingSection = PARSE_STORY2;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$AI ")) {

            		fillTextBuffer(parsingSection, currentRoom, textBuffer);
            		textBuffer.clear();

            		parsingSection = PARSE_AI;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$Message ")) {

            		fillTextBuffer(parsingSection, currentRoom, textBuffer);
            		textBuffer.clear();

            		parsingSection = PARSE_MESSAGE;
            		currentRoom = whatRoom(line);
	        	}
            	else if (line.trim().startsWith("$Ending")) {

            		fillTextBuffer(parsingSection, currentRoom, textBuffer);
            		textBuffer.clear();

            		parsingSection = PARSE_ENDING;
	        	}
            	else if (line.trim().startsWith("$Map:")) {

            		parsingSection = PARSE_MAP;
	        	}
            	else if (isTextProcessing(parsingSection)) {

            		textBuffer.add(line);
	        	}
            	else if (parsingSection == PARSE_MAP) {

                	char[] arr = line.toCharArray();
                	charMap[countMapLines++] = arr;
            	}
            }

            br.close();

            // just in case if the text buffer has still some data
    		fillTextBuffer(parsingSection, currentRoom, textBuffer);

            // PARSE: vertical and horizontal lines of the map
            this.completeHorizontalMapProcess(charMap);
            this.completeVerticalMapProcess(charMap);

            for(String roomID : namingMap.keySet()) {
            	Room r = roomsMap.get(roomID);
            	if (r != null) {
            		r.setName(namingMap.get(roomID));
            	}
            }

            // put all info together
            return this.finalMapping();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Inspect parsing
	 *
	 * @param substring
	 * @return
	 */
	private void inspectParsing(String inspectItemName, String roomID) {

		// check if we already have the list for the given room
		List<String> listItems = this.inspectMap.get(roomID.toUpperCase());

		// if not, create the new empty list and add to the HashMap for this roomID
		if (listItems == null) {
			listItems = new ArrayList<String>();
			this.inspectMap.put(roomID.toUpperCase(), listItems);
		}

		// finally, add the item to the list
		listItems.add(inspectItemName.trim());
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the startingRoom
	 */
	public Room getStartingRoom() {
		return startingRoom;
	}

	/**
	 * @return the roomsMap
	 */
	public Map<String, Room> getRoomsMap() {
		return roomsMap;
	}

	/**
	 * Create player from text file
	 *
	 * @param line
	 * @return player
	 */
	private Player createPlayer(String line) {

		String[] arr = line.split(",");
		if (arr.length < 2) return null;

		this.player = new Player(Integer.parseInt(arr[0].trim()), Integer.parseInt(arr[1].trim()));

		return this.player;
	}

	/**
	 * Parse characters
	 *
	 * @param line
	 * @return character
	 */
	private Character characterParsing(String line) {

		String[] arr = line.split(",");
		if (arr.length < 2) return null;

		Character character = new Character(arr[0].trim(), Integer.parseInt(arr[1].trim()), Integer.parseInt(arr[2].trim()),
								Integer.parseInt(arr[3].trim()), Integer.parseInt(arr[4].trim()));

		return character;
	}

	/**
	 * Parse inventory definition
	 *
	 * @param line
	 * @return Item
	 */
	private Item inventoryParsing(String line) {

		String[] arr = line.split(",");
		if (arr.length < 2) return null;

		Item item = null;

		if ("Item".equals(arr[0].trim())) {
			item = new Item(arr[1].trim(), Integer.parseInt(arr[2].trim()), ItemType.ITEM_TYPE_NONE);
		}
		else if ("Weapon".equals(arr[0].trim())) {
			item = new Weapon(arr[1].trim(), Integer.parseInt(arr[2].trim()),
					Integer.parseInt(arr[3].trim()), Integer.parseInt(arr[4].trim()),
					Integer.parseInt(arr[5].trim()), Integer.parseInt(arr[6].trim()));
		}
		else if ("MeleeWeapon".equals(arr[0].trim())) {
			item = new MeleeWeapon(arr[1].trim(), Integer.parseInt(arr[2].trim()), Integer.parseInt(arr[3].trim()));
		}
		else if ("Armour".equals(arr[0].trim())) {
			item = new Armour(arr[1].trim(), Integer.parseInt(arr[2].trim()), Integer.parseInt(arr[3].trim()), Integer.parseInt(arr[4].trim()));
		}
		else if ("Medicine".equals(arr[0].trim())) {
			item = new Medicine(arr[1].trim(), Integer.parseInt(arr[2].trim()), Integer.parseInt(arr[3].trim()), Integer.parseInt(arr[4].trim()));
		}
		else if ("Grenade".equals(arr[0].trim())) {
			item = new Grenade(arr[1].trim(), Integer.parseInt(arr[2].trim()), arr[3].trim(), Integer.parseInt(arr[4].trim()));
		}
		else if ("Keys".equals(arr[0].trim())) {
			item = new Keys(arr[1].trim(), Integer.parseInt(arr[2].trim()), arr[3].trim());
		}
		else if ("ExoticWeapon".equals(arr[0].trim())) {
			item = new ExoticWeapon(arr[1].trim(), Integer.parseInt(arr[2].trim()),
					arr[3].trim(), Integer.parseInt(arr[4].trim()),
					Integer.parseInt(arr[5].trim()), Integer.parseInt(arr[6].trim()), Integer.parseInt(arr[7].trim()));
		}
		else if ("ExoticMeleeWeapon".equals(arr[0].trim())) {
			item = new ExoticMeleeWeapon(arr[1].trim(), Integer.parseInt(arr[2].trim()), arr[3].trim(), Integer.parseInt(arr[4].trim()));
		}

		return item;
	}

	/**
	 * Add the item to the list of room items
	 *
	 * @param roomID
	 * @param item
	 */
	private void storeInventory(String roomID, Item item) {

		// check if we already have the list for the given room
		List<Item> listItems = this.inventoryMap.get(roomID.toUpperCase());

		// if not, create the new empty list and add to the HashMap for this roomID
		if (listItems == null) {
			listItems = new ArrayList<Item>();
			this.inventoryMap.put(roomID.toUpperCase(), listItems);
		}

		// finally, add the item to the list
		listItems.add(item);
	}

	/**
	 * Add the item to the list of room items
	 *
	 * @param roomID
	 * @param item
	 */
	private void placeCharacter(String roomID, Character character) {

		// check if we already have the list for the given room
		List<Character> listCharacters = this.characterMap.get(roomID.toUpperCase());

		// if not, create the new empty list and add to the HashMap for this roomID
		if (listCharacters == null) {
			listCharacters = new ArrayList<Character>();
			this.characterMap.put(roomID.toUpperCase(), listCharacters);
		}

		// finally, add the item to the list
		listCharacters.add(character);
	}
	/**
	 * Parse the room ID
	 *
	 * @param line
	 * @return
	 */
	private static String whatRoom(String line) {

		int index = -1;
		int endIndex = -1;

		if ((index = line.indexOf("$Room:")) > -1 && (endIndex = line.indexOf("-", index + 6)) > -1) {
			return line.substring(index + 6, endIndex).trim();
		}
		else if ((index = line.indexOf("$Inventory ")) > -1 && (endIndex = line.indexOf(":", index + 10)) > -1) {
			return line.substring(index + 10, endIndex).trim();
		}
		else if ((index = line.indexOf("$Character ")) > -1 && (endIndex = line.indexOf(":", index + 10)) > -1) {
			return line.substring(index + 10, endIndex).trim();
		}
		else if ((index = line.indexOf("$LockFrom ")) > -1 && (endIndex = line.indexOf(" to ", index + 9)) > -1) {
			return line.substring(index + 9, endIndex).trim();
		}
		else if ((index = line.indexOf("$Inspect ")) > -1 && (endIndex = line.indexOf(":", index + 8)) > -1) {
			return line.substring(index + 8, endIndex).trim();
		}
		else if ((index = line.indexOf("$InspectResponse ")) > -1 && (endIndex = line.indexOf(":", index + 16)) > -1) {
			return line.substring(index + 16, endIndex).trim();
		}
		else if ((index = line.indexOf("$Desc ")) > -1 && (endIndex = line.indexOf(":", index + 5)) > -1) {
			return line.substring(index + 5, endIndex).trim();
		}
		else if ((index = line.indexOf("$Message ")) > -1 && (endIndex = line.indexOf(":", index + 8)) > -1) {
			return line.substring(index + 8, endIndex).trim();
		}
		else if ((index = line.indexOf("$Story ")) > -1 && (endIndex = line.indexOf(":", index + 6)) > -1) {
			return line.substring(index + 6, endIndex).trim();
		}
		else if ((index = line.indexOf("$Story2 ")) > -1 && (endIndex = line.indexOf(":", index + 7)) > -1) {
			return line.substring(index + 7, endIndex).trim();
		}
		else if ((index = line.indexOf("$AI ")) > -1 && (endIndex = line.indexOf(":", index + 3)) > -1) {
			return line.substring(index + 3, endIndex).trim();
		}

		return "";
	}

	/**
	 * Check if text processing is in progress
	 *
	 * @param processType
	 * @return
	 */
	private boolean isTextProcessing(int processType) {
		if (processType == PARSE_DESC || processType == PARSE_STORY || processType == PARSE_STORY2 || processType == PARSE_INSPECT_RESPONSE ||
				processType == PARSE_AI || processType == PARSE_MESSAGE || processType == PARSE_ENDING) return true;

		return false;
	}

	/**
	 * Fill the map with the Desc/Story/AI/Message text
	 *
	 * @param processType
	 * @param roomID
	 * @param buffer
	 */
	private void fillTextBuffer(int processType, String roomID, List<String> buffer) {

		if(this.isTextProcessing(processType)) {

			String text = "";
			for(String s: buffer) {
				text += (s + "\n");
			}

			if (processType == PARSE_DESC) {
				this.roomDescMap.put(roomID.toUpperCase(), text);
			}
			else if (processType == PARSE_STORY) {
				this.storyMap.put(roomID.toUpperCase(), text);
			}
			else if (processType == PARSE_STORY2) {
				this.story2Map.put(roomID.toUpperCase(), text);
			}
			else if (processType == PARSE_INSPECT_RESPONSE) {

				// check if we already have the list for the given room
				List<String> listResponses = this.inspectResponseMap.get(roomID.toUpperCase());

				// if not, create the new empty list and add to the HashMap for this roomID
				if (listResponses == null) {
					listResponses = new ArrayList<String>();
					this.inspectResponseMap.put(roomID.toUpperCase(), listResponses);
				}

				// finally, add the item to the list
				listResponses.add(text);
			}
			else if (processType == PARSE_AI) {
				this.AIMap.put(roomID.toUpperCase(), text);
			}
			else if (processType == PARSE_MESSAGE) {
				this.messageMap.put(roomID.toUpperCase(), text);
			}
			else if (processType == PARSE_ENDING) {
				this.endings.add(text);
			}

    		buffer.clear();
		}
	}

	/**
	 * Horizontal map processing
	 *
	 * @param charMap
	 */
	private void completeHorizontalMapProcess(char[][] charMap) {

		Room firstRoom = null;

		//System.out.println("********* Horizontal processing **********");
		for(int i = 0; i < charMap.length; ++i) {

			if (charMap[i] == null) break;

			for(char ch : charMap[i]) {
				if (validateIfRoomChar(ch)) {
					Room r = this.roomsMap.get(String.valueOf(ch).toUpperCase());
					if (r == null) {
						r = new Room(String.valueOf(ch));
						this.roomsMap.put(String.valueOf(ch).toUpperCase(), r);
					}
				}
			}

			// look for something like B-----C
			String line = new String(charMap[i]);
			//if (!line.trim().isEmpty()) System.out.println(line);

			int start = 0;
			int index = -1;

			while((index = line.indexOf("-----", start)) > -1) {
				if (index == 0) {
           			System.err.println("Invalid map format - missing room on the start of the connection!");
        			System.exit(1);
				}

				char leftRoomChar = line.charAt(index - 1);
				char rightRoomChar = line.charAt(index + 5);

				if (!validateIfRoomChar(leftRoomChar) || !validateIfRoomChar(rightRoomChar)) {
           			System.err.println("Invalid map format - could not detect proper room sumbol!");
        			System.exit(1);
				}

				Room leftRoom = this.roomsMap.get(String.valueOf(leftRoomChar).toUpperCase());
				Room rightRoom = this.roomsMap.get(String.valueOf(rightRoomChar).toUpperCase());

				if (leftRoom == null || rightRoom == null) {
           			System.err.println("Invalid map format - could not find the room in the hashmap!");
        			System.exit(1);
				}

				leftRoom.setEast(rightRoom);
				rightRoom.setWest(leftRoom);

				start = index + 5;
			}

			index = line.indexOf("+");
			if (index > -1) {

				if (firstRoom != null) {
           			System.err.println("Invalid map format: multiple entrance rooms found!");
        			System.exit(1);
				}

				if (index > 0 && validateIfRoomChar(line.charAt(index-1))) {
					firstRoom = this.roomsMap.get(String.valueOf(line.charAt(index-1)).toUpperCase());
					if (firstRoom == null) {
	           			System.err.println("Invalid map format - first room not recognized!");
	        			System.exit(1);
					}

					firstRoom.makeEntranceRoom();
				}
				else if (index + 1 < line.length() && validateIfRoomChar(line.charAt(index+1))) {
					firstRoom = this.roomsMap.get(String.valueOf(line.charAt(index+1)).toUpperCase());
					if (firstRoom == null) {
	           			System.err.println("Invalid map format - first room not recognized!");
	        			System.exit(1);
					}

					this.startingRoom.makeEntranceRoom();				}
			}
		}
	}

	/**
	 * Vertical map processing
	 *
	 * @param charMap
	 */
	private void completeVerticalMapProcess(char[][] charMap) {

		Room firstRoom = null;

		//System.out.println("********* Vertical processing **********");

		// go for 120 characters wide
		for (int column = 0; column < 120; ++column) {

			char[] arr = new char[charMap.length];

			for(int i = 0; i < charMap.length; ++i) {
				if (charMap[i] == null) arr[i] = ' ';
				else if (column < charMap[i].length) arr[i] = charMap[i][column];
				else arr[i] = ' ';
			}

			// resulting string might be something like:
			// +A  I||M  S||U

			String line = new String(arr);
			//if (!line.trim().isEmpty()) System.out.println(line);

			int start = 0;
			int index = -1;

			while((index = line.indexOf("||", start)) > -1) {
				if (index == 0) {
           			System.err.println("Invalid map format - no room on the connection end!");
        			System.exit(1);
				}

				char northRoomChar = line.charAt(index - 1);
				char southRoomChar = line.charAt(index + 2);

				if (!validateIfRoomChar(northRoomChar) || !validateIfRoomChar(southRoomChar)) {
           			System.err.println("Invalid map format - could not detect proper room sumbol!");
        			System.exit(1);
				}

				Room northRoom = this.roomsMap.get(String.valueOf(northRoomChar).toUpperCase());
				Room southRoom = this.roomsMap.get(String.valueOf(southRoomChar).toUpperCase());

				if (northRoom == null || southRoom == null) {
           			System.err.println("Invalid map format - could not find the room in the hashmap!");
        			System.exit(1);
				}

				northRoom.setSouth(southRoom);
				southRoom.setNorth(northRoom);

				start = index + 2;
			}

			index = line.indexOf("+");
			if (index > -1) {

				if (firstRoom != null) {
           			System.err.println("Invalid map format: multiple entrance rooms found!");
        			System.exit(1);
				}

				if (index > 0 && validateIfRoomChar(line.charAt(index-1))) {
					firstRoom = this.roomsMap.get(String.valueOf(line.charAt(index-1)).toUpperCase());
					if (firstRoom == null) {
	           			System.err.println("Invalid map format!");
	        			System.exit(1);
					}

					firstRoom.makeEntranceRoom();
				}
				else if (index + 1 < line.length() && validateIfRoomChar(line.charAt(index+1))) {
					firstRoom = this.roomsMap.get(String.valueOf(line.charAt(index+1)).toUpperCase());
					if (firstRoom == null) {
	           			System.err.println("Invalid map format!");
	        			System.exit(1);
					}

					this.startingRoom.makeEntranceRoom();
				}
			}
		}
	}

	/**
	 * Final mapping
	 *
	 * @return
	 */
	private Map<String, Room> finalMapping() {

		Map<String, Room> finalMap = new HashMap<String, Room>();

		for(Room r : this.roomsMap.values()) {
			finalMap.put(r.getId().trim().toUpperCase(), r);
		}

		// load all the info for the corresponding rooms
		for(String roomID : this.roomDescMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			if (r != null) r.setDescription(this.roomDescMap.get(roomID));
		}

		for(String roomID : this.storyMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			if (r != null) r.setStory(this.storyMap.get(roomID));
		}

		for(String roomID : this.story2Map.keySet()) {
			Room r = this.roomsMap.get(roomID);
			if (r != null) r.setStory2(this.story2Map.get(roomID));
		}

		for(String roomID : this.AIMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			if (r != null) r.setAiText(this.AIMap.get(roomID));
		}

		for(String roomID : this.messageMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			if (r != null) r.setMessage(this.messageMap.get(roomID));
		}

		for(String roomID : this.inventoryMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			List<Item> items = this.inventoryMap.get(roomID);
			r.setItems(items);
		}

		for(String roomID : this.characterMap.keySet()) {
			Room r = this.roomsMap.get(roomID);
			List<Character> characters = this.characterMap.get(roomID);
			r.setCharacters(characters);
		}

		if (this.inspectMap.size() != this.inspectResponseMap.size()) {
			System.err.println("Inspect information is not matching with Responses!");
		}
		else {

			// map all Inspect items with the rooms
			for(String roomID : this.inspectMap.keySet()) {
				Room r = this.roomsMap.get(roomID);
				List<String> inspectItems = this.inspectMap.get(roomID);
				r.setInspectItems(inspectItems);
			}

			// map all Inspect responses with the rooms
			for(String roomID : this.inspectResponseMap.keySet()) {
				Room r = this.roomsMap.get(roomID);
				List<String> inspectResponseItems = this.inspectResponseMap.get(roomID);
				r.setInspectResponses(inspectResponseItems);
			}
		}

		for(String roomID : this.lockRoomMap.keySet()) {
			Room fromRoom = this.roomsMap.get(roomID);
			if (fromRoom == null) {
				System.err.println("LOCK: Room ID: {" + roomID + "} not found!");
				System.exit(1);
			}

			String strToRoom = this.lockRoomMap.get(roomID);
			Room toRoom = this.roomsMap.get(strToRoom);

			if (toRoom == null) {
				System.err.println("LOCK: Room ID: {" + strToRoom + "} not found!");
				System.exit(1);
			}

			fromRoom.setLockGoingToRoom(toRoom);
		}

		Zork.endingMessageList = this.endings;

		return finalMap;
	}

	/**
	 * Validate if character is room character
	 * @param ch
	 * @return
	 */
	private boolean validateIfRoomChar(char ch) {
		if (ch != ' ' && ch != '+' && ch != '-' && ch != '|') return true;
		return false;
	}

	/**
	 * Get endings list of values
	 *
	 * @return
	 */
	public List<String> getEndings() {
		return this.endings;
	}

	// --------- fields

	// the name of the definition file: room layout map & room description
	private String fileName = null;

	// main player class
	private Player player = null;

	// game starting room
	private Room startingRoom = null;

	// all rooms (key: room ID, value: room object)
	private Map<String, Room> roomsMap = new HashMap<String, Room>();

	//----- TEMP for parsing only

	// keeping mapping between Room  ID and real room names
	private Map<String, String> namingMap = new HashMap<String, String>();

	// keep parsed Room descriptions (key: room ID, value: room description)
	private Map<String, String> roomDescMap = new HashMap<String, String>();

	// Story narrative (key: room ID, value: story text)
	private Map<String, String> storyMap = new HashMap<String, String>();

	// Story narrative (key: room ID, value: story text)
	private Map<String, String> story2Map = new HashMap<String, String>();

	// AI narrative (key: room ID, value: AI text)
	private Map<String, String> AIMap = new HashMap<String, String>();

	// Message text (key: room ID, value: message)
	private Map<String, String> messageMap = new HashMap<String, String>();

	// Message lock definition ($LockFrom X to Y)
	private Map<String, String> lockRoomMap = new HashMap<String, String>();

	// keep the different endings of the story
	private List<String> endings = new ArrayList<String>();

	// keep the inspect items while parsing
	private Map<String, List<String>> inspectMap = new HashMap<String, List<String>>();

	// keep the inspect responses while parsing
	private Map<String, List<String>> inspectResponseMap = new HashMap<String, List<String>>();

	// keep the items while parsing
	private Map<String, List<Item>> inventoryMap = new HashMap<String, List<Item>>();

	// keep the characters while parsing
	private Map<String, List<Character>> characterMap = new HashMap<String, List<Character>>();

}	// end RoomsAndMapParser