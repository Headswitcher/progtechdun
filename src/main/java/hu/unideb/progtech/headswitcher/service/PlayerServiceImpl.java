package hu.unideb.progtech.headswitcher.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.unideb.progtech.headswitcher.entities.Player;
import hu.unideb.progtech.headswitcher.service.interfaces.PlayerService;

public class PlayerServiceImpl implements PlayerService {

	EntityManager entityManager;

	public PlayerServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void createPlayer(String username, String password) {

		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		entityManager.persist(player);

	}

	@Override
	public List<Player> getAllPlayer() {
		TypedQuery<Player> query = entityManager
				.createQuery("SELECT f from hu.unideb.progtech.headswitcher.entities.Player f", Player.class);
		return query.getResultList();
	}

}
