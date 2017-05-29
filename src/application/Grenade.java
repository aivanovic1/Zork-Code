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
		super(name, weight, ItemType.ITEM_TYPE_GRENADE);
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

	public Damage use(Player player) {
		player.getPlayerInventory().removeItem(this);
		Zork.combatHistory.add("ANG3L: Perfect throw!");
		return new Damage(this.power, this.ability);
	}

	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), this.getAbilityAsString(), 0, 0, this.power, 0, 1, 0, 0, 0);
	}

	public String toString() {
		return String.format("Grenade - %s, weight: %s, ability: %s, power: %d",
				super.getName(), super.getWeight(), this.ability.toString(), this.power);
	}

	private int power = 0;
	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} // end Grenade