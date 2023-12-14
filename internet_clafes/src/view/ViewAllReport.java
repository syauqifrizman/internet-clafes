package view;

import javafx.scene.Scene;
import main.MainStage;

public class ViewAllReport {

	
	private static ViewAllReport viewAllReport;
	private Scene scene;
	
	public static ViewAllReport getInstance() {
		return viewAllReport = viewAllReport == null ? new ViewAllReport() : viewAllReport;
	}

	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}
