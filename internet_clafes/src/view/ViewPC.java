package view;

import java.util.ArrayList;

import helper.Helper;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
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
import model.User;
import model.PC;
import controller.UserSessionController;
import repository.PCRepository;
import view.admin.AddJob;
import view.admin.AddPC;
import view.admin.ViewPCDetail;
import view.customer.BookPC;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;
import view.menu.MenuComputerTechnician;
import view.menu.MenuCustomer;
import view.menu.MenuOperator;

public class ViewPC extends HeaderLayout{
	private Scene scene;
	private TableView<PC> tv;
	
	public static ViewPC getInstance() {
		return new ViewPC();
	}
	
	public ViewPC() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	private void initTable() {
		VBox containerHeader = getUserHeader();
		
		VBox cont = new VBox();
		
		//membuat table data baru
		tv = new TableView<PC>();
		
		//mengisi kolom2 table data nya
		TableColumn<PC, Integer> id = new TableColumn<>("PC ID");
		id.setPrefWidth(50);
		id.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<PC, Integer> cond = new TableColumn<>("PC Condition");
		cond.setCellValueFactory(new PropertyValueFactory<>("pc_condition"));
		
		TableColumn<PC, Void> action = new TableColumn<>("Actions");
		action.setPrefWidth(270); //mengatur width dari column
		
		//membuat column dengan button
		Callback<TableColumn<PC, Void>, TableCell<PC, Void>> cellFactory = new Callback<TableColumn<PC, Void>, TableCell<PC, Void>>() {
		    @Override
		    public TableCell<PC, Void> call(final TableColumn<PC, Void> param) {
		        final TableCell<PC, Void> cell = new TableCell<PC, Void>() {
		        	
		        	//deklarasi button yang akan digunakan
		            private final Button reportButton = new Button("Report");
		            private final Button bookButton = new Button("Book");
		            private final Button detailButton = new Button("Detail");
		            private final Button addJobButton = new Button("Fix (Add Job)");

		            {
		                
		                //jika role user adalah Customer atau Operator, maka akan memunculkan Report Button
		                if (UserSessionController.getCurrentUserRole().equals("Customer") || UserSessionController.getCurrentUserRole().equals("Operator")) {
		                    reportButton.setOnAction((ActionEvent event) -> {
		                    	//mendapatkan user sekarang
		                        User getUser = UserSessionController.getCurrentUser();
		                        
		                        //mendapatkan data dari row 
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        
		                        //memunculkan laman Report PC dengan User dan PC sebagai parameter
		                        ReportPC reportpc = ReportPC.getInstance(getUser, getPC);
		                        reportpc.show();
		                    });
		                }
		                
		                //jika role user adalah Customer, maka munculkan tombol Book
		                if (UserSessionController.getCurrentUserRole().equals("Customer")) {
		                    bookButton.setOnAction((ActionEvent event) -> {
		                    	//mendapatkan user sekarang
		                        User getUser = UserSessionController.getCurrentUser();
		                        
		                        //mendapatkan data dari row 
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        
		                      //memunculkan laman Book PC dengan User dan PC sebagai parameter
		                        BookPC bookpc = BookPC.getInstance(getUser, getPC);
		                        bookpc.show();
		                    });
		                }
		                
		                //jika role user adalah Admin, maka akan memunculkan tombol Add Job dan Detail
		                if (UserSessionController.getCurrentUserRole().equals("Admin")) {
		                    addJobButton.setOnAction((ActionEvent event) -> {
		                        
		                        //mendapatkan data dari row 
		                        PC getPC = getTableView().getItems().get(getIndex());          
                        
		                        //user dialihkan ke laman View All Staff Job
		                        AddJob addjob = AddJob.getInstance(getPC);
		                        addjob.show();
		                    });
		                    
		                    detailButton.setOnAction((ActionEvent event) -> {
		                    	//mendapatkan data dari row 
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        
		                        //user dialihkan ke laman View PC Detail
			                    ViewPCDetail viewPCDetail = ViewPCDetail.getInstance(getPC);
			                    viewPCDetail.show();
			                });
		                }
		            }

		            @Override
		            public void updateItem(Void item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                    HBox containerButtons = new HBox();
		                    
		                    //jika role user adalah customer, maka munculka report button dan book button
		                    if (UserSessionController.getCurrentUserRole().equals("Customer")) {
		                    	containerButtons.getChildren().addAll(reportButton, bookButton);
		                    }
		                    
		                    //jika role user adalah Operator, munculkan Report button
		                    else if(UserSessionController.getCurrentUserRole().equals("Operator")) {
		                    	containerButtons.getChildren().add(reportButton);
		                    }
		                    
		                    //jika role user adalah Admin, munculkan Add Job dan PC Detail button
		                    else if(UserSessionController.getCurrentUserRole().equals("Admin")) {
		                    	containerButtons.getChildren().add(addJobButton);
		                    	containerButtons.getChildren().add(detailButton);
		                    }
		                    
		                    setGraphic(containerButtons);
		                }
		            }
		        };
		        return cell;
		    }
		};
		action.setCellFactory(cellFactory);
	    
		//masukkan column ke table
		tv.getColumns().add(id);
		tv.getColumns().add(cond);
		tv.getColumns().add(action);
		
		//buat tombol untuk Add PC
		Button addPCButton = new Button("ADD NEW PC");
		VBox containerHeadButton = new VBox();
		containerHeadButton.getChildren().add(addPCButton);
		
		containerHeadButton.setAlignment(Pos.TOP_RIGHT);
		
		//fungsionalitas tombol Add PC
		addPCButton.setOnMouseClicked(e ->{
			//jika diklik, maka user akan dialihkan ke laman Add PC
			AddPC.getInstance().show();
		});
		
		switch(UserSessionController.getCurrentUserRole()) {
			
			//jika role user adalah Customer, Menu Bar Customer akan dimunculkan
			case "Customer":
				cont.getChildren().addAll(MenuCustomer.createMenu(), containerHeader, tv);
				break;
				
			//jika role user adalah Computer Technician, Menu Bar Computer Technician akan dimunculkan
			case "Computer Technician":
				cont.getChildren().addAll(MenuComputerTechnician.createMenu(), containerHeader, tv);
				break;
				
			//jika role user adalah Operator, Menu Bar Operator akan dimunculkan
			case "Operator":
				cont.getChildren().addAll(MenuOperator.createMenu(UserSessionController.getCurrentUser()), containerHeader, tv);
				break;
				
			//jika role user adalah Admin, Menu Bar Admin akan dimunculkan
			case "Admin":
				cont.getChildren().addAll(MenuAdmin.createMenu(), containerHeader, containerHeadButton, tv);
				break;
			default:
				Helper.showAlert(AlertType.ERROR, "Invalid role");
		}
		
		scene = new Scene(cont, 800, 600);
    }
	
	//method untuk refresh table
	private void repaint() {
		tv.getItems().clear();
		
		//ambil data dari database lalu dimasukkan ke arraylist
		ArrayList<PC> pc = PCRepository.getAllPCData();
		
		//masukkan data dari arraylist ke table
		for (PC pc2 : pc) {
			tv.getItems().add(pc2);
		}
	}

}
