package application;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main Zork class - starts JavaFX UI app
 * All UI screens are composed with SceneBuilder
 *
 * @author Andrei Ivanovic
 *
 */
public class Zork extends Application {

	private Stage primaryStage;
    private BorderPane mainWindow;

    protected static Player player = null;
    protected static Map<String, Room> roomsMap = null;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Zork");

        this.showLoadingScreen();

        RoomsAndMapParser roomParser = new RoomsAndMapParser("./src/application/RoomLayout.dat");
        this.roomsMap = roomParser.loadMap();

        Zork.player = roomParser.getPlayer();

        // FOR TESTING ONLY
//        System.out.println("\n\n********* List all rooms **********\n");
//        for(Room r : this.roomsMap.values()) {
//        	System.out.println(r.toString());
//        }

        // FOR TESTING
//        Room r = this.roomsMap.get("*");
//        r.print();

        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

        this.initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Zork.class.getResource("MainWindow.fxml"));
            mainWindow = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(mainWindow);
            primaryStage.setScene(scene);
            this.primaryStage.setResizable(true);
            this.primaryStage.sizeToScene();
            primaryStage.show();

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2);
            this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showLoadingScreen() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Zork.class.getResource("LoadingScreen.fxml"));
            AnchorPane splashScreen = (AnchorPane)loader.load();

            final Dimension d = new Dimension(960, 640);
            final Rectangle rectangle = new Rectangle(d.width, d.height, Color.POWDERBLUE);
            splashScreen.maxWidth(d.height);
            splashScreen.maxWidth(d.width);

            // Show the scene containing the anchorPane layout.
            Scene scene = new Scene(splashScreen);
            this.primaryStage.setScene(scene);

            this.primaryStage.setResizable(false);
            this.primaryStage.sizeToScene();

            this.primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}	// end Zork