package view;

import javafx.scene.Scene;
import main.MainStage;

public class AllTransactionHistory {

	private static AllTransactionHistory atHistory;
	private Scene scene;
	
	public static AllTransactionHistory getInstance() {
		return atHistory = atHistory == null ? new AllTransactionHistory() : atHistory;
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}
