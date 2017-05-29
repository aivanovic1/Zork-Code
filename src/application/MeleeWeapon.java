/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class MeleeWeapon extends Item {

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

	public Damage use(Player player) {
		Zork.combatHistory.add("ANG3L: Good strike!");
		return new Damage(power);
	}

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, this.power, 0, 1, 0, 0, 0);
	}

	public String toString() {
		return String.format("MeleeWeapon - %s, weight: %s, power: %d",
				super.getName(), super.getWeight(), this.power);
	}

	private int power = 0;

} // end MeleeWeapon