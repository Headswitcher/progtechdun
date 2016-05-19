package hu.unideb.progtech.headswitcher.game.utility;

public class Room extends GameObject {

	private Boolean north;
	private Boolean west;
	private Boolean east;
	private Boolean south;
	private Integer depth;
	private Monster roomMonster;

	public Room(Boolean north, Boolean west, Boolean east, Boolean south, Integer depth, Monster roomMonster) {
		super();
		this.north = north;
		this.west = west;
		this.east = east;
		this.south = south;
		this.depth = depth;
		this.roomMonster = roomMonster;
	}

	public Monster getRoomMonster() {
		return roomMonster;
	}

	public void setRoomMonster(Monster roomMonster) {
		this.roomMonster = roomMonster;
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

	public Boolean getEast() {
		return east;
	}

	public void setEast(Boolean east) {
		this.east = east;
	}

	public Boolean getSouth() {
		return south;
	}

	public void setSouth(Boolean south) {
		this.south = south;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
