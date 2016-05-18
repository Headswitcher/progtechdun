package hu.unideb.progtech.headswitcher.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
	private void handleExit(ActionEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
