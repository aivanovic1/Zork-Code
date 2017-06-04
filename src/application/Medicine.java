/**
 *
 */
package application;

/**
 * This is the Player's healing Item (used in Combat)
 *
 * @author Andrei Ivanovic
 *
 */
public class Medicine extends Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param quantity
	 * @param heal
	 */
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

	/**
	 * Combat: medicine use in combat
	 */
	public Damage use(Player player) {

		if (this.quantity > 0) {
			Zork.combatHistory.add("ANG3L: Perfect time to heal up!");
			if (player.getHealth() + this.heal > 100) player.setHealth(100);
			else player.incrementHealth(this.heal);

			this.quantity--;
			//System.out.printf("Medicine: Heal by : %d, current player health: %d, quantity reduced to %d%n", this.heal, player.getHealth(), this.quantity);
		}

		if (this.quantity == 0) {
			this.deactivate();
			//System.out.printf("Medicine: No quantities left, make it inactive item!%n");
		}

		return new Damage(0);
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", 0, 0, 0, 0, this.quantity, this.heal, 0, 0);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("Medicine - %s, weight: %s, quantity: %s, heal: %d",
				super.getName(), super.getWeight(), this.quantity, this.heal);
	}

	private int quantity = 0;
	private int heal = 0;

} // end Medicine