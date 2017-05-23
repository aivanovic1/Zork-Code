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

	public String toString() {
		return String.format("Name: %s, Health: %d, Power: %d, Accuracy: %d", name, health, power, accuracy);
	}

	private String name = null;
	private int health = 0;
	private int power = 0;
	private int accuracy = 0;
	private int reduction = 0;

}//end character
