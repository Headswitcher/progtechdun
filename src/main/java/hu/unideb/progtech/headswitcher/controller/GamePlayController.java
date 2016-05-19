package hu.unideb.progtech.headswitcher.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.unideb.progtech.headswitcher.game.utility.Adventurer;
import hu.unideb.progtech.headswitcher.game.utility.DiceRoll;
import hu.unideb.progtech.headswitcher.game.utility.Monster;
import hu.unideb.progtech.headswitcher.game.utility.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GamePlayController implements Initializable {

	private DiceRoll diceRoll;
	private Room actualRoom;
	private Adventurer player;

	private Boolean north;
	private Boolean west;
	private Boolean south;
	private Boolean east;

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
		player = new Adventurer(200L, 150L, 150L);
		Room start = new Room(true, false, false, false, 1, null);
		diceRoll = new DiceRoll();
		actualRoom = start;
		nextRoomTest();
		setPlayerLabels();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A kezdet!");
		alert.setHeaderText("Kómán felkelsz egy szobában ahonnét csak egy kijárat van vigyázz hisz kalandok várnak!");
		alert.showAndWait();

	}

	private void setPlayerLabels() {
		playerHealthPoints.setText("HP: " + String.valueOf(player.getHealthPoint()));
		playerDamage.setText("DMG: " + String.valueOf(player.getDamage()));
	}

	private void nextRoomTest() {
		if (!actualRoom.getNorth())
			upButton.setDisable(true);
		else
			upButton.setDisable(false);
		if (!actualRoom.getSouth())
			downButton.setDisable(true);
		else
			downButton.setDisable(false);
		if (!actualRoom.getWest())
			leftButton.setDisable(true);
		else
			leftButton.setDisable(false);
		if (!actualRoom.getEast())
			rightButton.setDisable(true);
		else
			rightButton.setDisable(false);
	}

	@FXML
	private void handleUp(ActionEvent event) {
		System.out.println("Up");
		System.out.println("Kockadobás");

		roomRoll();

		if (actualRoom.getRoomMonster() != null)
			disableControls();
		else
			nextRoomTest();

	}

	@FXML
	private void handleDown(ActionEvent event) {
		System.out.println("Down");
		roomRoll();

		if (actualRoom.getRoomMonster() != null)
			disableControls();
		else
			nextRoomTest();
	}

	@FXML
	private void handleRight(ActionEvent event) {
		System.out.println("Right");
		roomRoll();

		if (actualRoom.getRoomMonster() != null)
			disableControls();
		else
			nextRoomTest();
	}

	@FXML
	private void handleLeft(ActionEvent event) {
		System.out.println("Left");
		roomRoll();

		if (actualRoom.getRoomMonster() != null)
			disableControls();
		else
			nextRoomTest();
	}

	@FXML
	private void handleAttack(ActionEvent event) {
		actualRoom.getRoomMonster().setHealthPoint(actualRoom.getRoomMonster().getHealthPoint() - player.getDamage());
		player.setHealthPoint(player.getHealthPoint() - actualRoom.getRoomMonster().getDamage());

		monsterHealthPoints.setText(String.valueOf(actualRoom.getRoomMonster().getHealthPoint()));
		setPlayerLabels();
		if (player.getHealthPoint() <= 0)
			gameOver();

		if (actualRoom.getRoomMonster().getHealthPoint() <= 0) {
			nextRoomTest();
		}
	}

	private void gameOver() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A vég!");
		alert.setHeaderText("Sajnos legyőztek téged a pontod a következő:");
		alert.showAndWait();

		// Todo Elirányítás leaderboardra és beírni a számát

	}

	private void roomRoll() {
		north = diceRoll.rollBoolean();
		west = diceRoll.rollBoolean();
		south = diceRoll.rollBoolean();
		east = diceRoll.rollBoolean();
		if (north.equals(false) && west.equals(false) && south.equals(false) && east.equals(false))
			roomRoll();
		if (diceRoll.roll(0, 10) == 1) {
			Monster m = new Monster(500L, 500L, 50L);
			actualRoom = new Room(north, west, east, south, actualRoom.getDepth() + 1, m);
		} else
			actualRoom = new Room(north, west, east, south, actualRoom.getDepth() + 1, null);
	}

	private void disableControls() {
		upButton.setDisable(true);
		leftButton.setDisable(true);
		rightButton.setDisable(true);
		downButton.setDisable(true);
	}
}
