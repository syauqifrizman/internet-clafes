package view.admin;

import controller.JobController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PC;
import view.ViewPC;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;

public class AddJob extends HeaderLayout{
	
	private static PC pc;
	
	private Scene scene;
	private VBox vb;
	private Label pcID, pcCondition, staffLabel, pcLabel;
	private TextField staffID, pcInput;
	private Button fixButton;

	public static AddJob getInstance(PC fixPC) {
		pc = fixPC;
		
		return new AddJob();
	}
	
	public AddJob() {
		addJob();
		addEventListener();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	//method utk memunculkan isi window
	private void addJob() {
		VBox containerHeader = getUserHeader();
		
		vb = new VBox();
		vb.setPadding(new Insets(0, 20, 20, 20));  // Set padding
		
		//instansiasi label, textfield, dan button
		pcID = new Label("PC ID: " + pc.getPc_ID());
		pcCondition = new Label("PC Condition: " + pc.getPc_condition());
		
		staffLabel = new Label("Assign this job to (Staff ID): ");
		staffID = new TextField();
		staffID.setPromptText("Insert Staff ID");
		
		//pembuatan input PC ID cadangan jika PC yang dibuatkan job sedang di-booking
		pcLabel = new Label("New PC ID (Customer will be assigned to this PC if it has been booked): ");
		pcInput = new TextField();
		pcInput.setPromptText("PC ID");
		
		fixButton = new Button("Add Job");
		
		vb.getChildren().addAll(MenuAdmin.createMenu(), containerHeader, pcID, pcCondition, staffLabel, staffID, pcLabel, pcInput, fixButton);
		
		scene = new Scene(vb, 800, 600);
	}
	
	//method untuk fungsionalitas button
	private void addEventListener() {
		fixButton.setOnMouseClicked(e -> {
			String brokenPC = pc.getPc_ID().toString();
			String staff = staffID.getText();
			String newPC = pcInput.getText();
			
			//kalau fix Button diklik, job baru akan segera dibuat
			JobController.addNewJob(staff, brokenPC, newPC);
			ViewPC viewpc = ViewPC.getInstance();
			viewpc.show();
			
		});
	}

}
