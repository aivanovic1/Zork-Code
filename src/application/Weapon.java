/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Weapon extends Item {

	public Weapon(String name, int weight, int power, int accuracy, int ammo, int ammoUsage) {
		super(name, weight, ItemType.ITEM_TYPE_WEAPON);
		this.power = power;
		this.accuracy = accuracy;
		this.ammo = ammo;
		this.ammoUsage = ammoUsage;
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
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public Damage use(Player player) {
		if (this.ammo - this.ammoUsage > 0){
			this.ammo -= this.ammoUsage;
			int hit = (int)(Math.random() * 100 + 1);
			if (1 <= hit && hit <= this.accuracy) {
				Zork.combatHistory.add("ANG3L: Good shot!");
				return new Damage(this.power);
			}
			else Zork.combatHistory.add("ANG3L: You missed!");
		}
		else this.deactivate();

		return new Damage(0);
	}

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), "", this.ammo, this.ammoUsage, this.power, this.accuracy, 1, 0, 0, 0);
	}

	public String toString() {
		return String.format("Weapon - %s, weight: %s, power: %d, accuracy: %d, ammo: %d, ammoUsage: %d",
				super.getName(), super.getWeight(), this.power, this.accuracy, this.ammo, this.ammoUsage);
	}

	private int power = 0;
	private int accuracy = 0;
	private int ammo = 0;
	private int ammoUsage = 0;

} // end Weapon