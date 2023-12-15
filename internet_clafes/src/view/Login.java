package view;

import java.sql.SQLException;

import controller.UserController;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.MainStage;
import model.User;
import model.UserSession;

public class Login{
	private static Login login;
	
	public static Login getInstance() {
		return login = login == null ? new Login() : login;
	}
	
	public Login() {
		initialize();
		addEventListener();
	}

	private Scene scene;
	private VBox vb;
	private Label loginTitle, usernameTitle, passwordTitle;
	private TextField usernameInput;
	private PasswordField passwordInput;
	private Button loginButton;
	private Hyperlink registerHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);
		loginTitle = new Label("Login");
		usernameTitle = new Label("Username");
		passwordTitle = new Label("Password");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");
		loginButton = new Button("Login");
		registerHyperlink = new Hyperlink("Don't have an account? Register Here!");

		vb.getChildren().addAll(loginTitle, usernameTitle, usernameInput, passwordTitle, passwordInput, loginButton,
				registerHyperlink);
		loginTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}

	private void addEventListener() {
		loginButton.setOnMouseClicked(e -> {
			String username = usernameInput.getText();
			String userPassword = passwordInput.getText();
			User user;
			try {
				user = UserController.getUserData(username, userPassword);
				if (user!=null) {
					UserSession.setCurrentUser(user);
					ViewPC viewpc = ViewPC.getInstance();
					viewpc.show();
				}
				else {
					Helper.showAlert(AlertType.ERROR, "User not found");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				Helper.showAlert(AlertType.ERROR, "User not found");
			}
			
		});

		registerHyperlink.setOnAction(e -> {
			Register registerPage = Register.getInstance();
			registerPage.show();
		});
	}
}
