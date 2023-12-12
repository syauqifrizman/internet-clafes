package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Helper {
	public static void showAlert(AlertType alertType, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setContentText(contentText);
		alert.show();
	}
}
