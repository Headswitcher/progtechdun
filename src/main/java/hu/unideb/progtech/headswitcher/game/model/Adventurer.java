package hu.unideb.progtech.headswitcher.game.model;

public class Adventurer {

	private Long maxHealthPoint;
	private Long healthPoint;
	private Long damage;
	private Long gold;

	public Adventurer(Long maxHealthPoint, Long healthPoint, Long damage, Long gold) {
		super();
		this.maxHealthPoint = maxHealthPoint;
		this.healthPoint = healthPoint;
		this.damage = damage;
		this.gold = gold;
	}

	public Long getMaxHealthPoint() {
		return maxHealthPoint;
	}

	public void setMaxHealthPoint(Long maxHealthPoint) {
		this.maxHealthPoint = maxHealthPoint;
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
