/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Damage {

	public Damage (int power, WeaponAbilityType ability) {
		this.power = power;
		this.ability = ability;
	}

	public Damage (int power) {
		this.power = power;
	}
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * @return the ability
	 */
	public WeaponAbilityType getAbility() {
		return ability;
	}

	private int power = 0;
	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} // end Damage