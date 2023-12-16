package view.customer;

import java.sql.Date;
import java.sql.SQLException;

import controller.PcBookController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PC;
import model.User;
import view.MenuCustomer;
import view.ViewPC;

public class BookPC {
	public static User user;
	public static PC pc;
	
	private Scene scene;
	private VBox vb;
	private Label userID, pcID, dateLabel;
	private DatePicker dateP;
	private Button bookButton;
	
	public static BookPC getInstance(User reportUser, PC reportPc) {
		user = reportUser;
		pc = reportPc;
		
		return new BookPC();
	}
	
	public BookPC() {
		bookWindow();
		addEventListener();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	//memunculkan isi dari page
	private void bookWindow() {
		vb = new VBox();
		vb.setPadding(new Insets(50, 50, 50, 50));
	    vb.setSpacing(10);
	    
	    //Username
		userID = new Label("Username: " + user.getUserName());
		
		//PC ID
		pcID = new Label("PC ID: " + pc.getPc_ID());
		
		//Pemilihan tanggal
		dateLabel = new Label("Pick Date:");
		dateP = new DatePicker();
		
		//tombol Book
		bookButton = new Button("Book");
		
		vb.getChildren().addAll(MenuCustomer.createMenu(), userID, pcID, dateLabel, dateP, bookButton);
		
		scene = new Scene(vb, 800, 600);
	}
	
	private void addEventListener() {
		
		//fungsionalitas tombol Book
		bookButton.setOnMouseClicked(e -> {
			Date bookedDate = java.sql.Date.valueOf(dateP.getValue()); //mengambil value dari date picker
			
			//menambah booking baru dengan memanggil controller
			try {
				Boolean addStatus = PcBookController.addNewBook(pc.getPc_ID().toString(), user.getUserID(), bookedDate);
				if(addStatus) {
					Helper.showAlert(AlertType.INFORMATION, "Success Add Book");
					ViewPC viewpc = ViewPC.getInstance();
					viewpc.show();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
	}

}
