/**
 *
 */
package application;

/**
 * One of the weapon items
 *
 * @author Andrei Ivanovic
 *
 */
public class ExoticMeleeWeapon extends Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param ability
	 * @param power
	 */
	public ExoticMeleeWeapon(String name, int weight, String ability, int power) {
		super(name, weight, ItemType.ITEM_TYPE_EXOTIC_MELEE);
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

	/**
	 * Combat: use of Melee weapon
	 */
	public Damage use(Player player) {
		Zork.combatHistory.add("ANG3L: Clean cut!");

		//System.out.printf("ExoticMeleeWeapon: power delt: %d%n", this.power);

		return new Damage(power,ability);
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), this.getAbilityAsString(), 0, 0, this.power, 0, 1, 0, 0, 0);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("ExoticMeleeWeapon - %s, weight: %s, ability: %s, power: %d",
				super.getName(), super.getWeight(), this.ability.toString(), this.power);
	}

	private int power = 0;
	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} // end ExoticMeleeWeapon