/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Armour extends Item {

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

	public int reduce(int damageDealt) {
		if (this.isActive()) {
			this.durability--;
			return (int)(damageDealt * (this.reduction/100.0));
		}
		return damageDealt;
	}

	@Override
	public boolean isActive() {
		if (this.durability > 0) return true;
		return false;
	}

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, 0, 0, 1, 0, this.reduction, this.durability);
	}

	public String toString() {
		return String.format("Armour - %s, weight: %s, assault reduction: %s, durability: %s",
				super.getName(), super.getWeight(), this.reduction, this.durability);
	}

	private int reduction = 0;
	private int durability = 0;

} //end Armour