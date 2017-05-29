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
 * @author Andrei Ivanovic
 *
 */
public class MainStoryController {

	/**
	 * C-tor
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
    	this.storyTextArea.setText(Zork.entranceRoom.getStory());

    	this.okButton.setOnAction(e -> this.handleClose(e));
    }

    public void handleClose(ActionEvent actionEvent) {
        // close the dialog.
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
      }

} // end MainStoryController