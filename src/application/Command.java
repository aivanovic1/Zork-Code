/**
 *
 */
package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Command that user enters in the main game panel
 *
 * @author Andrei Ivanovic
 *
 */
public class Command {

	/**
	 * Command understand main verb and parameters that might be attached to the verb: 'take knife'
	 *
	 * @param verb
	 */
	public Command(String verb) {
		this.verb = verb;
		this.params = new ArrayList<String>();
	}

	/**
	 * Add method
	 *
	 * @param param
	 */
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
	 * @return the originalEnteredCommnand
	 */
	public String getOriginalEnteredCommand() {
		return originalEnteredCommnand;
	}

	/**
	 * @param originalEnteredCommnand the originalEnteredCommnand to set
	 */
	public void setOriginalEnteredCommand(String originalEnteredCommnand) {
		this.originalEnteredCommnand = originalEnteredCommnand;
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
	private String originalEnteredCommnand = null;

} // end Command