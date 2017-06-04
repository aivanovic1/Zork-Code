/**
 *
 */
package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Inventory DialogBox (panel)
 *
 * @author Andrei Ivanovic
 *
 */
public class InventoryTableView {

	/**
	 * Constructor
	 */
	public InventoryTableView() {
	}

	/**
	 * Display the panel
	 */
    public void display() {

    	// load data to observable list
    	this.loadItems();

    	this.inventoryPane = new BorderPane();
		this.inventoryScene = new Scene(this.inventoryPane);

		HBox topHbox = new HBox();
		topHbox.setPrefSize(PANEL_WIDTH, 40);
		topHbox.setPadding(new Insets(5, 5, 5, 5));
		topHbox.setSpacing(10);   // Gap between nodes

		HBox bottomHbox = new HBox();

		StackPane bottomLaftPane = new StackPane();
		bottomLaftPane.setPrefSize(PANEL_WIDTH/2, 40);
		bottomLaftPane.setPadding(new Insets(10, 10, 10, 10));

		Label label = new Label();
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		label.setText("Total Weight: " + this.totalInventoryWeight);
		StackPane.setAlignment(label, Pos.CENTER_LEFT);
		bottomLaftPane.getChildren().addAll(label);

		StackPane bottomRightPane = new StackPane();
		bottomRightPane.setPrefSize(PANEL_WIDTH/2, 40);
		bottomRightPane.setPadding(new Insets(10, 10, 10, 10));

		this.okButton = new Button("OK");
		this.okButton.setPrefSize(100, 30);
		StackPane.setAlignment(this.okButton, Pos.CENTER_RIGHT);
		bottomRightPane.getChildren().addAll(this.okButton);

		bottomHbox.getChildren().addAll(bottomLaftPane, bottomRightPane);

    	this.okButton.setOnAction(e -> this.handleClose(e));

    	// create a table view UI control
    	this.tblView = new TableView<ExtendedItem>();

    	TableColumn<ExtendedItem, String> nameCol = new TableColumn<>("Item Name");
    	nameCol.setMinWidth(100);
    	nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    	TableColumn<ExtendedItem,String> typeCol = new TableColumn<>("Type");
    	typeCol.setMinWidth(80);
    	typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    	TableColumn<ExtendedItem,Integer> weightCol = new TableColumn<>("Weight");
    	weightCol.setMinWidth(30);
    	weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

    	TableColumn<ExtendedItem,String> abilityCol = new TableColumn<>("Ability");
    	abilityCol.setMinWidth(70);
    	abilityCol.setCellValueFactory(new PropertyValueFactory<>("ability"));

    	TableColumn<ExtendedItem,Integer> ammoCol = new TableColumn<>("Ammo");
    	ammoCol.setMinWidth(30);
    	ammoCol.setCellValueFactory(new PropertyValueFactory<>("ammo"));

    	TableColumn<ExtendedItem,Integer> ammoUsageCol = new TableColumn<>("Ammo Usage");
    	ammoUsageCol.setMinWidth(30);
    	ammoUsageCol.setCellValueFactory(new PropertyValueFactory<>("ammoUsage"));

    	TableColumn<ExtendedItem,Integer> powerCol = new TableColumn<>("Power");
    	powerCol.setMinWidth(30);
    	powerCol.setCellValueFactory(new PropertyValueFactory<>("power"));

    	TableColumn<ExtendedItem,Integer> accuracyCol = new TableColumn<>("Accuracy");
    	accuracyCol.setMinWidth(30);
    	accuracyCol.setCellValueFactory(new PropertyValueFactory<>("accuracy"));

    	TableColumn<ExtendedItem,Integer> qtyCol = new TableColumn<>("Quantity");
    	qtyCol.setMinWidth(30);
    	qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

    	TableColumn<ExtendedItem,Integer> healCol = new TableColumn<>("Heal Power");
    	healCol.setMinWidth(30);
    	healCol.setCellValueFactory(new PropertyValueFactory<>("heal"));

    	TableColumn<ExtendedItem,Integer> redCol = new TableColumn<>("Reduction");
    	redCol.setMinWidth(30);
    	redCol.setCellValueFactory(new PropertyValueFactory<>("reduction"));

    	TableColumn<ExtendedItem,Integer> durabilityCol = new TableColumn<>("Durability");
    	durabilityCol.setMinWidth(30);
    	durabilityCol.setCellValueFactory(new PropertyValueFactory<>("durability"));

    	this.tblView.setItems(this.extItemsData);
    	this.tblView.getColumns().addAll(nameCol, typeCol, weightCol, abilityCol, ammoCol, ammoUsageCol, powerCol, accuracyCol, qtyCol, healCol, redCol, durabilityCol);

	    this.inventoryPane.setTop(topHbox);
	    this.inventoryPane.setCenter(this.tblView);
	    this.inventoryPane.setBottom(bottomHbox);

    	this.inventoryStage = new Stage();
    	this.inventoryStage.setTitle("Player's Inventory");
    	this.inventoryStage.setWidth(PANEL_WIDTH);
    	this.inventoryStage.setHeight(PANEL_HEIGHT);
    	this.inventoryStage.initModality(Modality.APPLICATION_MODAL);

    	this.inventoryStage.setResizable(false);
    	//this.inventoryStage.initStyle(StageStyle.UNDECORATED);

    	this.inventoryStage.setScene(this.inventoryScene);
    	this.inventoryStage.show();
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Zork.commandEntryTextField.requestFocus();
            }
        });
      }

    /**
     * Load the items to the table view
     */
    private void loadItems() {
    	for(Item item : Zork.player.getPlayerInventory().getInventory()) {

    		this.totalInventoryWeight += item.getWeight();

    		ExtendedItem extItem = item.convertToExtendedItem();
    		this.extItemsData.add(extItem);
    	}
    }

	private Button okButton;

	private TableView<ExtendedItem> tblView;

	private int totalInventoryWeight = 0;
	final protected ObservableList<ExtendedItem> extItemsData = FXCollections.observableArrayList();

	private Stage inventoryStage = null;
	private BorderPane inventoryPane = null;
	private Scene inventoryScene = null;

	private final int PANEL_WIDTH = 900;
	private final int PANEL_HEIGHT = 440;

} // end InventoryTableView