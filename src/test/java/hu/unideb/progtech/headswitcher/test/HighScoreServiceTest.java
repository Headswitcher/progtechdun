package hu.unideb.progtech.headswitcher.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.progtech.headswitcher.entities.HighScore;
import hu.unideb.progtech.headswitcher.service.HighScoreServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;

public class HighScoreServiceTest {

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

			HighScoreService hs = new HighScoreServiceImpl(entityManager);
			entityManager.getTransaction().begin();
			hs.createHighScore("Unit", 500L);
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
	public void findHighScoreById() {
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
			assertEquals(hs.findHighScoreById(maxId).getUsername(), "Unit");
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

}
