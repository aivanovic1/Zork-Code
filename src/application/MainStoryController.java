/**
 *
 */
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Loads the first story of the Game
 * This is built with SceneBuilder tool
 *
 * @author Andrei Ivanovic
 *
 */
public class MainStoryController {

	/**
	 * Constructor
	 */
	public MainStoryController() {
		//System.out.println("MainStoryController c-tor!");
	}

	@FXML
	private Button okButton;

	@FXML
	private TextArea storyTextArea;

    @FXML
    public void initialize() {
    	//System.out.println("MainStoryController::initialize() method is invoked!");

    	this.storyTextArea.setWrapText(true);
    	this.storyTextArea.setText(Zork.startingRoom.getStory());

    	this.okButton.setOnAction(e -> this.handleClose(e));
    }

    /**
     * Close handler
     *
     * @param actionEvent
     */
    public void handleClose(ActionEvent actionEvent) {
        // close the dialog.
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
      }

} // end MainStoryController