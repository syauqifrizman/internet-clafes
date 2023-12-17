package view.admin;

import controller.PCController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MainStage;
import model.PC;
import controller.UserSessionController;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;
import view.menu.MenuComputerTechnician;
import view.menu.MenuCustomer;
import view.menu.MenuOperator;


public class ViewPCDetail extends HeaderLayout{
	
	Stage primaryStage;
	Scene scene;

    public ViewPCDetail(PC pc) {
		initialize(pc);
		addEventListener(pc);
    }
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	TextField pcConditionInput;
	Button deleteButton, updateButton;
	Label currentPCID, currentPCCondition;
	
	VBox container = new VBox(20);  // Set vertical spacing between elements
	private void initialize(PC pc) {
		VBox containerHeader = getUserHeader();
		
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
	    
	    // pc condition
	    Label conditionTitle = new Label("Update Current PC Condition to ['Usable', 'Maintenance', or 'Broken']:");
	    pcConditionInput = new TextField();
	    pcConditionInput.setPromptText("Insert Current PC Condition");

	    // Add elements to the container
	    switch(UserSessionController.getCurrentUserRole()) {
	    //kalau role customer, maka Menu Bar customer akan dimunculkan, dst
		case "Customer":
			container.getChildren().add(MenuCustomer.createMenu());
			break;
		case "Computer Technician":
			container.getChildren().add(MenuComputerTechnician.createMenu());
			break;
		case "Operator":
			container.getChildren().add(MenuOperator.createMenu(UserSessionController.getCurrentUser()));
			break;
		case "Admin":
			container.getChildren().add(MenuAdmin.createMenu());
			break;
		default:
			Helper.showAlert(AlertType.ERROR, "Invalid role");
	}
	    
	    //memasukkan label, textfield dll ke Container
	    container.getChildren().addAll(containerHeader, pageTitle, containerPCID, containerPCCondition, conditionTitle, pcConditionInput);
	    
	    //jika role user adalah admin, maka tombol UPDATE PC dan DELETE PC akan dimunculkan
	    if(UserSessionController.getCurrentUser().getUserRole().equals("Admin")) {
	    	container.getChildren().add(containerButton);
	    }

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	
	private void addEventListener(PC pc) {
		
		//jika tombol Delete PC diklik, maka PC tersebut akan dihapus
		deleteButton.setOnAction(event -> {
	        String pc_id = pc.getPc_ID().toString();
	        
	        PCController.deletePC(pc_id);
		});

		//jika tombol Update PC diklik, maka PC Status akan diganti sesuai dengan apa yang sudah di-input
		updateButton.setOnAction(event -> {
		    String pc_id = pc.getPc_ID().toString();

		    String pc_condition = pcConditionInput.getText();

		    PCController.updatePCCondition(pc_id, pc_condition);
			PC getNewUpdatePC = PCController.getPCDetail(pc_id);
		    ViewPCDetail ViewPCDetail = new ViewPCDetail(getNewUpdatePC);
		    ViewPCDetail.show();
		});

	}

	
    public static ViewPCDetail getInstance(PC fromViewPC) {
    	return new ViewPCDetail(fromViewPC);    	
    }
}
