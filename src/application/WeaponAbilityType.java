/**
 *
 */
package application;

/**
 * Keeps the list of different Weapon ability types
 *
 * @author Andrei Ivanovic
 *
 */
public enum WeaponAbilityType {

	WEAPON_ABILITY_NONE,
	WEAPON_ABILITY_EXPLODE, //deal damage = to the grenade's power to enemy
	WEAPON_ABILITY_NERVE, //end combat instantly against an enemy
	WEAPON_ABILITY_SLEEP, //skip 5 turns (enemy)
	WEAPON_ABILITY_STUN, //skip 1 turn (enemy)
	WEAPON_ABILITY_BURN, //do a little extra damage every turn to enemy(+2)
	WEAPON_ABILITY_SHOCK, //decreases opponent's accuracy (10%)
	WEAPON_ABILITY_VAMPIRE, //heals you (+2)
	WEAPON_ABILITY_SCALE; //increases the power of your weapon each time you use it (+1)

	/**
	 * toString()
	 */
	public String toString() {
		return WeaponAbilityType.abilityTypeToString(this);
	}

	/**
	 * Convert type to String
	 *
	 * @param type
	 * @return
	 */
	public static String abilityTypeToString(WeaponAbilityType type) {

		if (type == WeaponAbilityType.WEAPON_ABILITY_EXPLODE)
			return "Explode";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_NERVE)
			return "Nerve";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_SLEEP)
			return "Sleep";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_STUN)
			return "Stun";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_BURN)
			return "Burn";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_SHOCK)
			return "Shock";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_VAMPIRE)
			return "Vampire";
		else if (type == WeaponAbilityType.WEAPON_ABILITY_SCALE)
			return "Scale";
		else
			return "None";
	}

	/**
	 * Convert string input to actual WeaponAbilityType type
	 * @param ability
	 * @return
	 */
	public static WeaponAbilityType abilityStringToType(String ability) {

		if (ability == null || ability.isEmpty()) return WeaponAbilityType.WEAPON_ABILITY_NONE;

		if ("explode".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_EXPLODE;
		else if ("nerve".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_NERVE;
		else if ("sleep".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_SLEEP;
		else if ("stun".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_STUN;
		else if ("burn".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_BURN;
		else if ("shock".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_SHOCK;
		else if ("vampire".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_VAMPIRE;
		else if ("scale".equalsIgnoreCase(ability))
			return WeaponAbilityType.WEAPON_ABILITY_SCALE;
		else
			return WeaponAbilityType.WEAPON_ABILITY_NONE;
	}

} // end WeaponAbilityType