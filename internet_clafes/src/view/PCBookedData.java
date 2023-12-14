package view;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.util.Callback;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PCBook;

public class PCBookedData implements IView{
	
	private static PCBookedData pcBookedData;
	private static IView iview;
	private Scene scene;
	private TableView<PCBook> tv;
	private DatePicker datePicker;
	
	public static PCBookedData getInstance() {
		return new PCBookedData();
	}
	
	private PCBookedData() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		try {
			repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initTable() {
		VBox cont = new VBox();
		tv = new TableView<PCBook>();
		datePicker = new DatePicker();
		
		TableColumn<PCBook, Integer> id = new TableColumn<>("Booking ID");
		id.setCellValueFactory(new PropertyValueFactory<>("bookID"));
		
		TableColumn<PCBook, Integer> idPC = new TableColumn<>("PC ID");
		idPC.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<PCBook, Integer> idUser = new TableColumn<>("User ID");
		idUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		TableColumn<PCBook, String> date = new TableColumn<>("booking Date");
		date.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));
		
		TableColumn<PCBook, Button> deleteColumn = new TableColumn<>("Delete");
	    deleteColumn.setCellFactory(tc -> new ButtonCell());
	    deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
		
		tv.getColumns().addAll(id, idPC, idUser, date);
		
		datePicker.setOnAction(event -> {
	        try {
	            repaint();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    });
		HBox searchBox = new HBox(datePicker);
        cont.getChildren().addAll(MenuOperator.createMenu(), searchBox, tv);
		
		scene = new Scene(cont, 800, 600);
    }
	
	private void repaint() throws SQLException {
		tv.getItems().clear();
		ArrayList<PCBook> pcBook = PCBook.getAllPCBookedData();
		
		if (datePicker.getValue() != null) {
			java.sql.Date sqlDate = java.sql.Date.valueOf(datePicker.getValue());
	        pcBook = PCBook.getPCBookedByDate(sqlDate);
        } else {
            pcBook = PCBook.getAllPCBookedData();
        }
		
		for (PCBook pcBook2 : pcBook) {
			tv.getItems().add(pcBook2);
		}
		
	}


	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}
	
}
