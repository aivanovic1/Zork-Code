/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public class Character {

	public Character(String name, int health, int power, int accuracy, int reduction) {
		this.name = name;
		this.health = health;
		this.power = power;
		this.accuracy = accuracy;
		this.reduction = reduction;
	}
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	/**
	 * @param reduction the reduction to set
	 */
	public void setReduction(int reduction) {
		this.reduction = reduction;
	}
	/**
	 * @return the reduction
	 */
	public int getReduction() {
		return reduction;
	}
	/**
	 * @return the turnsToSkip
	 */
	public int getTurnsToSkip() {
		return turnsToSkip;
	}
	/**
	 * @param turnsToSkip the turnsToSkip to set
	 */
	public void setTurnsToSkip(int turnsToSkip) {
		this.turnsToSkip = turnsToSkip;
	}

	public void decrementTurnsToSkip() {
		if (this.turnsToSkip > 0) this.turnsToSkip--;
	}

	public void decrementHealth(int damage) {
		if (this.reduction > 0) this.health -= (damage * (this.reduction/100.0));
		else this.health -= damage;

		if (this.health < 0) this.health = 0;
	}

	public void takeDamage(Damage damage) {
		if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_EXPLODE) {
			decrementHealth(damage.getPower());
			return;
		} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_SLEEP) {
			this.setTurnsToSkip(5);
		} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_NERVE) {
			this.health = 0;
		} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_STUN) {
			this.setTurnsToSkip(1);
		} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_BURN) {
			this.decrementHealth(2);
		} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_SHOCK) {
			this.accuracy /= 2;
		}

		this.decrementHealth(damage.getPower());
		Zork.combatHistory.add("ANG3L: By the looks of it, they're at about " + this.health + " health!");
	}

	public boolean isAlive() {
		return this.health > 0;
	}

	public String toString() {
		return String.format("Name: %s, Health: %d, Power: %d, Accuracy: %d", name, health, power, accuracy);
	}

	private String name = null;
	private int health = 0;
	private int power = 0;
	private int accuracy = 0;
	private int reduction = 0;
	private int turnsToSkip = 0;

} // end Character