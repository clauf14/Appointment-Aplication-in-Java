package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;

public class AccountPageController implements Initializable {
	@FXML
	private Button menuButton;
	@FXML
	private Button appointmentButton;
	@FXML
	private Button logOutButton;
	@FXML
	private Button quitButton;
	@FXML
	private TextField currentFirstName;
	@FXML
	private TextField currentLastName;
	@FXML
	private TextField currentUsername;
	@FXML
	private TextField currentPassword;
	@FXML
	private PasswordField updatePassword;
	@FXML
	private TextField updateUsername;
	@FXML
	private TextField updateLastName;
	@FXML
	private TextField updateFirstName;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteAccount;
	@FXML
	private Label messageLabel;

	public void infoAlert(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setContentText("Your account has been deleted!");
		alert.setHeaderText("Info");
		alert.getDialogPane().setPrefSize(300, 200);
		alert.showAndWait();
	}

	public void warningAlert(ActionEvent event) {
		messageLabel.setText("");
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText("User " + updateUsername.getText() + " already exists! Try another username!");
		alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		alert.setHeaderText("Warning alert");
		alert.getDialogPane().setPrefSize(300, 200);
		alert.showAndWait();
	}

	public void menuButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MenuPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void appointmentButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/AppointPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void switchToLoginPage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LoginPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void logOutButtonOnAction(ActionEvent event) throws IOException {
		switchToLoginPage();
	}

	public void quitButtonOnAction(ActionEvent event) {
		Stage window = (Stage) quitButton.getScene().getWindow();
		window.close();
	}

	public void updateButtonOnAction(ActionEvent event) throws Exception {
		if (updatePassword.getText().length() >= 8) {
			try {
				UserService userService = new UserService();
				User user = userService.findUser(currentUsername.getText(), currentPassword.getText());
				User newUser = new User(updateFirstName.getText(), updateLastName.getText(), updatePassword.getText(),
						updateUsername.getText(), 1);
				userService.updateUser(user, newUser);
				messageLabel.setText(
						"Your account has been updated! Your new username is " + updateUsername.getText() + "!");
				messageLabel.setStyle("-fx-text-fill:black;");
				try {
					FileWriter myWriter = new FileWriter("curentUser.txt");
					myWriter.write(newUser.toString());
					myWriter.close();
					System.out.println("Successfully wrote user into file.");
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("Username already exists!");
				warningAlert(event);
			}
		} else {
			messageLabel.setText("The password needs to be longer than 7 characters!");
			messageLabel.setStyle("-fx-text-fill:red;");
		}

	}

	public void deleteAccountOnAction(ActionEvent event) throws Exception {
		UserService userService = new UserService();
		try {
			User user = userService.findUser(currentUsername.getText(), currentPassword.getText());
			userService.deleteUser(user);
		} catch (Exception e) {
			User user = userService.findUser(updateUsername.getText(), updatePassword.getText());
			userService.deleteUser(user);
		}
		switchToLoginPage();
		infoAlert(event);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		File file = new File("currentLocation.txt");
		if (file.length() == 0)
			appointmentButton.setDisable(true);
		else
			appointmentButton.setDisable(false);
		try {
			currentUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void currentUserInfo() throws Exception {
		UserService userService = new UserService();
		List<String> userInfo = new ArrayList<String>();
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
		User user = userService.findUser(userInfo.get(0), userInfo.get(1));
		currentFirstName.setText(user.getFirstName());
		currentLastName.setText(user.getLastName());
		currentUsername.setText(user.getUsername());
		currentPassword.setText(user.getPassword());
	}
}
