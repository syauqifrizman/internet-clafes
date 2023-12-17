package view;

import controller.ReportController;
import controller.UserSessionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PC;
import model.User;
import view.menu.HeaderLayout;
import view.menu.MenuCustomer;
import view.menu.MenuOperator;

public class ReportPC extends HeaderLayout{
	public static User user;
	public static PC pc;
	
	//deklarasi item yg akan dipakai
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
	
	//method memunculkan screen untuk penambahan report
	private void reportWindow() {
		VBox containerHeader = getUserHeader();
		
		vb = new VBox();
		vb.setPadding(new Insets(50, 50, 50, 50));
	    vb.setSpacing(10);
		
		//memunculkan PC ID yang ingin di-report
		pcID = new Label("PC ID: " + pc.getPc_ID());
		
		//input untuk report note
		reportLabel = new Label("What's wrong with the PC?:");
		reportNote = new TextField();
		reportNote.setPromptText("Problems with the PC");
		
		//tombol Report
		reportButton = new Button("Report");
		
		if(user.getUserRole().equals("Customer")) {
			vb.getChildren().add(MenuCustomer.createMenu());
		}
		else if(user.getUserRole().equals("Operator")) {
			vb.getChildren().add(MenuOperator.createMenu(UserSessionController.getCurrentUser()));
		}
		
		vb.getChildren().addAll(containerHeader, pcID, reportLabel, reportNote, reportButton);
		
		scene = new Scene(vb, 800, 600);
		
	}
	
	//method untuk fungsionalitas saat memencet tombol Report
	private void addEventListener() {
		reportButton.setOnMouseClicked(e -> {
			String reportnote = reportNote.getText();
			
			//saat di-click, report baru akan dibuat dan user akan diarahkan ke laman View All PC
			ReportController.addNewReport(user.getUserRole(), pc.getPc_ID().toString(), reportnote);
			ViewPC viewpc = ViewPC.getInstance();
			viewpc.show();
			
		});
	}

}
