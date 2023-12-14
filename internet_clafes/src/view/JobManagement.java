package view;

import javafx.scene.Scene;
import main.MainStage;

public class JobManagement {

	
	private static JobManagement jobManagement;
	private Scene scene;
	
	public static JobManagement getInstance() {
		return jobManagement = jobManagement == null ? new JobManagement() : jobManagement;
	}

	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}
