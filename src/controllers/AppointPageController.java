package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Place;
import service.PlaceService;

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

public class AppointPageController implements Initializable {
	@FXML
	private TextField usernameField;
	@FXML
	private TextField locationNameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField adressField;
	@FXML
	private TextField appointmentDate;
	@FXML
	private TextField availablePlaces;
	@FXML
	private Button deleteButtonAppointment;
	@FXML
	private Button menuButton;
	@FXML
	private Button logOutButton;
	@FXML
	private Button quitButton;
	@FXML
	private Button accountButton;

	public void infoAlert(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setContentText("Your appointment has been deleted!");
		alert.setHeaderText("Info");
		alert.getDialogPane().setPrefSize(300, 200);
		alert.showAndWait();
	}

	public void switchToMenuPage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MenuPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void deleteButtonAppointmentOnAction(ActionEvent event) throws IOException {
		try {
			FileWriter myWriter = new FileWriter("currentLocation.txt");
			myWriter.write("");
			myWriter.close();
			System.out.println("Successfully deleted location from file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchToMenuPage();
		infoAlert(event);
	}

	public void accountButtonOnAction() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/AccountPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void quitButtonOnAction(ActionEvent event) {
		Stage window = (Stage) quitButton.getScene().getWindow();
		window.close();
	}

	public void logOutButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LoginPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void menuButtonOnAction(ActionEvent event) throws IOException {
		switchToMenuPage();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameField.setText(LoginPageController.getInstance().username());
		try {
			getAppointment();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getAppointment() throws Exception {
		PlaceService placeService = new PlaceService();
		List<String> placeInfo = new ArrayList<>();
		try {
			File myObj = new File("currentLocation.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				placeInfo.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		Place p = placeService.findDate(placeInfo.get(0));
		locationNameField.setText(p.getLocation().getName());
		priceField.setText(p.getLocation().getPrice());
		adressField.setText(p.getLocation().getAdress());
		appointmentDate.setText(p.getDate());
		availablePlaces.setText(p.getAvailablePlaces());
	}
}
