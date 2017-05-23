/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class ExoticWeapon extends Item {

	public ExoticWeapon(String name, int weight, String ability, int power, int accuracy, int ammo, int ammoUsage) {
		super(name, weight);
		this.ability = WeaponAbilityType.abilityStringToType(ability);
		this.power = power;
		this.accuracy = accuracy;
		this.ammo = ammo;
		this.ammoUsage = ammoUsage;
	}
	/**
	 * @return the ability
	 */
	public String getAbilityAsString() {
		return WeaponAbilityType.abilityTypeToString(ability);
	}
	/**
	 * @return the ability
	 */
	public WeaponAbilityType getAbility() {
		return ability;
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
		return String.format("ExoticWeapon - %s, weight: %s, ability: %s, power: %d, accuracy: %d, ammo: %d, ammUsage: %d",
				super.getName(), super.getWeight(), this.ability.toString(), this.power, this.accuracy, this.ammo, this.ammoUsage);
	}

	private int power = 0;
	private int accuracy = 0;
	private int ammo = 0;
	private int ammoUsage = 0;

	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} //end ExoticWeapon
