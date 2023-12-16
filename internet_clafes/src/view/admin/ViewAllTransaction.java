package view.admin;

import java.util.ArrayList;

import controller.TransactionController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import main.MainStage;
import model.TransactionHeader;
import view.MenuAdmin;

public class ViewAllTransaction {
	private Scene scene;
	private VBox cont;
	private Label thistoryLabel;
	
	private TableView<TransactionHeader> tv;
	
	public static ViewAllTransaction getInstance() {
		return new ViewAllTransaction();
	}
	
	public ViewAllTransaction() {
		initTable();
		repaint();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	//method untuk memunculkan tabel dll
	private void initTable() {
		cont = new VBox();
		thistoryLabel = new Label("Transaction History");
		
		tv = new TableView<TransactionHeader>();//membuat tabel berdasarkan model TransactionHeader
		
		//generate kolom2nya
		TableColumn<TransactionHeader, Integer> id = new TableColumn<>("Transaction ID");
		id.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

		TableColumn<TransactionHeader, Integer> staff = new TableColumn<>("Staff ID");
		staff.setCellValueFactory(new PropertyValueFactory<>("staffID"));
		
		TableColumn<TransactionHeader, String> name = new TableColumn<>("Staff Name");
		name.setCellValueFactory(new PropertyValueFactory<>("staffName"));
		
		TableColumn<TransactionHeader, String> date = new TableColumn<>("Finished Date");
		date.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		
		//membuat kolom dengan isi button
		TableColumn<TransactionHeader, Void> detail = new TableColumn<>("Details");
		Callback<TableColumn<TransactionHeader, Void>, TableCell<TransactionHeader, Void>> 
		cellFactory = new Callback<TableColumn<TransactionHeader, Void>, TableCell<TransactionHeader, Void>>() {
		    @Override
		    public TableCell<TransactionHeader, Void> call(final TableColumn<TransactionHeader, Void> param) {
		        final TableCell<TransactionHeader, Void> cell = new TableCell<TransactionHeader, Void>() {
		        	
		        	//deklarasi button nya
		            private final Button detailButton = new Button("Details");

		            
		            {
		            	//jika Detail button diklik, user akan diarahkan ke page View Transaction Detail
		            	//yang berisi Transaction Detail dengan Transaction ID yang sama dengan yang sudah tadi dipilih
		                detailButton.setOnAction((ActionEvent event) -> {
		                	//mengambil data dari row
	                        TransactionHeader theader = getTableView().getItems().get(getIndex());
	                        
	                        //mengalihkan user ke page View Transaction Detail
	                        ViewTransactionDetail tdetail = ViewTransactionDetail.getInstance(theader.getTransactionID());
	                        tdetail.show();
		                });
		            }
		            	
		            //memunculkan button ke tabel
			            @Override
			            public void updateItem(Void item, boolean empty) {
			                super.updateItem(item, empty);
			                if (empty) {
			                    setGraphic(null);
			                } else {
			                    HBox containerButtons = new HBox();
			                    
			                    containerButtons.getChildren().add(detailButton);
			                    setGraphic(containerButtons);
			                }
			            }
		           };
		        return cell;
		    }
		};
		detail.setCellFactory(cellFactory);
		
		thistoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		//memasukkan kolom ke tabel
		tv.getColumns().add(id);
		tv.getColumns().add(staff);
		tv.getColumns().add(name);
		tv.getColumns().add(date);
		tv.getColumns().add(detail);
		
		cont.getChildren().addAll(MenuAdmin.createMenu(), thistoryLabel, tv);
		scene = new Scene(cont, 800, 600);
		
	}
	
	//method untuk mengisi data ke tabel
	private void repaint() {
		tv.getItems().clear();
		ArrayList<TransactionHeader> theader = TransactionController.getAllTransactionHeaderData();
		for (TransactionHeader transactionHeader : theader) {
			tv.getItems().add(transactionHeader);
		}
	}
}
