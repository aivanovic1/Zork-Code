/**
 *
 */
package application;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * @author Andrei Ivanovic
 *
 */
public class CombatPanel {

	public CombatPanel(Room r, Player player) {
		this.room = r;
		this.player = player;

		this.playerInventory = this.player.getPlayerInventory();
		this.characters = this.room.getCharacters();
	}

	public void start() {

		// VBox for the entire dialog
    	VBox vbox = new VBox();
		Scene combatScene = new Scene(vbox);

		// StackPane as the second node in VBox
		StackPane topPane = new StackPane();
		topPane.setPrefSize(700, 40);
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

		combatMessageList.setPrefWidth(700);
		combatMessageList.setPrefHeight(400);
		combatMessageList.setItems(historyItems);

		Zork.combatHistory = historyItems;

		// get the inventory be loaded for display
		this.printCombatStatus();

		textFieldEntry.setPromptText("Enter the 'use' command for the combat ...");
		textFieldEntry.setPrefSize(700, 30);

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
    	this.stage.setWidth(700);
    	this.stage.setHeight(500);
    	this.stage.setResizable(false);
    	this.stage.initModality(Modality.APPLICATION_MODAL);
    	//stage.initStyle(StageStyle.UNDECORATED);

    	this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!combatCompleted) event.consume();
            }
        });

    	this.stage.setScene(combatScene);
    	this.stage.showAndWait();
	}

	/**
	 * This is the main method for combat logic
	 * @param useCommandParam
	 */
	private void combat(String useCommandParam) {

		//TODO remove after testing
		if (useCommandParam.equalsIgnoreCase("x")) {
			combatCompleted = true;
			returnButton.setVisible(true);
		}

		int inventoryIndex = 0;

		try {
			inventoryIndex = Integer.parseInt(useCommandParam);

			if (inventoryIndex < 1 || inventoryIndex > this.playerInventory.getTotalActiveInventorySize()) {
				historyItems.add("Item # needs to be between 1 and " + this.playerInventory.getTotalActiveInventorySize() + "!");
			}
			else {

				Item item = this.playerInventory.getItem(inventoryIndex);

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

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}

					// close this window
					this.stage.close();
				}
				else {
					enemy.takeDamage(damage);
					if (enemy.getHealth() == 0) this.characters.remove(0);
					else {
						if (enemy.getTurnsToSkip() == 0) opponentCombat();
						else enemy.decrementTurnsToSkip();
					}
				}

				if (!player.isAlive()) {
					historyItems.add("ANG3L: Operator! Your vitals are at 0! Game over, man! Game over!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}

					// EXIT THE GAME!
					Platform.exit();
				}
			}

		} catch (NumberFormatException e) {

			historyItems.add("You have to specify # of the item in your inventory, starting from 1!");
		}

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	textFieldEntry.requestFocus();
            }
        });
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
		else Zork.combatHistory.add("ANG3L: They missed!");
	}

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

				historyItems.add("Command entered: " + command.getOriginalEnteredCommand());

				// invoke the combat logic with the parameter passed to USE command
				combat(command.getParam(1));
			}

			textFieldEntry.clear();
			textFieldEntry.setPromptText("Enter the 'use' command for the combat ...");

			this.printCombatStatus();

        }
	}

    // close inventory dialog box
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

	private void printCombatStatus() {
		String message = null;

		if (this.player.getPlayerInventory().getInventory() == null || this.player.getPlayerInventory().getInventory().size() == 0) {

			message = "ANG3L: There's nothing in your inventory.";
		}
		else{
			//message = "ANG3L: You have the following items in your inventory: ";
			message = "ANG3L: Your active inventory: ";

			for (int i = 0; i < this.player.getPlayerInventory().getInventory().size(); i++) {

				if (this.playerInventory.getInventory().get(i).isActive()) {
					if (i == 0) message += this.playerInventory.getInventory().get(i).getName() + " (" + (i+1) + ")";
					else message += ", " + this.playerInventory.getInventory().get(i).getName() + " (" + (i+1) + ")";
				}
			}
		}

		historyItems.add(message);

		historyItems.add("ANG3L: There's: " + Zork.currentRoom.getTotalAliveCharacters() + " enemies left! Your vitals are at: " + this.player.getHealth() + "%!");
	}

	// -- all the class members

    boolean combatCompleted = false;

    final private Stage stage = new Stage();

    final private Inventory playerInventory;
    final private List<Character> characters;

    final private Button returnButton = new Button("Return");
    final private TextField textFieldEntry = new TextField();
    final private ListView<String> combatMessageList = new ListView<String>();
    final private ObservableList<String> historyItems = FXCollections.observableArrayList();

	private Room room = null;
	private Player player = null;

} // end CombatPanel