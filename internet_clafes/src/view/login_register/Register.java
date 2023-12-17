package view.login_register;

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

public class Register {
	private static Register register;
	
	public static Register getInstance() {
		return register = register == null ? new Register() : register;
	}
	
	private Register() {
		initialize();
		addEventListener();
	}
	
	//deklarasi label, textfield, dll
	private Scene scene;
	private VBox vb;
	private Label registerTitle, usernameTitle, passwordTitle, ageTitle, confTitle;
	private TextField usernameInput, ageInput;
	private PasswordField passwordInput, confInput;
	private Button registerButton;
	private Hyperlink loginHyperlink;

	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}

	private void initialize() {
		vb = new VBox(10);
		
		//instansaisi dan definisi dari label, textfield, dll yang sudah dibuat
		registerTitle = new Label("Register (Default User Role will be 'Customer')");
		usernameTitle = new Label("Username");
		passwordTitle = new Label("Password");
		confTitle = new Label("Confirm Password");
		ageTitle = new Label("Age");
		usernameInput = new TextField();
		usernameInput.setPromptText("Input your username here");
		passwordInput = new PasswordField();
		passwordInput.setPromptText("Input your password here");
		confInput = new PasswordField();
		confInput.setPromptText("Confirm your password here");
		ageInput = new TextField();
		ageInput.setPromptText("Input your age here");
		registerButton = new Button("Register");
		loginHyperlink = new Hyperlink("Already have an account? Login Here!");
		
		//memasukkan label dll ke layar
		vb.getChildren().addAll(registerTitle, usernameTitle, usernameInput, passwordTitle, 
				passwordInput, confTitle, confInput, ageTitle, ageInput, registerButton, loginHyperlink);
		registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(50));
		scene = new Scene(vb, 800, 600);
	}
	
	//method berisi fungsionalitas button
	private void addEventListener() {
		registerButton.setOnMouseClicked(e -> {
			
			//mendapatkan input user dan dimasukkan ke temp var
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			String age = ageInput.getText();
			String confpass = confInput.getText();
			String statusRegister = "";
			
			//saat tombol Register diklik, input user akan digunakan sebagai parameter untuk membuat user baru
			statusRegister = UserController.AddNewUser(username, password, age, confpass);
			
			//memunculkan prompt jika gagal/sukses
			if(statusRegister.equals("Registration Successful")) {
				Helper.showAlert(AlertType.INFORMATION, statusRegister);
				Login login = Login.getInstance();
				login.show();
			}
			else if(statusRegister.equals("Registration Failed")){
				Helper.showAlert(AlertType.ERROR, statusRegister);
			}
			else {
				Helper.showAlert(AlertType.ERROR, statusRegister);
			}

		});
		
		loginHyperlink.setOnAction(e -> {
			//jika tombol login diklik, user akan diarahkan ke laman login
			Login loginPage = Login.getInstance();
			loginPage.show();
		});
	}

}
