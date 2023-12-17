package view.login_register;

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
import view.ViewPC;

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
		
		//deklarasi berbagai label dan textfield
		loginTitle = new Label("Login");
		usernameTitle = new Label("Username");
		passwordTitle = new Label("Password");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");
		loginButton = new Button("Login");
		registerHyperlink = new Hyperlink("Don't have an account? Register Here!");

		//memasukkan label dan textfield ke window secara berurutan
		vb.getChildren().addAll(loginTitle, usernameTitle, usernameInput, passwordTitle, passwordInput, loginButton,
				registerHyperlink);
		loginTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20)); //mengganti ukuran text pada sebuah label
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}

	//fungsionalitas tombol login
	private void addEventListener() {
		loginButton.setOnMouseClicked(e -> {
			
			//saat diklik, input user akan diambil
			String username = usernameInput.getText();
			String userPassword = passwordInput.getText();
			String statusLogin = "";
			
			//input user yang sudah diambil akan digunakan untuk dijadikan parameter
			//dalam pemanggilan user controller
			try {
				statusLogin = UserController.loginUser(username, userPassword);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(statusLogin.equals("Login Success!")) {
				
				User user = null;
				
				//jika berhasil, akan memunculkan prompt
				try {
					user = UserController.getUserData(username, userPassword);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Helper.showAlert(AlertType.INFORMATION, statusLogin);
				UserSession.setCurrentUser(user); //set user session dengan User yang dipakai untuk login
				
				//redirect user ke View All PC
				ViewPC viewpc = ViewPC.getInstance();
				viewpc.show();
			}
			else {
				Helper.showAlert(AlertType.ERROR, statusLogin);
			}
			
		});

		//jika hyperlink diklik, user akan diarahkan ke laman Register
		registerHyperlink.setOnAction(e -> {
			Register registerPage = Register.getInstance();
			registerPage.show();
		});
	}
}
