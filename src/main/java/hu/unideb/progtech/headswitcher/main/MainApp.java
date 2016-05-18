package hu.unideb.progtech.headswitcher.main;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.unideb.progtech.headswitcher.entities.Player;
import hu.unideb.progtech.headswitcher.service.PlayerServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.PlayerService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private static ObservableList<Player> PlayersData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Dun");
		initRootLayout();
		showLogin();

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showLogin() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/fxml/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static ObservableList<Player> getPlayersData() {
		return PlayersData;
	}

	public static void setPlayersData(ObservableList<Player> playersData) {
		PlayersData = playersData;
	}

	public static void updatePlayersData() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			System.out.println();
			PlayerService playerService = new PlayerServiceImpl(entityManager);
			PlayersData.clear();
			playerService.getAllPlayer().stream().forEach(l -> {
				PlayersData.add(l);
				System.out.println(l.getUsername()+ " " + l.getPassword() );
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

}