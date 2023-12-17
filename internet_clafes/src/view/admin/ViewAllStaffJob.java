package view.admin;

import java.util.ArrayList;

import controller.JobController;

import helper.Helper;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import main.MainStage;
import model.Job;
import controller.UserSessionController;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;
import view.menu.MenuComputerTechnician;

public class ViewAllStaffJob extends HeaderLayout{
	
	//declare items2 
	private TableView<Job> tv;
	private Scene scene;
	private VBox cont;
	private Label jobsLabel;
	
	public static ViewAllStaffJob getInstance() {
		return new ViewAllStaffJob();
	}
	
	public ViewAllStaffJob() {
		initTable();
		repaint();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	private void initTable() {
		VBox containerHeader = getUserHeader();
		
		cont = new VBox();
		jobsLabel = new Label("All Jobs");
		
		//buat table data berdasarkan model Job
		tv = new TableView<Job>();
		
		//membuat kolom2 nya
		TableColumn<Job, Integer> id = new TableColumn<>("Job ID");
		id.setCellValueFactory(new PropertyValueFactory<>("job_ID"));

		TableColumn<Job, Integer> user = new TableColumn<>("Computer Technician ID");
		user.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		TableColumn<Job, Integer> pc = new TableColumn<>("PC ID");
		pc.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<Job, String> stat = new TableColumn<>("Job Status");
		stat.setCellValueFactory(new PropertyValueFactory<>("jobStatus"));
		
		//membuat column dengan button
		TableColumn<Job, Void> detail = new TableColumn<>("Update Job");
		Callback<TableColumn<Job, Void>, TableCell<Job, Void>> 
		cellFactory = new Callback<TableColumn<Job, Void>, TableCell<Job, Void>>() {
		    @Override
		    public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
		        final TableCell<Job, Void> cell = new TableCell<Job, Void>() {

		        	//declare button nya
		            private final Button updateButton = new Button("Update Job Status");

		            {
		            	//fungsionalitas tombol update Job. 
		                updateButton.setOnAction((ActionEvent event) -> {
		                	//mengambil data dari row
	                        Job job = getTableView().getItems().get(getIndex());

		                    ViewJobDetail viewJobDetail = ViewJobDetail.getInstance(job);
		                    viewJobDetail.show();
		                });
		            }

			            @Override
			            public void updateItem(Void item, boolean empty) {
			                super.updateItem(item, empty);
			                if (empty) {
			                    setGraphic(null);
			                } else {
			                    HBox containerButtons = new HBox();
			                    
			                    containerButtons.getChildren().add(updateButton);
			                    setGraphic(containerButtons);
			                }
			            }
		           };
		        return cell;
		    }
		};
		detail.setCellFactory(cellFactory);
		
		jobsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		//memasukkan kolom2 ke dalam tabel
		tv.getColumns().add(id);
		tv.getColumns().add(user);
		tv.getColumns().add(pc);
		tv.getColumns().add(stat);
		tv.getColumns().add(detail);
		
		//jika role User adalah Computer Technician, maka akan memunculkan menu bar Computer Technician
		if(UserSessionController.getCurrentUserRole().equals("Computer Technician")) {
			cont.getChildren().addAll(MenuComputerTechnician.createMenu(), containerHeader, jobsLabel, tv);
		}
		//jika role User adalah Admin, maka akan memunculkan menu bar Admin
		else if(UserSessionController.getCurrentUserRole().equals("Admin")) {
			cont.getChildren().addAll(MenuAdmin.createMenu(), containerHeader, jobsLabel, tv);
		}
		
		scene = new Scene(cont, 800, 600);
	}
	
	//method untuk 'refresh' table
	private void repaint() {
		tv.getItems().clear();

		//ambil data dari database
		ArrayList<Job> jobs = JobController.getAllJobData();
		
//		kalo jobs empty alias tidak ada jobs sama sekali untuk semua technician
		if(jobs.isEmpty()) {
			Helper.showAlert(AlertType.INFORMATION, "There is no job for any Computer Technician");
		}
		else {
			//isi table per row nya
			for (Job job : jobs) {
				tv.getItems().add(job);
			}	
		}
	}

}
