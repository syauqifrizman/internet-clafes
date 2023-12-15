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
import model.PC;
import view.MenuAdmin;
import view.ViewPC;

public class AddPC {
	
	Stage primaryStage;
	Scene scene;
	
//	private void setScene() {
//		primaryStage.setScene(scene);
//	}

    public AddPC() {
//        this.primaryStage = primaryStage;
        initialize();
        addEventListener();
//        setScene();
    }
    
//	
//	public static void setScene(Stage primaryStage) {
//		AddPC addNewPC = new AddPC();
//		addNewPC._setScene(primaryStage);
//	}
//	
//	public static void setScene(Stage primaryStage, PC pc) {
//		setScene(primaryStage);
//	}
//	
//	public AddPC() {
//		initialize();
//		addEventListener();
//	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	Button addButton, backButton;
	TextField pcIDInput, pcConditionInput;
	
	private void initialize() {
	    VBox container = new VBox(20);  // Set vertical spacing between elements
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("ADD NEW PC");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

	    // PC ID Input
	    Label pcTitle = new Label("Insert New PC_ID");
	    pcIDInput = new TextField();
	    pcIDInput.setPromptText("Insert new PC ID [must be unique]");

	    // PC Condition Status
	    Label pcConditionTitle = new Label("PC Condition Status (default): ");
	    Label pcConditionStatus = new Label("Usable");
	    pcConditionStatus.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    HBox containerPcCondition = new HBox(10);  // Set horizontal spacing
	    containerPcCondition.getChildren().addAll(pcConditionTitle, pcConditionStatus);

	    // Add Button
	    addButton = new Button("ADD NEW PC");

	    // Add elements to the container
	    container.getChildren().addAll(MenuAdmin.createMenu(), pageTitle, pcTitle, containerPcCondition, pcIDInput, addButton);

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	
	private void addEventListener() {
		addButton.setOnMouseClicked(e ->{
			String pc_id = pcIDInput.getText();
			
			PCController.addNewPC(pc_id);
			
	    	ViewPC viewPCPage = new ViewPC();
	    	viewPCPage.show();
		});
	}
	
    public static AddPC getInstance() {
        // Implement logic to navigate to the "Book PC" page
    	return new AddPC();
    }
}
