package view;

import javafx.scene.Scene;
import main.MainStage;

public class TransactionHistory {
	
	private static TransactionHistory tHistory;
	private Scene scene;
	
	public static TransactionHistory getInstance() {
		return tHistory = tHistory == null ? new TransactionHistory() : tHistory;
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
}
