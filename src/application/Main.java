package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LoginPage.fxml"));
		Scene scene = new Scene(root, 600, 400);
		stage.setResizable(false);
		stage.setTitle("Footbalify");
		stage.getIcons().add(new Image("/resources/images/288976.png"));
		stage.setScene(scene);
		stage.show();
		System.out.println("Application is live!");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
