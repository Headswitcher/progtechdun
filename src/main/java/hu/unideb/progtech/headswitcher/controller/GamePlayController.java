package hu.unideb.progtech.headswitcher.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
	private List<Room> rooms;
	private Room actualRoom;
	private Adventurer player;

	private Boolean north;
	private Boolean west;
	private Boolean south;
	private Boolean east;

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
		rooms = new LinkedList<>();
		actualRoom = new Room(0L, 0L, true, false, false, false, 0L, null, "nothing");
		rooms.add(actualRoom);
		player = new Adventurer("Majdkivalasztja", 3000L, 50L, 0L);
		diceRoll = new DiceRoll();

		nextRoomTest();
		disableActions();
		setPlayerLabels();

		// Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("A kezdet!");
		// alert.setHeaderText("Kómán felkelsz egy szobában ahonnét csak egy
		// kijárat van vigyázz hisz kalandok várnak!");
		// alert.showAndWait();

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
		setNextRoomForNorth();
		setRoomLabel();
		roomEventHandler();

	}

	@FXML
	private void handleDown(ActionEvent event) {
		setNextRoomForSouth();
		setRoomLabel();
		roomEventHandler();
	}

	@FXML
	private void handleRight(ActionEvent event) {
		setNextRoomForEast();
		setRoomLabel();
		roomEventHandler();
	}

	@FXML
	private void handleLeft(ActionEvent event) {
		setNextRoomForWest();
		setRoomLabel();
		roomEventHandler();
	}

	@FXML
	private void handleAttack(ActionEvent event) {
		actualRoom.getRoomMonster().setHealthPoint(actualRoom.getRoomMonster().getHealthPoint() - player.getDamage());
		setMonsterHpLabel();
		if (actualRoom.getRoomMonster().getHealthPoint() >= 0) {
			player.setHealthPoint(player.getHealthPoint() - actualRoom.getRoomMonster().getDamage());
			setPlayerLabels();
		}

		if (player.getHealthPoint() <= 0)
			gameOver();

		if (actualRoom.getRoomMonster().getHealthPoint() <= 0) {
			nextRoomTest();
			disableActions();
		}
	}

	private void setMonsterHpLabel() {
		monsterHealthPoints.setText(String.valueOf(actualRoom.getRoomMonster().getHealthPoint()));
	}

	private void gameOver() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A vég!");
		alert.setHeaderText("Sajnos legyőztek téged a pontod a következő:");
		alert.showAndWait();

		// Todo Elirányítás leaderboardra és beírni a számát

	}

	private void setNextRoomForNorth() {

		Long newRoomPosY = actualRoom.getPosy() + 1;
		if (rooms.stream()
				.anyMatch(rs -> rs.getPosx().equals(actualRoom.getPosx()) && rs.getPosy().equals(newRoomPosY)))
			for (Room room : rooms) {
				if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(newRoomPosY))
					actualRoom = room;
				south = actualRoom.getSouth();
				north = actualRoom.getNorth();
				west = actualRoom.getWest();
				east = actualRoom.getEast();
			}
		else {

			south = true;
			north = diceRoll.rollBoolean();
			west = diceRoll.rollBoolean();
			east = diceRoll.rollBoolean();
			if (north.equals(false) && west.equals(false) && east.equals(false))
				setNextRoomForNorth();

			if (diceRoll.roll(0, 10) == 1) {
				Monster m = spawnMonster();
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);

			} else {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
			}

		}
	}

	private Monster spawnMonster() {
		Long hp = actualRoom.getDepth() * 20;
		Long dmg = (long) (actualRoom.getDepth() * 1.4);
		Long gold = actualRoom.getDepth() * 10;
		Monster m = new Monster(hp, dmg, gold);
		return m;
	}

	private void setNextRoomForSouth() {
		Long newRoomPosY = actualRoom.getPosy() - 1;
		if (rooms.stream()
				.anyMatch(rs -> rs.getPosx().equals(actualRoom.getPosx()) && rs.getPosy().equals(newRoomPosY)))
			for (Room room : rooms) {
				if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(newRoomPosY))
					actualRoom = room;
				south = actualRoom.getSouth();
				north = actualRoom.getNorth();
				west = actualRoom.getWest();
				east = actualRoom.getEast();
			}
		else {

			south = diceRoll.rollBoolean();
			north = true;
			west = diceRoll.rollBoolean();
			east = diceRoll.rollBoolean();
			if (south.equals(false) && west.equals(false) && east.equals(false))
				setNextRoomForSouth();

			if (diceRoll.roll(0, 10) == 1) {
				Monster m = spawnMonster();
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);

			} else {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
			}

		}

	}

	private void setNextRoomForEast() {

		Long newRoomPosX = actualRoom.getPosx() + 1;
		if (rooms.stream()
				.anyMatch(rs -> rs.getPosx().equals(newRoomPosX) && rs.getPosy().equals(actualRoom.getPosy())))
			for (Room room : rooms) {
				if (room.getPosx().equals(newRoomPosX) && room.getPosy().equals(actualRoom.getPosy()))
					actualRoom = room;
				south = actualRoom.getSouth();
				north = actualRoom.getNorth();
				west = actualRoom.getWest();
				east = actualRoom.getEast();
			}
		else {

			south = diceRoll.rollBoolean();
			north = diceRoll.rollBoolean();
			west = true;
			east = diceRoll.rollBoolean();
			if (north.equals(false) && south.equals(false) && east.equals(false))
				setNextRoomForEast();

			if (diceRoll.roll(0, 10) == 1) {
				Monster m = spawnMonster();
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);

			} else {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
			}

		}

	}

	private void setNextRoomForWest() {
		Long newRoomPosX = actualRoom.getPosx() - 1;
		if (rooms.stream()
				.anyMatch(rs -> rs.getPosx().equals(newRoomPosX) && rs.getPosy().equals(actualRoom.getPosy())))
			for (Room room : rooms) {
				if (room.getPosx().equals(newRoomPosX) && room.getPosy().equals(actualRoom.getPosy()))
					actualRoom = room;
				south = actualRoom.getSouth();
				north = actualRoom.getNorth();
				west = actualRoom.getWest();
				east = actualRoom.getEast();
			}
		else {

			south = diceRoll.rollBoolean();
			north = diceRoll.rollBoolean();
			east = true;
			west = diceRoll.rollBoolean();
			if (north.equals(false) && south.equals(false) && west.equals(false))
				setNextRoomForEast();

			if (diceRoll.roll(0, 10) == 1) {
				Monster m = spawnMonster();
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);

			} else {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
			}

		}
	}

	private void setRoomLabel() {

		roomPosXLabel.setText(actualRoom.getPosx().toString());
		roomPosYLabel.setText(actualRoom.getPosy().toString());

	}

	private void disableActions() {
		attackButton.setDisable(true);

	}

	private void enableActions() {
		attackButton.setDisable(false);
	}

	private void setPlayerLabels() {
		playerHealthPoints.setText("HP: " + String.valueOf(player.getHealthPoint()));
		playerDamage.setText("DMG: " + String.valueOf(player.getDamage()));
	}

	private void disableControls() {
		upButton.setDisable(true);
		leftButton.setDisable(true);
		rightButton.setDisable(true);
		downButton.setDisable(true);
	}

	private void roomEventHandler() {

		if (actualRoom.getEvent() != null) {
			switch (actualRoom.getEvent()) {
			case "monster": {
				onMonsterEvent();
				break;
			}
			case "master": {
				onMasterEvent();
				break;
			}
			case "shop": {
				onShopEvent();
				break;
			}
			case "altar": {
				onAltarEvent();
				break;
			}
			case "nothing": {
				onNothingEvent();
				break;
			}
			}
		} else {

			System.out.println("nem kaphat null-t tho");
		}
	}

	private void onAltarEvent() {
		// TODO Auto-generated method stub

		System.out.println("Event:	Altar");

	}

	private void onNothingEvent() {
		System.out.println("Event:	Nothing");
		nextRoomTest();

	}

	private void onShopEvent() {
		// TODO Auto-generated method stub
		System.out.println("Event:	Shop");

	}

	private void onMasterEvent() {
		// TODO Auto-generated method stub
		System.out.println("Event:	Master");

	}

	private void onMonsterEvent() {
		// TODO Auto-generated method stub
		System.out.println("Event:	Monster");

		if (actualRoom.getRoomMonster().getHealthPoint() > 0) {
			setMonsterHpLabel();
			disableControls();
			enableActions();
		} else
			nextRoomTest();
	}

}
