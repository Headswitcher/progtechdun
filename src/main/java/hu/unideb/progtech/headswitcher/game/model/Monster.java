package hu.unideb.progtech.headswitcher.game.model;

public class Monster {

	private Long healthPoint;
	private Long gold;
	private Long damage;

	public Monster(Long healthPoint, Long gold, Long damage) {
		this.healthPoint = healthPoint;
		this.gold = gold;
		this.damage = damage;
	}

	public Long getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(Long healthPoint) {
		this.healthPoint = healthPoint;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long experience) {
		this.gold = experience;
	}

	public Long getDamage() {
		return damage;
	}

	public void setDamage(Long damage) {
		this.damage = damage;
	}

}
