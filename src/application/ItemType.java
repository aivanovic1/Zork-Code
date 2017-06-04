/**
 *
 */
package application;

/**
 * Keep the Item types
 *
 * @author Andrei Ivanovic
 *
 */
public enum ItemType {

	ITEM_TYPE_NONE,
	ITEM_TYPE_ARMOUR,
	ITEM_TYPE_EXOTIC_MELEE,
	ITEM_TYPE_EXOTIC,
	ITEM_TYPE_GRENADE,
	ITEM_TYPE_KEYS,
	ITEM_TYPE_MEDICINE,
	ITEM_TYPE_MELEE,
	ITEM_TYPE_WEAPON;

	/**
	 * toString()
	 */
	public String toString() {
		if (this == ItemType.ITEM_TYPE_ARMOUR) return "Armour";
		else if (this == ItemType.ITEM_TYPE_EXOTIC_MELEE) return "Exotic Melee";
		else if (this == ItemType.ITEM_TYPE_EXOTIC) return "Exotic";
		else if (this == ItemType.ITEM_TYPE_GRENADE) return "Grenade";
		else if (this == ItemType.ITEM_TYPE_KEYS) return "Keys";
		else if (this == ItemType.ITEM_TYPE_MEDICINE) return "Medicine";
		else if (this == ItemType.ITEM_TYPE_MELEE) return "Melee";
		else if (this == ItemType.ITEM_TYPE_WEAPON) return "Weapon";
		else return "Unknown";
	}

} // end ItemType