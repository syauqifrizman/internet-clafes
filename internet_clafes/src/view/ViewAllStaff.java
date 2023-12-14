package view;

import javafx.scene.Scene;
import main.MainStage;

public class ViewAllStaff {

	private static ViewAllStaff viewAllStaff;
	private Scene scene;
	
	public static ViewAllStaff getInstance() {
		return viewAllStaff = viewAllStaff == null ? new ViewAllStaff() : viewAllStaff;
	}

	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}
