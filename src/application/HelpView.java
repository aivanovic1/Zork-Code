/**
 *
 */
package application;

import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Andrei Ivanovic
 *
 */
public class HelpView extends Region {

	public HelpView() {

	}

	public void display() {

		this.browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		//webEngine.load("file://application/help.html");

		URL url = getClass().getResource("help.html");
		webEngine.load(url.toExternalForm());

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

		Scene scene = new Scene(pane, 750,500, Color.web("#666970"));

		Stage stage = new Stage();
        stage.setTitle("Zork Help");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        //scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
	}

    // close help dialog box
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

} // end HelpView