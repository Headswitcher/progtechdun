package hu.unideb.progtech.headswitcher.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.unideb.progtech.headswitcher.game.utility.GameLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

		GL.nextRoomTest(upButton, downButton, leftButton, rightButton);
		GL.disableActions(attackButton);
		GL.setPlayerLabels(playerHealthPoints, playerDamage, playerGold);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A kezdet!");
		alert.setHeaderText("Kómán felkelsz egy szobában ahonnét csak egy kijárat van vigyázz hisz kalandok várnak!");
		alert.showAndWait();
	}

	@FXML
	private void handleUp(ActionEvent event) {
		GL.setNextRoomForNorth();
		GL.setRoomLabel(roomPosXLabel, roomPosYLabel);
		GL.nextRoomTest(upButton, downButton, leftButton, rightButton);
		GL.roomEventHandler(upButton, leftButton, rightButton, downButton, monsterHealthPoints, attackButton,
				playerHealthPoints, playerDamage, playerGold);
	}

	@FXML
	private void handleDown(ActionEvent event) {
		GL.setNextRoomForSouth();
		GL.setRoomLabel(roomPosXLabel, roomPosYLabel);
		GL.nextRoomTest(upButton, downButton, leftButton, rightButton);
		GL.roomEventHandler(upButton, leftButton, rightButton, downButton, monsterHealthPoints, attackButton,
				playerHealthPoints, playerDamage, playerGold);
	}

	@FXML
	private void handleRight(ActionEvent event) {
		GL.setNextRoomForEast();
		GL.setRoomLabel(roomPosXLabel, roomPosYLabel);
		GL.nextRoomTest(upButton, downButton, leftButton, rightButton);
		GL.roomEventHandler(upButton, leftButton, rightButton, downButton, monsterHealthPoints, attackButton,
				playerHealthPoints, playerDamage, playerGold);
	}

	@FXML
	private void handleLeft(ActionEvent event) {
		GL.setNextRoomForWest();
		GL.setRoomLabel(roomPosXLabel, roomPosYLabel);
		GL.nextRoomTest(upButton, downButton, leftButton, rightButton);
		GL.roomEventHandler(upButton, leftButton, rightButton, downButton, monsterHealthPoints, attackButton,
				playerHealthPoints, playerDamage, playerGold);
	}

	@FXML
	private void handleAttack(ActionEvent event) {
		GL.attackIfPlayerDiesGoMainMenu(monsterHealthPoints, playerHealthPoints, playerDamage, playerGold, attackButton, upButton, downButton,
				leftButton, rightButton);
	}

	// private void goMainMenu() {
	//
	// Stage stage;
	// Parent root;
	// try {
	// FXMLLoader loader = new
	// FXMLLoader(getClass().getResource("/view/fxml/menu/MainMenu.fxml"));
	// root = loader.load();
	// loader.<MainMenuController> getController();
	// stage = (Stage) attackButton.getScene().getWindow();
	// Scene scene = new Scene(root);
	// stage.setScene(scene);
	// stage.show();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	/////////////////////

}
