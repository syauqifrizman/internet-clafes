package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.login_register.Login;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MainStage mainStage = MainStage.getInstance();
		mainStage.setStage(primaryStage);
		
		Login login = Login.getInstance();
		login.show();
		
		mainStage.getStage().setTitle("Internet CLafes");
		mainStage.getStage().setResizable(false);
		mainStage.getStage().show();
		primaryStage.show();
	}

}
