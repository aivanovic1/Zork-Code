/**
 *
 */
package application;

/**
 * Models Armour object used in the Combat
 *
 * @author Andrei Ivanovic
 *
 */
public class Armour extends Item {

	/**
	 * Armour for the Player
	 *
	 * @param name
	 * @param weight
	 * @param reduction
	 * @param durability
	 */
	public Armour(String name, int weight, int reduction, int durability) {
		super(name, weight, ItemType.ITEM_TYPE_ARMOUR);
		this.reduction = reduction;
		this.durability = durability;
	}
	/**
	 * @return the reduction
	 */
	public int getReduction() {
		return reduction;
	}
	/**
	 * @return the durability
	 */
	public int getDurability() {
		return durability;
	}

	/**
	 * Reduce damage
	 *
	 * @param damageDealt
	 * @return
	 */
	public int reduce(int damageDealt) {
		if (this.isActive()) {

			//System.out.printf("Armour: durability decreased to: %d, damage delt: %d%n", this.durability - 1, (int)(damageDealt * (this.reduction/100.0)));

			this.durability--;
			return (int)(damageDealt * (this.reduction/100.0));
		}
		return damageDealt;
	}

	/**
	 * Check if the Item is still in active state
	 */
	@Override
	public boolean isActive() {
		if (this.durability > 0) return true;
		return false;
	}

	/**
	 * This is helper function to convert for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, 0, 0, 1, 0, this.reduction, this.durability);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("Armour - %s, weight: %s, assault reduction: %s, durability: %s",
				super.getName(), super.getWeight(), this.reduction, this.durability);
	}

	private int reduction = 0;
	private int durability = 0;

} // end Armour