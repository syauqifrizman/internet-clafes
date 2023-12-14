package view;

import javafx.scene.Scene;
import main.MainStage;

public class PCBookedData {

	private static PCBookedData bookedData;
	private Scene scene;
	
	public static PCBookedData getInstance() {
		return bookedData = bookedData == null ? new PCBookedData() : bookedData;
	}

	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
}
