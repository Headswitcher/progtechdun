package hu.unideb.progtech.headswitcher.service.interfaces;

import java.util.List;

import hu.unideb.progtech.headswitcher.entities.Player;

public interface PlayerService {

	public void createPlayer(String username, String password);

	public List<Player> getAllPlayer();
}
