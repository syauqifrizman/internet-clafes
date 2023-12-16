package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Helper {
	
	//method untuk memunculkan error/info prompt (Alert)
	public static void showAlert(AlertType alertType, String contentText) {
		
		//pembuatan object Alert baru
		Alert alert = new Alert(alertType);
		
		//set text nya sesuai yang diinginkan
		alert.setContentText(contentText);
		
		//tampilkan alert nya
		alert.show();
	}
}
