package view;

import java.sql.SQLException;

import controller.ReportController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PC;
import model.User;
import model.UserSession;

public class ReportPC {
	public static User user;
	public static PC pc;
	
	private Scene scene;
	private VBox vb;
	private Label userID, pcID, reportLabel;
	private TextField reportNote;
	private Button reportButton;
	
	public static ReportPC getInstance(User reportUser, PC reportPc) {
		user = reportUser;
		pc = reportPc;
		
		return new ReportPC();
	}
	
	public ReportPC() {
		reportWindow();
		addEventListener();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	private void reportWindow() {
		vb = new VBox();
		vb.setPadding(new Insets(50, 50, 50, 50));
	    vb.setSpacing(10);
		userID = new Label("Username: " + user.getUserName());
		pcID = new Label("PC ID: " + pc.getPc_ID());
		reportLabel = new Label("What's wrong with the PC?:");
		reportNote = new TextField();
		reportNote.setPromptText("Problems with the PC");
		reportButton = new Button("Report");
		
		vb.getChildren().addAll(userID, pcID, reportLabel, reportNote, reportButton);
		
		scene = new Scene(vb, 800, 600);
		
	}
	
	private void addEventListener() {
		reportButton.setOnMouseClicked(e -> {
			String reportnote = reportNote.getText();
			
			ReportController.addNewReport(user.getUserRole(), pc.getPc_ID().toString(), reportnote, null);
			ViewPC viewpc = ViewPC.getInstance();
			viewpc.show();
			
		});
	}

}
