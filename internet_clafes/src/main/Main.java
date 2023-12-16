package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.login_register.Login;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//method dari class Application untuk menjalankan GUI dengan JavaFX
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//membuat class MainStage menjadi 'primary stage' (seperti dasar dari GUI nya)
		MainStage mainStage = MainStage.getInstance();
		mainStage.setStage(primaryStage);
		
		//memunculkan laman login
		Login login = Login.getInstance();
		login.show();
		
		//membuat title dari app nya sebagai "Internet CLafes"
		mainStage.getStage().setTitle("Internet CLafes");
		
		//membuat window app menjadi tidak bisa di-resize
		mainStage.getStage().setResizable(false);
		
		//munculkan GUI nya
		mainStage.getStage().show();
		primaryStage.show();
	}

}
