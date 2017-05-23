/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class ExoticMeleeWeapon extends Item {
	public ExoticMeleeWeapon(String name, int weight, String ability, int power) {
		super(name, weight);
		this.ability = WeaponAbilityType.abilityStringToType(ability);
		this.power = power;
	}
	/**
	 * @return the ability
	 */
	public String getAbilityAsString() {
		return WeaponAbilityType.abilityTypeToString(this.ability);
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

	public String toString() {
		return String.format("ExoticMeleeWeapon - %s, weight: %s, ability: %s, power: %d",
				super.getName(), super.getWeight(), this.ability, this.power);
	}

	private WeaponAbilityType ability;
	private int power = 0;

}//end ExoticMeleeWeapon
