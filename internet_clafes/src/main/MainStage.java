package main;

import javafx.stage.Stage;

public class MainStage {

	private Stage stage;

	private static MainStage mainStage;

	public static MainStage getInstance() {
		return mainStage = mainStage == null ? new MainStage() : mainStage;
	}

	private MainStage() {
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}
}
