package view.technician;

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
import view.menu.MenuComputerTechnician;

public class ViewTechnicianJob {
	
	private TableView<Job> tv;
	private Scene scene;
	private VBox cont;
	private Label jobsLabel;
	
	public static ViewTechnicianJob getInstance(String userID) {
		return new ViewTechnicianJob(userID);
	}
	
	public ViewTechnicianJob(String userID) {
		initTable();
		repaint(userID);
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	private void initTable() {
		cont = new VBox();
		jobsLabel = new Label("All Jobs For Computer Technician");
		
		tv = new TableView<Job>();
		
		TableColumn<Job, Integer> id = new TableColumn<>("Job ID");
		id.setCellValueFactory(new PropertyValueFactory<>("job_ID"));

		TableColumn<Job, Integer> user = new TableColumn<>("Computer Technician ID");
		user.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		TableColumn<Job, Integer> pc = new TableColumn<>("PC ID");
		pc.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<Job, String> stat = new TableColumn<>("Job Status");
		stat.setCellValueFactory(new PropertyValueFactory<>("jobStatus"));
		
		TableColumn<Job, Void> detail = new TableColumn<>("Update Job");
		Callback<TableColumn<Job, Void>, TableCell<Job, Void>> 
		cellFactory = new Callback<TableColumn<Job, Void>, TableCell<Job, Void>>() {
		    @Override
		    public TableCell<Job, Void> call(final TableColumn<Job, Void> param) {
		        final TableCell<Job, Void> cell = new TableCell<Job, Void>() {

		            private final Button updateButton = new Button("Finish Job");


		            {
		                updateButton.setOnAction((ActionEvent event) -> {
	                        Job job = getTableView().getItems().get(getIndex());
	                        JobController.updateJobStatus(job.getJob_ID().toString(), "Complete");
	                        repaint(job.getUserID().toString());
		                });
		            }

			            @Override
			            public void updateItem(Void item, boolean empty) {
			                super.updateItem(item, empty);
			                if (empty) {
			                    setGraphic(null);
			                } else {
			                	Job job = getTableView().getItems().get(getIndex());
			                	if(job.getJobStatus().equals("UnComplete")) {
			                		HBox containerButtons = new HBox();
			                		
			                		containerButtons.getChildren().add(updateButton);
			                		setGraphic(containerButtons);
			                	}
			                }
			            }
		           };
		        return cell;
		    }
		};
		detail.setCellFactory(cellFactory);
		
		jobsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		tv.getColumns().add(id);
		tv.getColumns().add(user);
		tv.getColumns().add(pc);
		tv.getColumns().add(stat);
		tv.getColumns().add(detail);
		
		if(UserSessionController.getCurrentUserRole().equals("Computer Technician")) {
			cont.getChildren().addAll(MenuComputerTechnician.createMenu(), jobsLabel, tv);
		}
		
		scene = new Scene(cont, 800, 600);
	}
	
	private void repaint(String userID) {
		tv.getItems().clear();
		ArrayList<Job> jobs = JobController.getTechnicianJob(userID);
		
		if(jobs.isEmpty()) {
			Helper.showAlert(AlertType.INFORMATION, "There is no job for you");
		}
		else {
			for (Job job : jobs) {
				tv.getItems().add(job);
			}			
		}
	}

}



