package view.admin;

import controller.PCController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MainStage;
import view.menu.MenuAdmin;

public class AddPC {
	
	Stage primaryStage;
	Scene scene;

    public AddPC() {
        initialize();
        addEventListener();
    }
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	Button addButton, backButton;
	TextField pcIDInput, pcConditionInput;
	
	//method untuk membuat item2 (button, label, dll)
	private void initialize() {
	    VBox container = new VBox(20);  // Set vertical spacing between elements
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("ADD NEW PC");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

	    // PC ID Input
	    pcIDInput = new TextField();
	    pcIDInput.setPromptText("Insert new PC ID [must be unique]");
	    Label pcTitle = new Label("Insert New PC ID [must be unique]:");

	    // PC Condition Status
	    Label pcConditionTitle = new Label("PC Condition Status (default will be): ");
	    Label pcConditionStatus = new Label("Usable");
	    pcConditionStatus.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    HBox containerPcCondition = new HBox(10);  // Set horizontal spacing
	    containerPcCondition.getChildren().addAll(pcConditionTitle, pcConditionStatus);

	    // Add Button
	    addButton = new Button("ADD NEW PC");

	    // Add elements to the container
	    container.getChildren().addAll(MenuAdmin.createMenu(), pageTitle, containerPcCondition, pcTitle, pcIDInput, addButton);

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	//method fungsionalitas dari button
	private void addEventListener() {
		//jika button Add PC diklik, PC baru akan dibuat di database dengan memanggil PC Controller
		addButton.setOnMouseClicked(e ->{
			String pc_id = pcIDInput.getText();
			
			PCController.addNewPC(pc_id);
		});
	}
	
    public static AddPC getInstance() {
    	return new AddPC();
    }
}
