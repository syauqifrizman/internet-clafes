package view.admin;

import controller.JobController;
import controller.PCController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MainStage;
import model.Job;
import model.PC;
import controller.UserSessionController;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;

public class ViewJobDetail extends HeaderLayout{
	
	Stage primaryStage;
	Scene scene;

    public ViewJobDetail(Job job) {
		initialize(job);
		addEventListener(job);
    }
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	TextField jobStatusInput;
	Button deleteButton, updateButton;
	Label currentJobID, currentCompTechID,currentPCID, jobStatus;
	
	VBox container = new VBox(20);  // Set vertical spacing between elements
	private void initialize(Job job) {
		VBox containerHeader = getUserHeader();
		
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("About The Job");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    
	    // Job ID
	    Label jobIDLabel = new Label("Job ID: ");
	    currentJobID = new Label(job.getJob_ID().toString());
	    currentJobID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerJobID = new HBox();
	    containerJobID.getChildren().addAll(jobIDLabel, currentJobID);
	    
	    //  Computer Technician ID
	    Label compTechIDLabel = new Label("Computer Technician ID: ");
	    currentCompTechID = new Label(job.getUserID().toString());
	    currentCompTechID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerTechID = new HBox();
	    containerTechID.getChildren().addAll(compTechIDLabel, currentCompTechID);
	    
	    // PC ID
	    PC getPC = PCController.getPCDetail(job.getPc_ID().toString());
	    Label pcIDLabel = new Label("PC ID: ");
	    currentPCID = new Label(getPC.getPc_ID().toString());
	    currentPCID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerPCID = new HBox();
	    containerPCID.getChildren().addAll(pcIDLabel, currentPCID);
	    
	    //  Job Status
	    Label jobStatusLabel = new Label("Job Status: ");
	    jobStatus = new Label(job.getJobStatus());
	    jobStatus.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerJobStatus = new HBox();
	    containerJobStatus.getChildren().addAll(jobStatusLabel, jobStatus);

	    updateButton = new Button("UPDATE JOB STATUS");
	    
	    HBox containerButton = new HBox();
	    containerButton.getChildren().add(updateButton);
	    
	    // job status
	    Label statusTitle = new Label("Update Current Job Status to ['Complete' or 'UnComplete']:");
	    jobStatusInput = new TextField();
	    jobStatusInput.setPromptText("Insert Current Job Status");

	    // Add elements to the container
	    if(UserSessionController.getCurrentUserRole().equals("Admin")) {
	    	container.getChildren().add(MenuAdmin.createMenu());
	    }
	
	    
	    container.getChildren().addAll(containerHeader, pageTitle, containerPCID, containerJobStatus, statusTitle, jobStatusInput);
	    
	    if(UserSessionController.getCurrentUser().getUserRole().equals("Admin")) {
	    	container.getChildren().add(containerButton);
	    }

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	
	private void addEventListener(Job job) {
		updateButton.setOnAction(event -> {
		    String job_id = job.getJob_ID().toString();

		    // Move this line inside the event handler
		    String job_status = jobStatusInput.getText();
		    if(job.getJobStatus().equals(job_status)) {
		    	Helper.showAlert(AlertType.ERROR, "Already " + job_status + ", type another job status");
		    	return;
		    }
		    
		    JobController.updateJobStatus(job_id, job_status);
		    
		    Job getNewUpdateJob = JobController.getTechnicianJobByID(job_id);

		    ViewJobDetail ViewJobDetail = new ViewJobDetail(getNewUpdateJob);
		    ViewJobDetail.show();
		});

	}

	
    public static ViewJobDetail getInstance(Job fromViewAllStaff) {
    	return new ViewJobDetail(fromViewAllStaff);    	
    }
}
