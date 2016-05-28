package hu.unideb.progtech.headswitcher.game.utility;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.unideb.progtech.headswitcher.game.model.Adventurer;
import hu.unideb.progtech.headswitcher.game.model.Monster;
import hu.unideb.progtech.headswitcher.game.model.Room;
import hu.unideb.progtech.headswitcher.main.MainApp;
import hu.unideb.progtech.headswitcher.service.HighScoreServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;

public class GameLogic {

	private DiceRoll diceRoll;
	private List<Room> rooms;
	private Room actualRoom;
	private Adventurer player;
	private Boolean isPlayerStillAlive;
	private Boolean isMonsterStillAlive;

	private Boolean north;
	private Boolean west;
	private Boolean south;
	private Boolean east;

	public GameLogic() {
		isPlayerStillAlive = true;

		rooms = new LinkedList<>();
		actualRoom = new Room(0L, 0L, true, false, false, false, 0L, null, "nothing");
		rooms.add(actualRoom);
		rooms.add(new Room(0L, 1L, true, true, true, true, 2L, null, "nothing"));
		player = new Adventurer(250L, 250L, 50L, 5L);
		diceRoll = new DiceRoll();
	}

	public void setNextRoomForWest() {
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

			int rolledValue;
			try {
				rolledValue = diceRoll.roll(1, 5);

				switch (rolledValue) {
				case 1: {
					Monster m = spawnMonster();
					isMonsterStillAlive = true;
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, m, "monster");
					rooms.add(actualRoom);
					break;
				}
				case 2: {
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
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
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				case 5: {
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void setNextRoomForEast() {

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

			int rolledValue;
			try {
				rolledValue = diceRoll.roll(1, 5);

				switch (rolledValue) {
				case 1: {
					Monster m = spawnMonster();
					isMonsterStillAlive = true;
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, m, "monster");
					System.out.println(actualRoom.getRoomMonster());
					rooms.add(actualRoom);
					break;
				}
				case 2: {
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
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
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				case 5: {
					actualRoom = new Room(newRoomPosX, actualRoom.getPosy(), north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void setNextRoomForSouth() {
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

			int rolledValue;
			try {
				rolledValue = diceRoll.roll(1, 5);

				switch (rolledValue) {
				case 1: {
					Monster m = spawnMonster();
					isMonsterStillAlive = true;
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, m, "monster");
					System.out.println(actualRoom.getRoomMonster());
					rooms.add(actualRoom);
					break;
				}
				case 2: {
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
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
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				case 5: {
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void setNextRoomForNorth() {

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

			int rolledValue;
			try {
				rolledValue = diceRoll.roll(1, 5);

				switch (rolledValue) {
				case 1: {
					System.out.println("MONSTER SPAWN");
					Monster m = spawnMonster();
					isMonsterStillAlive = true;
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, m, "monster");
					System.out.println(actualRoom.getRoomMonster());
					rooms.add(actualRoom);

					break;
				}
				case 2: {
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
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
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				case 5: {
					actualRoom = new Room(actualRoom.getPosx(), newRoomPosY, north, west, east, south,
							actualRoom.getDepth() + 1, null, "nothing");
					rooms.add(actualRoom);
					break;
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Monster spawnMonster() {
		Long hp = actualRoom.getDepth() * 20;
		Long dmg = (long) (actualRoom.getDepth() * 1.4);
		Long gold = actualRoom.getDepth() * 10;
		Monster m = new Monster(hp, gold, dmg);
		return m;
	}

	public void attack() {
		// Player attacks
		actualRoom.getRoomMonster().setHealthPoint(actualRoom.getRoomMonster().getHealthPoint() - player.getDamage());

		// Player getting attacked if monster still alive
		if (actualRoom.getRoomMonster().getHealthPoint() > 0)
			player.setHealthPoint(player.getHealthPoint() - actualRoom.getRoomMonster().getDamage());
		if (player.getHealthPoint() <= 0)
			isPlayerStillAlive = false;

		if (actualRoom.getRoomMonster().getHealthPoint() <= 0) {
			addGoldToPlayer();
			for (Room room : rooms)
				if ((room.getPosx().equals(actualRoom.getPosx())) && room.getPosy().equals(actualRoom.getPosy())) {
					room.setEvent("nothing");
					room.setRoomMonster(null);
				}
		}
	}

	public void setHighScore() {
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

	public void addGoldToPlayer() {
		player.setGold(player.getGold() + actualRoom.getRoomMonster().getGold());

	}

	public boolean isItGameOver() {
		return !isPlayerStillAlive;
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

	public Boolean getIsPlayerStillAlive() {
		return isPlayerStillAlive;
	}

	public void setIsPlayerStillAlive(Boolean isPlayerStillAlive) {
		this.isPlayerStillAlive = isPlayerStillAlive;
	}

	public Boolean getIsMonsterStillAlive() {
		return isMonsterStillAlive;
	}

	public void setIsMonsterStillAlive(Boolean isMonsterStillAlive) {
		this.isMonsterStillAlive = isMonsterStillAlive;
	}

}
