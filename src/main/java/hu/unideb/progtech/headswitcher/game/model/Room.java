package hu.unideb.progtech.headswitcher.game.model;

public class Room {

	private Long posx;
	private Long posy;
	private Boolean north;
	private Boolean west;
	private Boolean east;
	private Boolean south;
	private Long depth;
	private Monster roomMonster;
	private String event;

	public Room(Long posx, Long posy, Boolean north, Boolean west, Boolean east, Boolean south, Long depth,
			Monster roomMonster, String event) {
		super();
		this.posx = posx;
		this.posy = posy;
		this.north = north;
		this.west = west;
		this.east = east;
		this.south = south;
		this.depth = depth;
		this.roomMonster = roomMonster;
		this.event = event;
	}

	public Long getPosx() {
		return posx;
	}

	public void setPosx(Long posx) {
		this.posx = posx;
	}

	public Long getPosy() {
		return posy;
	}

	public void setPosy(Long posy) {
		this.posy = posy;
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

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Monster getRoomMonster() {
		return roomMonster;
	}

	public void setRoomMonster(Monster roomMonster) {
		this.roomMonster = roomMonster;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
