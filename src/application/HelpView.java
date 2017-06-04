/**
 *
 */
package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Showing Help file from HTML source [WebView is crashing on HTML from the Internet)
 *
 * @author Andrei Ivanovic
 *
 */
public class HelpView extends Region {

	/**
	 * Constructor
	 */
	public HelpView() {
		//
	}

	/**
	 * Display
	 */
	public void display() {

		try {
			this.browser = new WebView();
			this.webEngine = this.browser.getEngine();

			//URL url = getClass().getResource("help.html");
			//webEngine.load(url.toExternalForm());

			this.webEngine.load("https://github.com/aivanovic1/Zork-Code/wiki");

			this.browser.setPrefSize(780, 500);

			HBox bottomHbox = new HBox();
			bottomHbox.setPadding(new Insets(10,10,10,10));
			bottomHbox.setSpacing(10);   // Gap between nodes
			bottomHbox.setStyle("-fx-background-color: #336699;");

			Button okBtn = new Button("OK");
			okBtn.setPrefSize(100, 30);
			bottomHbox.getChildren().addAll(okBtn);

			okBtn.setOnAction(e -> this.handleClose(e));

			getChildren().addAll(this.browser);

			BorderPane pane = new BorderPane();
			pane.setCenter(this);
			pane.setBottom(bottomHbox);

			Scene scene = new Scene(pane); //pane, 750,500, Color.web("#666970"));

			Stage stage = new Stage();
			stage.setTitle("Zork Help");
			stage.setHeight(500);
			stage.setWidth(800);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);

			//scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
			stage.showAndWait();
		} catch (Exception e) {
		}
	}


	/**
	 * Dialog closing handler
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

	private WebView browser = null;
	private WebEngine webEngine = null;

} // end HelpView