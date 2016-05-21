package hu.unideb.progtech.headswitcher.game.utility;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.unideb.progtech.headswitcher.controller.MainMenuController;
import hu.unideb.progtech.headswitcher.game.model.Adventurer;
import hu.unideb.progtech.headswitcher.game.model.Monster;
import hu.unideb.progtech.headswitcher.game.model.Room;
import hu.unideb.progtech.headswitcher.main.MainApp;
import hu.unideb.progtech.headswitcher.service.HighScoreServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameLogic {

	private DiceRoll diceRoll;
	private List<Room> rooms;
	private Room actualRoom;
	private Adventurer player;

	private Boolean north;
	private Boolean west;
	private Boolean south;
	private Boolean east;

	public GameLogic() {
		rooms = new LinkedList<>();
		actualRoom = new Room(0L, 0L, true, false, false, false, 0L, null, "nothing");
		rooms.add(actualRoom);
		player = new Adventurer(250L, 250L, 50L, 5L);
		diceRoll = new DiceRoll();
	}

	public void nextRoomTest(Button upButton, Button downButton, Button leftButton, Button rightButton) {
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

	public void setRoomLabel(Label roomPosXLabel, Label roomPosYLabel) {

		roomPosXLabel.setText(actualRoom.getPosx().toString());
		roomPosYLabel.setText(actualRoom.getPosy().toString());

	}

	public void setNextRoomForWest() throws Exception {
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

			int rolledValue = diceRoll.roll(1, 5);

			switch (rolledValue) {
			case 1: {
				Monster m = spawnMonster();
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				rooms.add(actualRoom);
				break;
			}
			case 2: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "altar");
				rooms.add(actualRoom);
				break;
			}

			case 3: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
				break;
			}
			case 4: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "master");
				rooms.add(actualRoom);
				break;
			}
			case 5: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "shop");
				rooms.add(actualRoom);
				break;
			}
			}
			System.out.println(north);
			System.out.println(south);
			System.out.println(west);
			System.out.println(east);

		}
	}

	public void setNextRoomForEast() throws Exception {

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

			int rolledValue = diceRoll.roll(1, 5);

			switch (rolledValue) {
			case 1: {
				Monster m = spawnMonster();
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);
				break;
			}
			case 2: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "altar");
				rooms.add(actualRoom);
				break;
			}

			case 3: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
				break;
			}
			case 4: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "master");
				rooms.add(actualRoom);
				break;
			}
			case 5: {
				actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
						actualRoom.getDepth() + 1, null, "shop");
				rooms.add(actualRoom);
				break;
			}
			}

			System.out.println(rolledValue);
			System.out.println(actualRoom.getEvent());

		}

	}

	public void setNextRoomForSouth() throws Exception {
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

			int rolledValue = diceRoll.roll(1, 5);

			switch (rolledValue) {
			case 1: {
				Monster m = spawnMonster();
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);
				break;
			}
			case 2: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "altar");
				rooms.add(actualRoom);
				break;
			}

			case 3: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
				break;
			}
			case 4: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "master");
				rooms.add(actualRoom);
				break;
			}
			case 5: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "shop");
				rooms.add(actualRoom);
				break;
			}
			}

			System.out.println(rolledValue);
			System.out.println(actualRoom.getEvent());
		}

	}

	public void setNextRoomForNorth() throws Exception {

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

			int rolledValue = diceRoll.roll(1, 5);

			switch (rolledValue) {
			case 1: {
				Monster m = spawnMonster();
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, m, "monster");
				System.out.println(actualRoom.getRoomMonster());
				rooms.add(actualRoom);

				break;
			}
			case 2: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "altar");
				rooms.add(actualRoom);
				break;
			}

			case 3: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "nothing");
				rooms.add(actualRoom);
				break;
			}
			case 4: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "master");
				rooms.add(actualRoom);
				break;
			}
			case 5: {
				actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
						actualRoom.getDepth() + 1, null, "shop");
				rooms.add(actualRoom);
				break;
			}
			}

			System.out.println(rolledValue);
			System.out.println(actualRoom.getEvent());

		}
	}

	private Monster spawnMonster() {
		Long hp = actualRoom.getDepth() * 20;
		Long dmg = (long) (actualRoom.getDepth() * 1.4);
		Long gold = actualRoom.getDepth() * 10;
		Monster m = new Monster(hp, gold, dmg);
		return m;
	}

	public void setPlayerLabels(Label playerHealthPoints, Label playerDamage, Label playerGold) {
		playerHealthPoints.setText(
				"HP: " + String.valueOf(player.getMaxHealthPoint()) + "/" + String.valueOf(player.getHealthPoint()));
		playerDamage.setText("DMG: " + String.valueOf(player.getDamage()));
		playerGold.setText(player.getGold().toString());
	}

	private void onShopEvent(Label playerHealthPoints, Label playerDamage, Label playerGold) {

		Long hpPotionPrice = actualRoom.getDepth() * 20;

		if (player.getGold() >= hpPotionPrice) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Shop");
			alert.setHeaderText("Találkoztál egy kereskedővel aki egy gyógyító italt árul (50% élet)");
			alert.setContentText("Szeretnél venni eggyet kettőt?");

			ButtonType buttonTypeWantOne = new ButtonType("Kérek egyet," + hpPotionPrice + " gold",
					ButtonData.CANCEL_CLOSE);
			ButtonType buttonTypeDontWant = new ButtonType("Nem kérek", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeWantOne, buttonTypeDontWant);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeWantOne) {
				player.setHealthPoint(player.getHealthPoint() + (player.getMaxHealthPoint() / 2));
				if (player.getHealthPoint() > player.getMaxHealthPoint())
					player.setHealthPoint(player.getMaxHealthPoint());

				player.setGold(player.getGold() - hpPotionPrice);
				setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
			}
		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Shop");
			alert.setHeaderText("Sajnos nincs elgendő pénzed hogy életerő italt vásárolj!!");
			alert.setContentText("Ital ára: " + hpPotionPrice + " A te pénzed: " + player.getGold());
			alert.showAndWait();

		}
		System.out.println("Event:	Shop");

	}

	public void roomEventHandler(Button upButton, Button leftButton, Button rightButton, Button downButton,
			Label monsterHealthPoints, Button attackButton, Label playerHealthPoints, Label playerDamage,
			Label playerGold) {

		if (actualRoom.getEvent() != null) {
			switch (actualRoom.getEvent()) {
			case "monster": {
				onMonsterEvent(upButton, leftButton, rightButton, downButton, monsterHealthPoints, attackButton);
				break;
			}
			case "master": {
				onMasterEvent(playerHealthPoints, playerDamage, playerGold);
				break;
			}
			case "shop": {
				onShopEvent(playerHealthPoints, playerDamage, playerGold);
				break;
			}
			case "altar": {
				onAltarEvent(playerHealthPoints, playerDamage, playerGold);
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

	private void onNothingEvent() {
		System.out.println("Event:	Nothing");
	}

	private void onAltarEvent(Label playerHealthPoints, Label playerDamage, Label playerGold) {
		System.out.println("Event:	Altar");

		if (player.getHealthPoint() != player.getMaxHealthPoint()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Egy altár!");
			alert.setHeaderText("Visszanyerted az életerődet!");
			alert.showAndWait();
			player.setHealthPoint(player.getMaxHealthPoint());
			for (Room room : rooms)
				if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(actualRoom.getPosy()))
					room.setEvent("nothing");
			setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Egy altár!");
			alert.setHeaderText("Most tele van az életerőd térj vissza ide ha szükséged van rá!");
			alert.showAndWait();
		}
	}

	public void attackIfPlayerDiesGoMainMenu(Label monsterHealthPoints, Label playerHealthPoints, Label playerDamage, Label playerGold,
			Button attackButton, Button upButton, Button downButton, Button leftButton, Button rightButton) {
		actualRoom.getRoomMonster().setHealthPoint(actualRoom.getRoomMonster().getHealthPoint() - player.getDamage());

		if (actualRoom.getRoomMonster().getHealthPoint() > 0) {
			player.setHealthPoint(player.getHealthPoint() - actualRoom.getRoomMonster().getDamage());
			setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
		}

		if (player.getHealthPoint() <= 0)
			gameOver(attackButton);

		if (actualRoom.getRoomMonster().getHealthPoint() <= 0) {
			nextRoomTest(upButton, downButton, leftButton, rightButton);
			disableActions(attackButton);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Győzelem!");
			alert.setHeaderText(
					"Sikeresen legyőzted a szörnyet zsákmányod:" + actualRoom.getRoomMonster().getGold() + "!");
			alert.showAndWait();
			addGoldToPlayer();
			setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
			for (Room room : rooms)
				if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(actualRoom.getPosy()))
					room.setEvent("nothing");
		}

		setMonsterHpLabel(monsterHealthPoints);
	}

	private void gameOver(Button attackButton) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("A vég!");
		alert.setHeaderText("Sajnos legyőztek téged a pontod a következő volt: " + player.getGold());
		alert.showAndWait();

		setHighScore();
		goMainMenu(attackButton);

	}

	private void goMainMenu(Button attackButton) {

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

	private void setHighScore() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			HighScoreService hs = new HighScoreServiceImpl(entityManager);
			entityManager.getTransaction().begin();
			hs.createHighScore(MainApp.getActiveUser(), player.getGold());
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

	private void addGoldToPlayer() {
		player.setGold(player.getGold() + actualRoom.getRoomMonster().getGold());

	}

	private void onMasterEvent(Label playerHealthPoints, Label playerDamage, Label playerGold) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mester");
		alert.setHeaderText("Találkoztál egy mesterrel aki hajlandó tanítani");
		alert.setContentText("Válassz a két lehetőség közül eggyet");

		ButtonType buttonTypeMaxHp = new ButtonType("Élet", ButtonData.CANCEL_CLOSE);
		ButtonType buttonTypeDmg = new ButtonType("Sebzés", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeMaxHp, buttonTypeDmg);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeMaxHp) {
			player.setMaxHealthPoint(player.getMaxHealthPoint() + (actualRoom.getDepth() * 5));
			setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
		} else if (result.get() == buttonTypeDmg) {
			player.setDamage(player.getDamage() + actualRoom.getDepth());
			setPlayerLabels(playerHealthPoints, playerDamage, playerGold);
		}

		for (Room room : rooms)
			if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(actualRoom.getPosy()))
				room.setEvent("nothing");

		System.out.println("Event:	Master");

	}

	private void onMonsterEvent(Button upButton, Button leftButton, Button rightButton, Button downButton,
			Label monsterHealthPoints, Button attackButton) {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("SZÖRNYETEG");
		alert.setHeaderText("Egy szörnyeteg!!");
		alert.setContentText("Hogy tovább juss lekell győznöd!!");
		alert.showAndWait();

		setMonsterHpLabel(monsterHealthPoints);
		disableControls(upButton, leftButton, rightButton, downButton);
		enableActions(attackButton);

		System.out.println("Event:	Monster");
	}

	private void disableControls(Button upButton, Button leftButton, Button rightButton, Button downButton) {
		upButton.setDisable(true);
		leftButton.setDisable(true);
		rightButton.setDisable(true);
		downButton.setDisable(true);
	}

	private void enableActions(Button attackButton) {
		attackButton.setDisable(false);
	}

	private void setMonsterHpLabel(Label monsterHealthPoints) {
		monsterHealthPoints.setText(String.valueOf(actualRoom.getRoomMonster().getHealthPoint()));
	}

	public void disableActions(Button attackButton) {
		attackButton.setDisable(true);
	}

	public DiceRoll getDiceRoll() {
		return diceRoll;
	}

	public void setDiceRoll(DiceRoll diceRoll) {
		this.diceRoll = diceRoll;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room getActualRoom() {
		return actualRoom;
	}

	public void setActualRoom(Room actualRoom) {
		this.actualRoom = actualRoom;
	}

	public Adventurer getPlayer() {
		return player;
	}

	public void setPlayer(Adventurer player) {
		this.player = player;
	}

	public Boolean getNorth() {
		return north;
	}

	public void setNorth(Boolean north) {
		this.north = north;
	}

	public Boolean getWest() {
		return west;
	}

	public void setWest(Boolean west) {
		this.west = west;
	}

	public Boolean getSouth() {
		return south;
	}

	public void setSouth(Boolean south) {
		this.south = south;
	}

	public Boolean getEast() {
		return east;
	}

	public void setEast(Boolean east) {
		this.east = east;
	}

}
