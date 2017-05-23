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
		super(name, weight);
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

	public String toString() {
		return String.format("Armour - %s, weight: %s, assault reduction: %s, durability: %s",
				super.getName(), super.getWeight(), this.reduction, this.durability);
	}

	private int reduction = 0;
	private int durability = 0;

} //end Armour