package hu.unideb.progtech.headswitcher.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "High_Score")
public class HighScore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "gold", nullable = false)
	private Long gold;

	@Temporal(TemporalType.TIMESTAMP)
	Date dateOfTheGame;

	@PrePersist
	public void prePersist() {
		dateOfTheGame = new Date();
	}

	public HighScore() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public Date getDateOfTheGame() {
		return dateOfTheGame;
	}

	public void setDateOfTheGame(Date dateOfTheGame) {
		this.dateOfTheGame = dateOfTheGame;
	}

}
