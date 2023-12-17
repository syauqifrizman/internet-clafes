package view.menu;

import controller.UserSessionController;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderLayout {
	public VBox getUserHeader() {
		//mendapatkan data user yang sudah login
		String usernameLogin = UserSessionController.getCurrentUsername();
		String userRoleLogin = UserSessionController.getCurrentUserRole();
		
		//memunculkan Username di bagian atas window
		HBox containerUsername = new HBox();
		Label usernameLabel = new Label("Login as: ");
		Label usernameLoginLabel = new Label(usernameLogin);
		usernameLoginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		containerUsername.getChildren().addAll(usernameLabel, usernameLoginLabel);
		
		//memunculkan Role di bagian atas window
		HBox containerUserRole = new HBox();
		Label UserRoleLabel = new Label("User Role as: ");
		Label userRoleLoginLabel = new Label(userRoleLogin);
		userRoleLoginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		containerUserRole.getChildren().addAll(UserRoleLabel, userRoleLoginLabel);
		
		
		VBox containerHeader = new VBox(4);
		containerHeader.getChildren().addAll(containerUsername, containerUserRole);
		
		return containerHeader;
	}
}
