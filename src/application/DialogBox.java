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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The main DialogBox JavaFX function to display Game Story
 *
 * @author Andrei Ivanovic
 *
 */
public class DialogBox {

	/**
	 * Constructor: passing message to display and image to show
	 *
	 * @param msg
	 * @param imageFileName
	 */
	public DialogBox(String title, String msg, String imageFileName) {

		this.message = msg;
		this.title = title;
		this.imageFileName = imageFileName;
	}

	/**
	 * Display the dialog box
	 */
	public void display() {

		// VBox for the entire dialog
    	VBox vbox = new VBox();
		Scene dialogBoxScene = new Scene(vbox);

		// StackPane as the second node in VBox
		StackPane imagePane = new StackPane();
		imagePane.setPrefSize(WIDTH, 250);
		imagePane.setPadding(new Insets(5, 5, 5, 5));

		Image image = new Image( DialogBox.class.getResourceAsStream(this.imageFileName) );
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(WIDTH - 20);
		imageView.setFitHeight(250);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);

		//imageView.fitWidthProperty().bind(this.stage.widthProperty());
		//imageView.fitHeightProperty().bind(this.stage.heightProperty());

		StackPane.setAlignment(imageView, Pos.CENTER);
		imagePane.getChildren().add(imageView);

		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefSize(WIDTH, 325);
		textArea.setWrapText(true);
		textArea.setText(this.message);

		StackPane bottomPane = new StackPane();
		bottomPane.setPrefSize(WIDTH, 40);

		// OK button in the stack pane
		Button okButton = new Button("OK");
		okButton.setPrefSize(80, 30);
		StackPane.setAlignment(okButton, Pos.CENTER);
		bottomPane.getChildren().add(okButton);

		// add the textArea and StackPane to VBox
		vbox.getChildren().addAll(imagePane, textArea, bottomPane);

		okButton.setOnAction(e -> this.handleClose(e));

		this.stage = new Stage();
    	this.stage.setTitle(this.title);
    	this.stage.setWidth(WIDTH);
    	this.stage.setHeight(HEIGHT);
    	this.stage.setResizable(false);
    	this.stage.initModality(Modality.APPLICATION_MODAL);

    	this.stage.setScene(dialogBoxScene);
    	this.stage.showAndWait();
	}

    /**
     * Closing handler
     *
     * @param actionEvent
     */
    public void handleClose(ActionEvent actionEvent) {
        // close the dialog.
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Zork.commandEntryTextField.requestFocus();
            }
        });
    }

    final private int WIDTH = 610;
    final private int HEIGHT = 650;

	private Stage stage = null;

	private String title = null;
	private String message = null;
	private String imageFileName = null;

} // end Dialog Box