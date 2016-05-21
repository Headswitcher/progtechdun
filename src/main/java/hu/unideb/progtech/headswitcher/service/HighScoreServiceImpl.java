package hu.unideb.progtech.headswitcher.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.unideb.progtech.headswitcher.entities.HighScore;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;

public class HighScoreServiceImpl implements HighScoreService {

	EntityManager entityManager;

	public HighScoreServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void createHighScore(String username, Long gold) {

		HighScore highScore = new HighScore();
		highScore.setUsername(username);
		highScore.setGold(gold);

		entityManager.persist(highScore);

	}

	@Override
	public List<HighScore> getAllHighScore() {
		TypedQuery<HighScore> query = entityManager
				.createQuery("SELECT f from hu.unideb.progtech.headswitcher.entities.HighScore f", HighScore.class);
		return query.getResultList();
	}

	@Override
	public HighScore findHighScoreById(Long Id) {
		return entityManager.find(HighScore.class, Id);

	}

	@Override
	public void removeHighScoreById(Long Id) {
		HighScore hs = findHighScoreById(Id);
		if (hs != null) {
			entityManager.remove(hs);
		}

	}

}
