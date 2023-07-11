package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Location;
import model.Place;
import service.LocationService;
import service.PlaceService;
import javafx.scene.control.ChoiceBox;

public class MenuPageController implements Initializable {
	@FXML
	private Button appointmentButton;
	@FXML
	private Button logOutButton;
	@FXML
	private Button quitButton;
	@FXML
	private Label nameLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label adressLabel;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private ChoiceBox<String> choiceBox1;
	@FXML
	private ChoiceBox<String> choiceBox2;
	@FXML
	private ChoiceBox<String> choiceBox3;
	@FXML
	private Label priceLabel;
	@FXML
	private Label availablePlaceLabel;
	@FXML
	private Label messageLabel;
	@FXML
	private Label nameLabel1;
	@FXML
	private Label descriptionLabel1;
	@FXML
	private Label adressLabel1;
	@FXML
	private Label priceLabel1;
	@FXML
	private Label availablePlaceLabel1;
	@FXML
	private Label nameLabel2;
	@FXML
	private Label descriptionLabel2;
	@FXML
	private Label adressLabel2;
	@FXML
	private Label priceLabel2;
	@FXML
	private Label availablePlaceLabel2;
	@FXML
	private Label nameLabel3;
	@FXML
	private Label descriptionLabel3;
	@FXML
	private Label adressLabel3;
	@FXML
	private Label priceLabel3;
	@FXML
	private Label availablePlaceLabel3;
	@FXML
	private Button bookButton;
	@FXML
	private Button bookButton1;
	@FXML
	private Button bookButton2;
	@FXML
	private Button bookButton3;
	@FXML
	private Pane mainPane;
	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private Button accountButton;
	@FXML
	private Label appointmentMessage;

	public enum Greetings {
		Welcome, Hello, hi, cheers, Howdy, Salute, bonjour, good_evening, Hola
	}

	private Greetings randomGreeting() { // enum
		String regex = "^[A-Z].*"; // regex
		Pattern pattern = Pattern.compile(regex);
		List<Greetings> greet = new ArrayList<>();
		for (Greetings greeting : Greetings.values()) {
			String value = greeting.name();
			Matcher matcher = pattern.matcher(value);
			boolean startsWithCapitalLetter = matcher.find();
			if (startsWithCapitalLetter) {
				greet.add(greeting);
			}
		}
		Random random = new Random();
		int randomIndex = random.nextInt(greet.size());
		return greet.get(randomIndex);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundel) {
		File file = new File("currentLocation.txt");
		if (file.length() == 0)
			appointmentButton.setDisable(true);
		else
			appointmentButton.setDisable(false);
		try {
			locationInfo("Park Arena", nameLabel, descriptionLabel, adressLabel, priceLabel, availablePlaceLabel,
					choiceBox);
			locationInfo("Pro Arena", nameLabel1, descriptionLabel1, adressLabel1, priceLabel1, availablePlaceLabel1,
					choiceBox1);
			locationInfo("AMS Sports Center", nameLabel2, descriptionLabel2, adressLabel2, priceLabel2,
					availablePlaceLabel2, choiceBox2);
			locationInfo("Rio Sport Fotbal", nameLabel3, descriptionLabel3, adressLabel3, priceLabel3,
					availablePlaceLabel3, choiceBox3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		messageLabel.setText(randomGreeting() + ", " + LoginPageController.getInstance().username() + "!");
	}

	public void appointmentButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/AppointPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void logOutButtonOnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LoginPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void quitButtonOnAction(ActionEvent event) {
		Stage window = (Stage) quitButton.getScene().getWindow();
		window.close();
	}

	public void accountButtonOnAction() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/AccountPage.fxml"));
		Stage window = (Stage) logOutButton.getScene().getWindow();
		window.setScene(new Scene(root));
	}

	public void bookButtonOnAction(ActionEvent event) {
		getAppointmentValue(choiceBox);
	}

	public void bookButtonOnAction1(ActionEvent event) {
		getAppointmentValue(choiceBox1);
	}

	public void bookButtonOnAction2(ActionEvent event) {
		getAppointmentValue(choiceBox2);
	}

	public void bookButtonOnAction3(ActionEvent event) {
		getAppointmentValue(choiceBox3);
	}

	public void locationInfo(String locationName, Label name, Label description, Label adress, Label price,
			Label availablePlace, ChoiceBox<String> choiceBox) throws Exception {
		LocationService locationService = new LocationService();
		Location loc = locationService.findLocation(locationName);
		name.setText(loc.getName());
		description.setText("Description: " + loc.getDescription());
		adress.setText("Adress:" + loc.getAdress());
		price.setText(loc.getPrice());
		PlaceService placeService = new PlaceService();
		List<Place> places = placeService.getAllPlaces();
		List<String> sortedPlaces = new ArrayList<>();
		if (loc.getActive() == 1) {
			for (int i = 1; i <= places.size(); i++) {
				Place place = placeService.findPlace(i);
				if (place.getLocation().getLocationId() == loc.getLocationId()) {
					availablePlace.setText(place.getAvailablePlaces());
					sortedPlaces.add(place.getDate());
				}
			}
			Collections.sort(sortedPlaces);
			choiceBox.getItems().addAll(sortedPlaces);
		} else {
			choiceBox.setDisable(true);
			availablePlace.setText("No places");
		}
	}

	public void getAppointmentValue(ChoiceBox<String> choiceBox) {
		String value = choiceBox.getValue();
		try {
			FileWriter myWriter = new FileWriter("currentLocation.txt");
			myWriter.write(value);
			myWriter.close();
			System.out.println("Successfully wrote user into file.");
		} catch (IOException e) {
			System.out.println("error");
		}
		System.out.println(value);
		appointmentMessage.setText("Your appointment has been recorded! Check 'Appointment' page!");
		appointmentButton.setDisable(false);
	}
}
