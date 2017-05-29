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
		super(name, weight, ItemType.ITEM_TYPE_MEDICINE);
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

	public Damage use(Player player) {
		if (this.quantity > 0) {
			Zork.combatHistory.add("ANG3L: Perfect time to heal up!");
			if (player.getHealth() + this.heal > 100) player.setHealth(100);
			else {
				player.incrementHealth(this.heal);
				this.quantity--;
			}
		}
		else this.deactivate();

		return new Damage(0);
	}

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, 0, 0, this.quantity, this.heal, 0, 0);
	}

	public String toString() {
		return String.format("Medicine - %s, weight: %s, quantity: %s, heal: %d",
				super.getName(), super.getWeight(), this.quantity, this.heal);
	}

	private int quantity = 0;
	private int heal = 0;

} // end Medicine