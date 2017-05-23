/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Medicine extends Item {

	public Medicine(String name, int weight, int quantity, int heal) {
		super(name, weight);
		this.quantity = quantity;
		this.heal = heal;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @return the heal
	 */
	public int getHeal() {
		return heal;
	}

	public String toString() {
		return String.format("Medicine - %s, weight: %s, quantity: %s, heal: %d",
				super.getName(), super.getWeight(), this.quantity, this.heal);
	}

	private int quantity = 0;
	private int heal = 0;

} //end Medicine
