/**
 *
 */
package application;

/**
 * Weapon Item - used in Combat
 *
 * @author Andrei Ivanovic
 *
 */
public class MeleeWeapon extends Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param power
	 */
	public MeleeWeapon(String name, int weight, int power) {
		super(name, weight, ItemType.ITEM_TYPE_MELEE);
		this.power = power;
	}

	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Combat: use of Melee weapon
	 */
	public Damage use(Player player) {
		Zork.combatHistory.add("ANG3L: Good strike!");
		//System.out.printf("MeleeWeapon - damage delt: %d%n", this.power);
		return new Damage(power);
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, this.power, 0, 1, 0, 0, 0);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("MeleeWeapon - %s, weight: %s, power: %d",
				super.getName(), super.getWeight(), this.power);
	}

	private int power = 0;

} // end MeleeWeapon