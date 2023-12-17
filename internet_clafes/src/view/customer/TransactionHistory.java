package view.customer;

import java.util.ArrayList;

import controller.TransactionController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.TransactionDetail;
import controller.UserSessionController;
import view.menu.MenuCustomer;

public class TransactionHistory {
	
	private Scene scene;
	private VBox cont;
	private Label thistoryLabel;
	
	private TableView<TransactionDetail> tv;
	
	public static TransactionHistory getInstance() {
		return new TransactionHistory();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	public TransactionHistory() {
		initTable();
		repaint();
	}
	
	private void initTable() {
		cont = new VBox();
		thistoryLabel = new Label("Your Transaction History");
		tv = new TableView<TransactionDetail>();//membuat tabel berdasarkan model TransactionDetail
		
		//generate kolom2nya
		TableColumn<TransactionDetail, Integer> id = new TableColumn<>("Transaction ID");
		id.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

		TableColumn<TransactionDetail, Integer> pc = new TableColumn<>("PC ID");
		pc.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<TransactionDetail, String> name = new TableColumn<>("Customer Name");
		name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		
		TableColumn<TransactionDetail, String> date = new TableColumn<>("Booked Date");
		date.setCellValueFactory(new PropertyValueFactory<>("bookedTime"));
		
		//memasukkan kolom ke tabel
		tv.getColumns().add(id);
		tv.getColumns().add(pc);
		tv.getColumns().add(name);
		tv.getColumns().add(date);
		
		thistoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		//memunculkan tabel, label, dan menu bar
		cont.getChildren().add(MenuCustomer.createMenu());
		cont.getChildren().add(thistoryLabel);
		cont.getChildren().add(tv);
		
		scene = new Scene(cont, 800, 600);
	}
	
	//method untuk mengisi data ke tabel
	private void repaint() {
		tv.getItems().clear();
		
		//mengambil data dari database ke sebuah arraylist
		ArrayList<TransactionDetail> tdetails = TransactionController.getUserTransactionDetail(UserSessionController.getCurrentUser().getUserID());
		
		//masukkan ke table
		for (TransactionDetail transactionDetail : tdetails) {
			tv.getItems().add(transactionDetail);
		}
	}
}
