/**
 *
 */
package application;

/**
 * One of the weapon's item type
 *
 * @author Andrei Ivanovic
 */
public class ExoticWeapon extends Item {

	/**
	 * Constructor
	 *
	 * @param name
	 * @param weight
	 * @param ability
	 * @param power
	 * @param accuracy
	 * @param ammo
	 * @param ammoUsage
	 */
	public ExoticWeapon(String name, int weight, String ability, int power, int accuracy, int ammo, int ammoUsage) {
		super(name, weight, ItemType.ITEM_TYPE_EXOTIC);
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
	 * Set power
	 * @param p
	 */
	public void setPower(int p) {
		this.power = p;
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

	/**
	 * Combat: Use exotic weapon
	 */
	public Damage use(Player player) {

		if (this.ammo - this.ammoUsage >= 0) {

			this.ammo -= this.ammoUsage;
			int hit = (int)(Math.random() * 100 + 1);

			//System.out.printf("ExoticWeapon: ammo reduced to: %d, hit: %d, accuracy: %d%n", this.ammo, hit, this.accuracy);

			if (1 <= hit && hit <= this.accuracy) {

				//System.out.printf("  Hit! Damage delt: %d%n", this.power);
				Zork.combatHistory.add("ANG3L: Bullseye!");

				return new Damage(this.power, this.ability);
			}
			else {
				//System.out.printf("  Missed!%n", hit);
				Zork.combatHistory.add("ANG3L: Just scraped them!");
			}
		}

		if (this.ammo - this.ammoUsage < 0) this.deactivate();

		return new Damage(0);
	}

	/**
	 * This is helper function to convert this item for display in Inventory Panel
	 */
	@Override
	public ExtendedItem convertToExtendedItem() {

		return new ExtendedItem(this.getName(), this.getType(), this.getWeight(), this.getAbilityAsString(), this.ammo, this.ammoUsage, this.power, this.accuracy, 1, 0, 0, 0);
	}

	/**
	 * toString()
	 */
	public String toString() {
		return String.format("ExoticWeapon - %s, weight: %s, ability: %s, power: %d, accuracy: %d, ammo: %d, ammUsage: %d",
				super.getName(), super.getWeight(), this.ability.toString(), this.power, this.accuracy, this.ammo, this.ammoUsage);
	}

	private int power = 0;
	private int accuracy = 0;
	private int ammo = 0;
	private int ammoUsage = 0;

	private WeaponAbilityType ability = WeaponAbilityType.WEAPON_ABILITY_NONE;

} // end ExoticWeapon