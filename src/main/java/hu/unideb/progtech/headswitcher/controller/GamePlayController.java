package hu.unideb.progtech.headswitcher.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.unideb.progtech.headswitcher.game.utility.GameLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GamePlayController implements Initializable {

	GameLogic GL;

	@FXML
	private Label playerGold;
	@FXML
	private Label roomPosXLabel;
	@FXML
	private Label roomPosYLabel;
	@FXML
	private Label playerHealthPoints;
	@FXML
	private Label playerDamage;
	@FXML
	private Label monsterHealthPoints;
	@FXML
	private Label monsterDamage;
	@FXML
	private Button attackButton;
	@FXML
	private Button upButton;
	@FXML
	private Button downButton;
	@FXML
	private Button rightButton;
	@FXML
	private Button leftButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		GL = new GameLogic();
		enableDirections();
		disableActions();
		setPlayerLabels();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A kezdet!");
		alert.setHeaderText("Kómán felkelsz egy szobában ahonnét csak egy kijárat van vigyázz hisz kalandok várnak!");
		alert.showAndWait();

	}

	@FXML
	private void handleUp(ActionEvent event) {
		GL.setNextRoomForNorth();
		setLabels();
		checkMonsterThenSetControlls();
		if (GL.isItGameOver()) {
			gameOver();
		}
	}

	@FXML
	private void handleDown(ActionEvent event) {
		GL.setNextRoomForSouth();
		setRoomLabel();

		setLabels();
		checkMonsterThenSetControlls();
		if (GL.isItGameOver()) {
			gameOver();
		}
	}

	@FXML
	private void handleRight(ActionEvent event) {
		GL.setNextRoomForEast();
		enableDirections();

		setLabels();
		checkMonsterThenSetControlls();
		if (GL.isItGameOver()) {
			gameOver();
		}
	}

	@FXML
	private void handleLeft(ActionEvent event) {
		GL.setNextRoomForWest();
		enableDirections();

		setLabels();
		checkMonsterThenSetControlls();
		if (GL.isItGameOver()) {
			gameOver();
		}
	}

	@FXML
	private void handleAttack(ActionEvent event) {
		GL.attack();
		enableDirections();
		setLabels();
		checkMonsterThenSetControlls();
		if (GL.isItGameOver()) {
			gameOver();
		}
	}

	public void enableDirections() {

		if (!GL.getActualRoom().getNorth()) {
			upButton.setDisable(true);
		} else {
			upButton.setDisable(false);
		}
		if (!GL.getActualRoom().getSouth()) {
			downButton.setDisable(true);
		} else {
			downButton.setDisable(false);
		}
		if (!GL.getActualRoom().getWest()) {
			leftButton.setDisable(true);
		} else {
			leftButton.setDisable(false);
		}
		if (!GL.getActualRoom().getEast()) {
			rightButton.setDisable(true);
		} else {
			rightButton.setDisable(false);
		}
	}

	private void gameOver() {
		GL.setHighScore();
		gameOverAlert();
		goMainMenu();

	}

	private void gameOverAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A vég!");
		alert.setHeaderText("Sajnos legyőztek téged a pontod a következő volt: " + GL.getPlayer().getGold());
		alert.showAndWait();
	}

	public void disableActions() {
		attackButton.setDisable(true);
	}

	private void enableActions() {
		attackButton.setDisable(false);
	}

	private void setMonsterHpLabel() {
		if (GL.getActualRoom().getRoomMonster() != null) {
			monsterHealthPoints.setText(String.valueOf(GL.getActualRoom().getRoomMonster().getHealthPoint()));
			monsterDamage.setText(String.valueOf(GL.getActualRoom().getRoomMonster().getDamage()));
		}
	}

	public void setPlayerLabels() {
		playerHealthPoints.setText("HP: " + String.valueOf(GL.getPlayer().getMaxHealthPoint()) + "/"
				+ String.valueOf(GL.getPlayer().getHealthPoint()));
		playerDamage.setText("DMG: " + String.valueOf(GL.getPlayer().getDamage()));
		playerGold.setText(GL.getPlayer().getGold().toString());
	}

	public void setRoomLabel() {
		roomPosXLabel.setText(GL.getActualRoom().getPosx().toString());
		roomPosYLabel.setText(GL.getActualRoom().getPosy().toString());
	}

	private void setLabels() {
		setRoomLabel();
		setMonsterHpLabel();
		setPlayerLabels();
	}

	private void goMainMenu() {

		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/menu/MainMenu.fxml"));
			root = loader.load();
			loader.<MainMenuController> getController();
			stage = (Stage) attackButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkMonsterThenSetControlls() {
		// If theres a monster
		if (GL.getActualRoom().getRoomMonster() != null) {
			enableActions();
			disableDirections();
		} else {
			disableActions();
			enableDirections();
		}
	}

	private void disableDirections() {
		upButton.setDisable(true);
		downButton.setDisable(true);
		leftButton.setDisable(true);
		rightButton.setDisable(true);
	}

}
