/**
 *
 */
package application;

/**
 * Keeps the info on the room entry direction (used for locks)
 *
 * @author Andrei Ivanovic
 *
 */
public enum RoomEntryDirection {

	ROOM_ENTRY_DIRECTION_UNKNOWN,
	ROOM_ENTRY_DIRECTION_FROM_SOUTH,
	ROOM_ENTRY_DIRECTION_FROM_EAST,
	ROOM_ENTRY_DIRECTION_FROM_NORTH,
	ROOM_ENTRY_DIRECTION_FROM_WEST,
	ROOM_ENTRY_DIRECTION_FROM_GOTO;

} // end RoomEntryDirection