package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class RegisterPageController {
	@FXML
	private Button backButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private Label messageLabel;

	public void warningAlert(ActionEvent event) {
		messageLabel.setText("");
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText("User " + usernameField.getText() + " already exists!");
		alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		alert.setHeaderText("Warning alert");
		alert.getDialogPane().setPrefSize(300, 200);
		alert.showAndWait();
	}

	public void createAlert(ActionEvent event) {
		messageLabel.setText("");
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Congratulations!");
		alert.setContentText("User " + usernameField.getText() + " has been registered succsessfully! "
				+ "Press the arrow in the top left corner to go back to the login page!");
		alert.setHeaderText("Congratulations!");
		alert.getDialogPane().setPrefSize(300, 250);
		alert.showAndWait();
	}

	public void backButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LoginPage.fxml"));
		Stage window = (Stage) registerButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void registerButtonOnAction(ActionEvent event) {
		if (passwordField.getText().equals(confirmPasswordField.getText()) && !(passwordField.getText().isEmpty())
				&& !(confirmPasswordField.getText().isEmpty()) && passwordField.getText().length() >= 8
				&& confirmPasswordField.getText().length() >= 8) {
			validateRegister(event);
		} else if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
				|| usernameField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()
				|| passwordField.getText().isEmpty()) {
			messageLabel.setText("Please complete all fields!");
			messageLabel.setStyle("-fx-text-fill:red;");
		} else {
			messageLabel.setText("Passwords does not match or password is shorter than 7 characters!");
			messageLabel.setStyle("-fx-text-fill:red;");
		}

	}

	public void validateRegister(ActionEvent event) {
		UserService userService = new UserService();
		try {
			userService.addUser(firstNameField.getText(), lastNameField.getText(), passwordField.getText(),
					usernameField.getText(), 1);
			System.out.println("User registered successfully!");
			createAlert(event);
		} catch (Exception e) {
			System.out.println("User already exits!");
			warningAlert(event);
		}
	}
}
