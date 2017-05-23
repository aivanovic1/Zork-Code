/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Grenade extends Item {

	public Grenade(String name, int weight, String ability, int power) {
		super(name, weight);
		this.ability = WeaponAbilityType.abilityStringToType(ability);
		this.power = power;
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

	public String toString() {
		return String.format("Grenade - %s, weight: %s, ability: %s, power: %d",
				super.getName(), super.getWeight(), this.ability.toString(), this.power);
	}

	private int power = 0;
	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} //end Grenade
