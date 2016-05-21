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

	private static ObservableList<Player> playersData = FXCollections.observableArrayList();
	private static String activeUser;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Dun");
		this.primaryStage.setResizable(false);
		initRootLayout();
		showLogin();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/fxml/root/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void showLogin() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/fxml/menu/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static ObservableList<Player> getPlayersData() {
		return playersData;
	}

	public static void setPlayersData(ObservableList<Player> playersData) {
		MainApp.playersData = playersData;
	}

	public static String getActiveUser() {
		return activeUser;
	}

	public static void setActiveUser(String activeUser) {
		MainApp.activeUser = activeUser;
	}

	public static void updatePlayersData() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			System.out.println();
			PlayerService playerService = new PlayerServiceImpl(entityManager);
			playersData.clear();
			playerService.getAllPlayer().stream().forEach(l -> {
				playersData.add(l);
				System.out.println(l.getUsername() + " " + l.getPassword());
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

}