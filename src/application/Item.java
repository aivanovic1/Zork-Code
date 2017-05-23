/**
 *
 */
package application;

/**
 * @author Andrei Ivanovic
 *
 */

public class Item {

	public Item(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	public String toString() {
		return String.format("Item - %s, weight: %s", this.getName(), this.getWeight());
	}

	private String name = null;
	private int weight = 0;

} // end Item