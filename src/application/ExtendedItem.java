/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class ExtendedItem {

	public ExtendedItem(String name, ItemType type, int weight, String ability, int ammo, int ammoUsage, int power, int accuracy, int qty, int heal, int reduction, int durability) {

		this.name = name;
		this.type = type;
		this.weight = weight;
		this.ability = ability;
		this.ammo = ammo;
		this.ammoUsage = ammoUsage;
		this.power = power;
		this.accuracy = accuracy;
		this.qty = qty;
		this.heal = heal;
		this.reduction = reduction;
		this.durability = durability;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the type
	 */
	public ItemType getType() {
		return type;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @return the ability
	 */
	public String getAbility() {
		return ability;
	}
	/**
	 * @return the ammo
	 */
	public int getAmmo() {
		return ammo;
	}
	/**
	 * @return the ammoUsage
	 */
	public int getAmmoUsage() {
		return ammoUsage;
	}
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * @return the accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}
	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * @return the heal
	 */
	public int getHeal() {
		return heal;
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

	private String name = null;
	private ItemType type = ItemType.ITEM_TYPE_NONE;
	private int weight = 0;
	private String ability = null;
	private int ammo = 0;
	private int ammoUsage = 0;
	private int power = 0;
	private int accuracy = 0;
	private int qty = 0;
	private int heal = 0;
	private int reduction = 0;
	private int durability = 0;

} // end ExtendedItem