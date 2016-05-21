package hu.unideb.progtech.headswitcher.service.interfaces;

import java.util.List;

import hu.unideb.progtech.headswitcher.entities.HighScore;

public interface HighScoreService {

	public void createHighScore(String username, Long gold);

	public List<HighScore> getAllHighScore();
	
	public HighScore findHighScoreById(Long Id);
	
	public void removeHighScoreById(Long Id);
}
