package hu.unideb.progtech.headswitcher.game.utility;

public class Adventurer {

	private Long name;
	private Long healthPoint;
	private Long experience;
	private Long damage;

	public Adventurer(Long healthPoint, Long experience, Long damage) {
		super();
		this.healthPoint = healthPoint;
		this.experience = experience;
		this.damage = damage;
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Long getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(Long healthPoint) {
		this.healthPoint = healthPoint;
	}

	public Long getExperience() {
		return experience;
	}

	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public Long getDamage() {
		return damage;
	}

	public void setDamage(Long damage) {
		this.damage = damage;
	}

}
