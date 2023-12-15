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
import model.User;
import view.BookPC;
import view.MenuAdmin;

public class ViewPCDetail {
	
	Stage primaryStage;
	Scene scene;

    public ViewPCDetail(PC pc) {
		initialize(pc);
		addEventListener();
    }
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	Button deleteButton, updateButton;
	Label currentPCID, currentPCCondition;
	
	VBox container = new VBox(20);  // Set vertical spacing between elements
	private void initialize(PC pc) {
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("About The PC");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    
	    // PC ID
	    Label pcIDLabel = new Label("PC ID: ");
	    currentPCID = new Label(pc.getPc_ID().toString());
	    currentPCID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCID = new HBox();
	    containerPCID.getChildren().addAll(pcIDLabel, currentPCID);
	    
	    //  PC condition
	    Label pcConditionLabel = new Label("PC Condition: ");
	    currentPCCondition = new Label(pc.getPc_condition());
	    currentPCCondition.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCCondition = new HBox();
	    containerPCCondition.getChildren().addAll(pcConditionLabel, currentPCCondition);

	    // Add Button
	    deleteButton = new Button("DELETE PC");
	    updateButton = new Button("UPDATE PC");
	    
	    HBox containerButton = new HBox();
	    containerButton.getChildren().addAll(deleteButton, updateButton);

	    // Add elements to the container
	    container.getChildren().addAll(MenuAdmin.createMenu(), pageTitle, containerPCID, containerPCCondition, containerButton);

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	
	private void addEventListener() {
		deleteButton.setOnAction(event -> {
		    try {
		        System.out.println("Delete button clicked");
		        String pc_id = currentPCID.getText();
		        System.out.println("PC ID: " + pc_id);
		        PCController.deletePC(pc_id);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		});


		updateButton.setOnAction(event -> {
		    String pc_id = currentPCID.getText();

		    // pc condition
		    Label conditionTitle = new Label("Update Current PC Condition:");
		    TextField pcConditionInput = new TextField();
		    pcConditionInput.setPromptText("Insert Current PC Condition");

		    container.getChildren().addAll(conditionTitle, pcConditionInput);

		    // Move this line inside the event handler
		    String pc_condition = pcConditionInput.getText();

		    PCController.updatePCCondition(pc_id, pc_condition);
		});

	}

	
    public static ViewPCDetail getInstance(PC fromViewPC) {
    	return new ViewPCDetail(fromViewPC);    	
    }
}
