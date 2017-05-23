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
		super(name, weight);
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

	public String toString() {
		return String.format("Weapon - %s, weight: %s, power: %d, accuracy: %d, ammo: %d, ammUsage: %d",
				super.getName(), super.getWeight(), this.power, this.accuracy, this.ammo, this.ammoUsage);
	}

	private int power = 0;
	private int accuracy = 0;
	private int ammo = 0;
	private int ammoUsage = 0;

} // end Weapon