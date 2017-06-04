/**
 *
 */
package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Panel dedicated to combat only
 *
 * @author Andrei Ivanovic
 *
 */
public class CombatPanel {

	/**
	 * Create the Combat Panel class
	 *
	 * @param r
	 * @param player
	 */
	public CombatPanel(Room r, Player player) {
		this.room = r;
		this.player = player;

		this.playerInventory = this.player.getPlayerInventory();
		this.characters = this.room.getCharacters();
	}

	/**
	 * Start the Java FX panel display
	 */
	public void start() {

		HBox hbox = new HBox();
		Scene combatScene = new Scene(hbox);

		StackPane leftImagePane = new StackPane();
		leftImagePane.setPrefSize(IMAGE_PANEL_WIDTH, HEIGHT);
		leftImagePane.setPadding(new Insets(5, 5, 5, 5));

		Image leftImage = new Image( CombatPanel.class.getResourceAsStream(this.player.getImageFileName()) );
		ImageView leftImageView = new ImageView();
		leftImageView.setImage(leftImage);
		leftImageView.setFitWidth(IMAGE_PANEL_WIDTH - 5);
		leftImageView.setPreserveRatio(true);
		leftImageView.setSmooth(true);
		leftImageView.setCache(true);
		StackPane.setAlignment(leftImageView, Pos.CENTER);
		leftImagePane.getChildren().add(leftImageView);

		StackPane rightImagePane = new StackPane();
		rightImagePane.setPrefSize(IMAGE_PANEL_WIDTH, HEIGHT);
		rightImagePane.setPadding(new Insets(5, 5, 5, 5));

		Image rightImage = new Image( CombatPanel.class.getResourceAsStream(this.room.getEnemyImageFileName()) );
		ImageView rightImageView = new ImageView();
		rightImageView.setImage(rightImage);
		rightImageView.setFitWidth(IMAGE_PANEL_WIDTH - 5);
		rightImageView.setPreserveRatio(true);
		rightImageView.setSmooth(true);
		rightImageView.setCache(true);
		StackPane.setAlignment(rightImageView, Pos.CENTER);
		rightImagePane.getChildren().add(rightImageView);

		// VBox for the entire dialog
    	VBox vbox = new VBox();
		//Scene combatScene = new Scene(vbox);

    	// add things to hbox
    	hbox.getChildren().addAll(leftImagePane, vbox, rightImagePane);

		// StackPane as the second node in VBox
		StackPane topPane = new StackPane();
		topPane.setPrefSize(TEXT_AREA_WIDTH, 40);
		topPane.setPadding(new Insets(10, 10, 10, 10));

		Label label = new Label();
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		label.setText("Combat in progress ...");
		StackPane.setAlignment(label, Pos.CENTER_LEFT);
		topPane.getChildren().add(label);

		// Button in the stack pane
		returnButton.setPrefSize(80, 30);
		returnButton.setVisible(false);
		StackPane.setAlignment(returnButton, Pos.CENTER_RIGHT);

		// add button to the StackPane
		topPane.getChildren().add(returnButton);

		combatMessageList.setPrefWidth(TEXT_AREA_WIDTH);
		combatMessageList.setPrefHeight(400);
		combatMessageList.setItems(historyItems);

		Zork.combatHistory = historyItems;

		// get the inventory be loaded for display
		this.printCombatStatus();

		textFieldEntry.setPromptText("Enter the 'use' command for the combat ...");
		textFieldEntry.setPrefSize(TEXT_AREA_WIDTH, 30);

		textFieldEntry.setOnAction(e -> commandProcessing(e));

		// add the textArea and StackPane to VBox
		vbox.getChildren().addAll(topPane, combatMessageList, textFieldEntry);

		returnButton.setOnAction(e -> this.handleClose(e));

        // trick from Internet how to display multi-line text in the list-view
        // https://stackoverflow.com/questions/9964579/javafx-setwraptexttrue-wordwrap-doesnt-work-in-listview
		combatMessageList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(final ListView<String> list) {
            	return new ListCell<String>() {
            		{
	                    Text text = new Text();
	                    text.wrappingWidthProperty().bind(list.widthProperty().subtract(15));
	                    text.textProperty().bind(itemProperty());

	                    setPrefWidth(0);
	                    setGraphic(text);
	                }
            	};
            }
        });

    	this.stage.setTitle("Zork Combat Scene");
    	this.stage.setWidth(WIDTH);
    	this.stage.setHeight(HEIGHT);
    	this.stage.setResizable(false);
    	this.stage.initModality(Modality.APPLICATION_MODAL);
    	//this.stage.initStyle(StageStyle.UNDECORATED);

    	// on the window close
    	this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!combatCompleted) event.consume();
                else closeAudio();
            }
        });

    	// start the sound, if the file is specified (!= null)
    	this.playAudio(this.room.getSoundFileName());

    	this.stage.setScene(combatScene);
    	this.stage.showAndWait();
	}

	/**
	 * This is the main method for combat logic
	 * @param useCommandParam
	 */
	private void combat(String useCommandParam) {

		// TODO remove after testing
//		if (useCommandParam.equalsIgnoreCase("x")) {
//			combatCompleted = true;
//			returnButton.setVisible(true);
//		}

		int inventoryIndex = 0;

		try {
			inventoryIndex = Integer.parseInt(useCommandParam);

			if (inventoryIndex < 1 || inventoryIndex > this.activeInventory.size()) {
				historyItems.add("Item # needs to be between 1 and " + this.activeInventory.size() + "!");
			}
			else {

				Item item = this.activeInventory.get(inventoryIndex - 1);

				historyItems.add("You selected: " + item.getName());
				Damage damage = item.use(this.player);

				if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_VAMPIRE) {
					player.incrementHealth(2);

				} else if (damage.getAbility() == WeaponAbilityType.WEAPON_ABILITY_SCALE) {

					if (item.getType() == ItemType.ITEM_TYPE_EXOTIC) {
						((ExoticWeapon) item).setPower(((ExoticWeapon) item).getPower() + 1);
					}
				}

				Character enemy = this.characters.get(0);

				if (enemy == null) {
					historyItems.add("ANG3L: Good job, you killed them all!");
					this.combatCompleted = true;

					// Win - close the Combat panel, go to the main screen
			        Platform.runLater(new Runnable() {
			            @Override
			            public void run() {
			            	closeCombatDialogBoxOnWin();
			            }
			        });
				}
				else {

					// calculate the damage to the enemy
					enemy.takeDamage(damage);

					if (enemy.getHealth() == 0) {
						// enemy died, remove it from the Room's characters list
						this.characters.remove(0);
					}
					else {

						// enemy strikes
						if (enemy.getTurnsToSkip() == 0) {
							opponentCombat(); // opponents hits
						}
						else {
							enemy.decrementTurnsToSkip();
							historyItems.add("ANG3L: Enemy is knocked out! They're gonna get up soon: " + enemy.getTurnsToSkip() + " turns!");
						}
					}

					if (Zork.currentRoom.getTotalAliveCharacters() == 0) {

						// Win - close the Combat panel, go to the main screen
				        Platform.runLater(new Runnable() {
				            @Override
				            public void run() {
				            	closeCombatDialogBoxOnWin();
				            }
				        });
					}
				}

				if (!player.isAlive()) {
					historyItems.add("ANG3L: Operator! Your vitals are at 0! Game over, man! Game over!");

					this.closeAudio();

					// Win - close the Combat panel, go to the main screen
			        Platform.runLater(new Runnable() {
			            @Override
			            public void run() {
			            	closeCombatDialogBoxOnLoss();
			            }
			        });
				}
			}

		} catch (NumberFormatException e) {

			historyItems.add("You have to specify # of the item in your inventory, starting from 1!");
		}

		if ( player.isAlive() ) {

	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	            	textFieldEntry.requestFocus();
	            }
	        });
		}
	}

	/**
	 * Enemy's turn for combat (they simply attack)
	 */
	private void opponentCombat() {

		if (this.characters.isEmpty()) return;

		int damageDealt = 0;
		Character enemy = this.characters.get(0);

		int hit = (int)(Math.random() * 100 + 1);
		if (1 <= hit && hit <= enemy.getAccuracy()){

			damageDealt = enemy.getPower();
			Zork.combatHistory.add("ANG3L: They got a shot off!");
			this.player.decrementHealth(damageDealt);
		}
		else {
			Zork.combatHistory.add("ANG3L: They missed!");
		}
	}

	/**
	 * Main command processing controller
	 *
	 * @param event
	 */
	private void commandProcessing(ActionEvent event) {

		if ((textFieldEntry.getText() != null && !textFieldEntry.getText().isEmpty())) {

			Command command = null;

			String enteredCommand = textFieldEntry.getText().trim();

			if (!enteredCommand.toLowerCase().startsWith("use ")) {
				historyItems.add("You can only use 'use' command to combat!");
			}
			else {
				command = new Command("use");
				command.addParam(enteredCommand.substring(3).trim());
				command.setOriginalEnteredCommand(enteredCommand);

				//historyItems.add("Command entered: " + command.getOriginalEnteredCommand());

				// invoke the combat logic with the parameter passed to USE command
				combat(command.getParam(1));
			}

			textFieldEntry.clear();
			textFieldEntry.setPromptText("Enter the 'use' command for the combat ...");

			this.printCombatStatus();

        }
	}

	/**
	 * Close inventory dialog box
	 *
	 * @param actionEvent
	 */
    public void handleClose(ActionEvent actionEvent) {
        // close the dialog.
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

		this.closeAudio();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Zork.commandEntryTextField.requestFocus();
            }
        });
      }

    /**
     * Print combat status
     */
	private void printCombatStatus() {

		String message = null;
		this.activeInventory.clear();

		if (this.player.getPlayerInventory().getInventory() == null || this.player.getPlayerInventory().getTotalActiveInventorySize() == 0) {

			message = "ANG3L: There's nothing in your inventory.";
		}
		else{
			//message = "ANG3L: You have the following items in your inventory: ";
			message = "ANG3L: Your active inventory: ";

			int count = 0;

			for (Item item : this.playerInventory.getInventory()) {

				if (item.isActive()) {

					++count;

					if (count == 1) message += item.getName() + " (" + count + ")";
					else message += ", " + item.getName() + " (" + count + ")";

					this.activeInventory.add(item);
				}
			}
		}

		historyItems.add(message);

		historyItems.add("ANG3L: There's: " + Zork.currentRoom.getTotalAliveCharacters() + " enemies left! Your vitals are at: " + this.player.getHealth() + "%!");
	}

	/**
	 * Run this when Player won the combat!
	 */
	public void closeCombatDialogBoxOnWin() {

		this.closeAudio();

		// show alert box
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Combat: WIN!");
		//alert.setHeaderText("Information Alert");
		String s = "Great job, Operator! You're a killing machine.";
		alert.setContentText(s);
		alert.showAndWait();

		// close this window
		this.stage.close();
	}

	/**
	 * Run this when PLayer lost the game
	 */
	public void closeCombatDialogBoxOnLoss() {

		this.closeAudio();

		// show alert box
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Combat: LOSS!");
		//alert.setHeaderText("Information Alert");
		String s = "Operator, your vitals are at 0%! Game over, man! Game over!";
		alert.setContentText(s);
		alert.showAndWait();

		// close this window
		this.stage.close();

		// Platform.exit() CRASHES, so I had to use simple System.exit() in Game Execution
	}

	/**
	 * Play audio file
	 *
	 * @param audioFilePath
	 */
    public void playAudio(String audioFilePath) {

    	if (!Zork.useAudio || audioFilePath == null) return;

    	try {
    		InputStream in = CombatPanel.class.getResourceAsStream(audioFilePath);

    		this.audioStream = new AudioStream(in);

			AudioPlayer.player.start(this.audioStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Close the audio stream
     */
    public void closeAudio() {

    	if (Zork.useAudio && audioStream != null) {
    		AudioPlayer.player.stop(audioStream);
        }
    }

	// ----- all the class members

	private final int TEXT_AREA_WIDTH = 600;
	private final int IMAGE_PANEL_WIDTH = 200;
	private final int WIDTH = TEXT_AREA_WIDTH + IMAGE_PANEL_WIDTH * 2;
	private final int HEIGHT = 500;

	// boolean flag
    boolean combatCompleted = false;

    final private Stage stage = new Stage();

    final private Inventory playerInventory;
    final private List<Item> activeInventory = new ArrayList<Item>();

    final private List<Character> characters;

    // for the audio
    private AudioStream audioStream = null;

    final private Button returnButton = new Button("Return");
    final private TextField textFieldEntry = new TextField();
    final private ListView<String> combatMessageList = new ListView<String>();
    final private ObservableList<String> historyItems = FXCollections.observableArrayList();

	private Room room = null;
	private Player player = null;

} // end CombatPanel