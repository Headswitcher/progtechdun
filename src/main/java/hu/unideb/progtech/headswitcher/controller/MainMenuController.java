package hu.unideb.progtech.headswitcher.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

	@FXML
	private Button startNewGameButton;

	@FXML
	private Button leaderboardsButton;

	@FXML
	private Button creditsButton;

	@FXML
	private Button exitButton;

	@FXML
	private void handlePlay(ActionEvent event) {
		System.out.println("Itt vagyok");
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/game/GamePlay.fxml"));
			root = loader.load();
			loader.<GamePlayController> getController();
			stage = (Stage) startNewGameButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleExit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void handleCredits(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("DON");
		alert.setHeaderText("Erdei Krisztán (PTI)");
		alert.showAndWait();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
