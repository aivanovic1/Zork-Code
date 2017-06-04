/**
 *
 */
package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is the process which gets triggered only when we come to the end of the game!
 * It is a count-down from 60sec
 *
 * @author Andrei Ivanovic
 *
 */
public class LastMinute implements Runnable {

	/**
	 * Last minute constructor
	 */
	public LastMinute() {
		// nothing
	}

	/**
	 * Starts the thread that keeps counter displayed from 60 sec to 1 sec
	 */
	public void start() {

		Zork.finalCountdownStarted = true;

		Zork.finalMinuteTimerLabel.setText("Remaining time: 60 sec");
		Zork.finalMinuteTimerLabel.setVisible(true);

		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Thread runs this method
	 */
	@Override
	public void run() {

		for (int i = 59; i > 0; --i) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			final int counter = i;

			Platform.runLater( () -> Zork.finalMinuteTimerLabel.setText(String.format("Remaining time: %d sec", counter)));
		}

		Zork.gameTerminated = true;
		Platform.runLater( () -> Zork.finalMinuteTimerLabel.setText("GAME OVER!"));

		// display the last message
		Platform.runLater( () -> showLastMessage());
	}

	/**
	 * Shows the GAME CLOSING message
	 */
	private void showLastMessage() {

		// VBox for the entire dialog
    	VBox endGameVBox = new VBox();
		Scene endingScene = new Scene(endGameVBox);

		// text area to be the first UI control in VBox
		TextArea endingText = new TextArea();
		endingText.setPrefColumnCount(80);
		endingText.setPrefHeight(470);
		endingText.setEditable(false);
		endingText.setWrapText(true);

		// need to use the proper ending message - depending on the final room
		if ("A".equalsIgnoreCase(Zork.currentRoom.getId())) endingText.setText(Zork.endingMessageList.get(0));
		else if ("&".equalsIgnoreCase(Zork.currentRoom.getId())) endingText.setText(Zork.endingMessageList.get(1));
		else endingText.setText(Zork.endingMessageList.get(2));

		// StackPane as the second node in VBox
		StackPane bottomPane = new StackPane();
		bottomPane.setPrefSize(900, 30);
		bottomPane.setPadding(new Insets(10, 10, 10, 10));

		// Button in the stack pane
		Button okButton = new Button("OK");
		okButton.setPrefSize(100, 30);
		StackPane.setAlignment(okButton, Pos.CENTER);

		// add button to the StackPane
		bottomPane.getChildren().addAll(okButton);

		// add the textArea and StackPane to VBox
		endGameVBox.getChildren().addAll(endingText, bottomPane);

    	okButton.setOnAction(e -> this.exitApp(e));

    	Stage stage = new Stage();
    	stage.setTitle("Ending the game ...");
    	stage.setWidth(900);
    	stage.setHeight(500);
    	stage.setResizable(false);
    	stage.initModality(Modality.APPLICATION_MODAL);

    	stage.setOnCloseRequest(event -> {
    	    System.out.println("Stage is closing");
    	    Platform.exit();
    	});

    	stage.setScene(endingScene);
    	stage.show();
	}

	/**
	 * Exit the app
	 *
	 * @param actionEvent
	 */
    public void exitApp(ActionEvent actionEvent) {
        // close the dialog.
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

        Platform.exit();
      }

} // end LastMinute