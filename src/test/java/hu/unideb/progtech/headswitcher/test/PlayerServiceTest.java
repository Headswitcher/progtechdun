package hu.unideb.progtech.headswitcher.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.progtech.headswitcher.entities.Player;
import hu.unideb.progtech.headswitcher.service.PlayerServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.PlayerService;

public class PlayerServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			PlayerService playerService = new PlayerServiceImpl(entityManager);
			entityManager.getTransaction().begin();
			playerService.createPlayer("UnitTest", "tesztunithoz");
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

	@After
	public void tearDown() throws Exception {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			PlayerService playerService = new PlayerServiceImpl(entityManager);
			List<Player> plist = playerService.getAllPlayer();
			Long maxId = 0L;
			for (Player player : plist) {
				if (player.getId() > maxId)
					maxId = player.getId();
			}
			entityManager.getTransaction().begin();
			playerService.removePlayerById(maxId);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

	@Test
	public void findById() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			PlayerService playerService = new PlayerServiceImpl(entityManager);
			List<Player> plist = playerService.getAllPlayer();
			Long maxId = 0L;
			for (Player player : plist) {
				maxId = player.getId();
			}

			Assert.assertEquals(playerService.findPlayerById(maxId).getUsername(), "UnitTest");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

	}

}
