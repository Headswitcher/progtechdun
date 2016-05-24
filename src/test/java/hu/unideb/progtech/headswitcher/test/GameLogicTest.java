package hu.unideb.progtech.headswitcher.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import hu.unideb.progtech.headswitcher.entities.HighScore;
import hu.unideb.progtech.headswitcher.game.model.Monster;
import hu.unideb.progtech.headswitcher.game.model.Room;
import hu.unideb.progtech.headswitcher.game.utility.GameLogic;
import hu.unideb.progtech.headswitcher.service.HighScoreServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;

public class GameLogicTest {

	GameLogic gl = new GameLogic();

	@After
	public void tearDown() throws Exception {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			HighScoreService hs = new HighScoreServiceImpl(entityManager);
			List<HighScore> hsl = hs.getAllHighScore();
			Long maxId = 0L;
			for (HighScore highScore : hsl) {
				if (highScore.getId() > maxId) {
					maxId = highScore.getId();
				}
			}
			entityManager.getTransaction().begin();
			hs.removeHighScoreById(maxId);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

	}

	@Test
	public void spawnMonsterHPTest() {
		gl.getActualRoom().setDepth(5L);
		Monster testMonster = gl.spawnMonster();
		Assert.assertEquals(100L, testMonster.getHealthPoint().longValue());
	}

	@Test
	public void spawnMonsteDMGTest() {
		gl.getActualRoom().setDepth(5L);
		Monster testMonster = gl.spawnMonster();
		Assert.assertEquals(7L, testMonster.getDamage().longValue());
	}

	@Test
	public void spawnMonsterGoldTest() {
		gl.getActualRoom().setDepth(5L);
		Monster testMonster = gl.spawnMonster();
		Assert.assertEquals(50L, testMonster.getGold().longValue());
	}

	@Test
	public void addGoldToPlayerFromMonsterTest() {

		gl.setActualRoom(new Room(10L, 10L, true, false, false, false, 20L, null, "monster"));
		gl.getActualRoom().setRoomMonster(gl.spawnMonster());
		gl.getPlayer().setGold(0L);
		gl.addGoldToPlayer();
		Assert.assertEquals(200L, gl.getPlayer().getGold().longValue());
	}

	@Test
	public void setHighScoreTest() {

		gl.setHighScore();

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			HighScoreService hs = new HighScoreServiceImpl(entityManager);
			List<HighScore> hsl = hs.getAllHighScore();
			Long maxId = 0L;
			for (HighScore highScore : hsl) {
				if (highScore.getId() > maxId) {
					maxId = highScore.getId();
				}
			}

			Assert.assertEquals(5L, hs.findHighScoreById(maxId).getGold().longValue());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
}
