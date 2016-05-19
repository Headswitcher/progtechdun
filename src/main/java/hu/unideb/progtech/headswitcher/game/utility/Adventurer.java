package hu.unideb.progtech.headswitcher.game.utility;

public class Adventurer {

	private String name;
	private Long healthPoint;
	private Long damage;
	private Long gold;

	public Adventurer(String name, Long healthPoint, Long damage, Long gold) {
		super();
		this.name = name;
		this.healthPoint = healthPoint;
		this.damage = damage;
		this.gold = gold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(Long healthPoint) {
		this.healthPoint = healthPoint;
	}

	public Long getDamage() {
		return damage;
	}

	public void setDamage(Long damage) {
		this.damage = damage;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

}
