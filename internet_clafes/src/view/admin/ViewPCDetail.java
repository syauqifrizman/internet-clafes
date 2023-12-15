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
	
	private static PC pc;
	
	Stage primaryStage;
	Scene scene;
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}
	
	public static void setScene(Stage primaryStage) {
		ViewPCDetail viewPCDetail = new ViewPCDetail();
		viewPCDetail._setScene(primaryStage);
	}
	
	public static void setScene(Stage primaryStage, PC pc) {
		setScene(primaryStage);
	}
	
	public ViewPCDetail(PC pc) {
		initialize(pc);
		addEventListener();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	Button deleteButton, updateButton;
	TextField pcIDInput, pcConditionInput;
	
	private void initialize(PC pc) {
	    VBox container = new VBox(20);  // Set vertical spacing between elements
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("About The PC");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    
	    // PC ID
	    Label pcIDLabel = new Label("PC ID: ");
	    Label currentPCID = new Label(pc.getPc_ID().toString());
	    currentPCID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCID = new HBox();
	    containerPCID.getChildren().addAll(pcIDLabel, currentPCID);
	    
	    //  PC condition
	    Label pcConditionLabel = new Label("PC Condition: ");
	    Label currentPCCondition = new Label(pc.getPc_condition());
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
//		deleteButton.setOnMouseClicked(e ->{
//			String pc_id = pcIDInput.getText();
//			
//			PCController.addNewPC(pc_id);
//		});
	}
	
    public static ViewPCDetail getInstance(PC fromViewPC) {
    	return new ViewPCDetail(fromViewPC);    	
    }
}
