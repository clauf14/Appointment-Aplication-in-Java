package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;

public class LoginPageController {
	@FXML
	private Button loginButton;
	@FXML
	private Button quitButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Label messageLabel;

	private static LoginPageController instance;

	public LoginPageController() {
		instance = this;
	}

	public static LoginPageController getInstance() {
		return instance;
	}

	public void error(String message) {
		messageLabel.setText(message);
	}

	public String username() {
		List<String> userInfo = new ArrayList<>();
		try {
			File myObj = new File("curentUser.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				userInfo.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return userInfo.get(0);
	}

	public void switchToMenuPage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MenuPage.fxml"));
		Stage window = (Stage) registerButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void switchToRegisterPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/Registerpage.fxml"));
		Stage window = (Stage) loginButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void loginButtonOnAction(ActionEvent event) throws Exception {
		if (usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty()) {
			messageLabel.setText("Please enter username and password!");
			messageLabel.setStyle("-fx-text-fill:red;");
		} else if (usernameTextField.getText().isEmpty() && !(passwordTextField.getText().isEmpty())) {
			messageLabel.setText("Please enter username!");
			messageLabel.setStyle("-fx-text-fill:red;");
		} else if (passwordTextField.getText().isEmpty() && !(usernameTextField.getText().isEmpty())) {
			messageLabel.setText("Please enter password!");
			messageLabel.setStyle("-fx-text-fill:red;");
		} else {
			validateLogin();
		}
	}

	public void quitButtonOnAction(ActionEvent event) {
		Stage window = (Stage) quitButton.getScene().getWindow();
		window.close();
	}

	public void registerButtonOnAction(ActionEvent event) throws IOException {
		switchToRegisterPage(event);
	}

	public void validateLogin() throws Exception {
		UserService userservice = new UserService();
		User loggedUser = userservice.findUser(usernameTextField.getText(), passwordTextField.getText());
		if (loggedUser != null) {
			System.out.println("succes");
			try {
				FileWriter myWriter = new FileWriter("curentUser.txt");
				myWriter.write(loggedUser.toString());
				myWriter.close();
				System.out.println("Successfully wrote user into file.");
				switchToMenuPage();
			} catch (IOException e) {
				messageLabel.setText("Invalid login!");
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			System.out.println("not ok");
			messageLabel.setText("Invalid login!");
			passwordTextField.setText("");
		}
	}

}
