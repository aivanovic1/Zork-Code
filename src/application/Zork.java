package application;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	// the main pane for the app
    private BorderPane mainWindow;

    protected static Player player = null;
    protected static Room entranceRoom = null;
    protected static Stage primaryStage = null;

    protected static Room currentRoom = null;

    //only used in combat panel
    protected static ObservableList<String> combatHistory = null;

    // used in the main panel to kep the message history
    protected static ObservableList<String> mainHistoryList = null;

    // last minute handling
    protected static boolean finalCountdownStarted = false;
    protected static boolean gameTerminated = false;
    protected static Label finalMinuteTimerLabel = null;

    protected static TextField commandEntryTextField = null;

    // keep all rooms in the HashMap, accessible from any part of the game
    protected static Map<String, Room> roomsMap = null;
    protected static List<String> endingMessageList = null;
    protected static RoomEntryDirection roomEntryDirection = RoomEntryDirection.ROOM_ENTRY_DIRECTION_UNKNOWN;

    private final static int MAX_MSG_SIZE = 120000;

    // start() is the main method when running Java FX applications

    @Override
    public void start(Stage primaryStage) {
    	Zork.primaryStage = primaryStage;
    	Zork.primaryStage.setTitle("Zork");

        // loading graphics
        this.showLoadingScreen();

        // parse the game map and definitions for Player, Rooms, Inventory and much more ...
        RoomsAndMapParser roomParser = new RoomsAndMapParser("./src/application/RoomLayout.dat");
        this.roomsMap = roomParser.loadMap();

        for(Room r : Zork.roomsMap.values()) {
        	if (r.isEntranceRoom()) {
        		Zork.entranceRoom = r;
        		break;
        	}
        }

        // remember the Player object
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
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

        // load the main Game screen
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
            Zork.primaryStage.setScene(scene);
            Zork.primaryStage.setResizable(false);
            //Zork.primaryStage.sizeToScene();
            Zork.primaryStage.setHeight(750);
            Zork.primaryStage.setWidth(1100);
            Zork.primaryStage.setTitle("Zork");
            Zork.primaryStage.show();

            // center the screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            Zork.primaryStage.setX((primScreenBounds.getWidth() - Zork.primaryStage.getWidth()) / 2);
            Zork.primaryStage.setY((primScreenBounds.getHeight() - Zork.primaryStage.getHeight()) / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The first graphics loading screen
     */
    public void showLoadingScreen() {
        try {
            // Loading the intro graphics screen
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
	 * Cut the message in the history panel in smaller sizes
	 *
	 * @param message
	 */
	public static void displayInHistoryPanel(String message) {

		if (message == null || message.isEmpty()) return;

		//if (message.length() > 32) System.out.println("Last 30 characters: [" + message.substring(message.length() - 30) + "]");

		if (message.length() <= MAX_MSG_SIZE) {

			Zork.mainHistoryList.add(message);

		} else {
			String arr[] = message.split(" ");

			String msg = "";
			for(String s : arr) {
				if (msg.isEmpty()) msg += s;
				else msg += (" " + s);

				if (msg.length() >= MAX_MSG_SIZE) {
					Zork.mainHistoryList.add(msg);
					msg = "";
				}
			}

			if (!msg.isEmpty()) Zork.mainHistoryList.add(msg);
		}
	}

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // Java FX code
    public static void main(String[] args) {
        launch(args);
    }

}	// end Zork