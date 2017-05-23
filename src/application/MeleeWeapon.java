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
		super(name, weight);
		this.power = power;
	}
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}

	public String toString() {
		return String.format("MeleeWeapon - %s, weight: %s, power: %d",
				super.getName(), super.getWeight(), this.power);
	}

	private int power = 0;

} // end MeleeWeapon
