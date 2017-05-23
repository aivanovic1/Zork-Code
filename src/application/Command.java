/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Ivanovic
 *
 */
public class Command {

	public Command(String verb) {
		this.verb = verb;
		this.params = new ArrayList<String>();
	}

	public void addParam(String param) {
		this.params.add(param);
	}

	/**
	 * @return the verb
	 */
	public String getVerb() {
		return verb;
	}
	/**
	 * @param index
	 * @return param at index position starting from 1
	 */
	public String getParam(int index) {
		if (index < 1 || index > this.params.size()) return null;
		return this.params.get(index-1);
	}

	/**
	 * @return the params
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * Generates a String containing all params with the verb
	 * @return String
	 */
	public String generateFullCommand(){
		String fullCommand = this.verb;

		for (String s : this.params){
			fullCommand += (" " + s);
		}

		return fullCommand;
	}
	/**
	 * Generates a String containing all params without the verb
	 * @return String
	 */
	public String generateFullCommandNoVerb(){
		String fullCommandNoVerb = "";

		for (String s : this.params){
			fullCommandNoVerb += (" " + s);
		}

		return fullCommandNoVerb.trim();
	}

	private String verb = null;
	private List<String> params = null;

} // end Command