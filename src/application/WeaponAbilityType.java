/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */
public enum WeaponAbilityType {

	WEAPON_ABILITY_NONE,
	WEAPON_ABILITY_EXPLODE,
	WEAPON_ABILITY_NERVE,
	WEAPON_ABILITY_SLEEP,
	WEAPON_ABILITY_STUN,
	WEAPON_ABILITY_BURN,
	WEAPON_ABILITY_SHOCK,
	WEAPON_ABILITY_VAMPIRE,
	WEAPON_ABILITY_SCALE;

	public String toString() {
		return WeaponAbilityType.abilityTypeToString(this);
	}

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