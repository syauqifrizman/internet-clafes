package view;

import javafx.scene.Scene;
import main.MainStage;

public class TechnicianJob {
	
	private static TechnicianJob technicianJob;
	private Scene scene;
	
	public static TechnicianJob getInstance() {
		return technicianJob = technicianJob == null ? new TechnicianJob() : technicianJob;
	}

	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}