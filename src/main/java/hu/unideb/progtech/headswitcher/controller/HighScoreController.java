package hu.unideb.progtech.headswitcher.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.unideb.progtech.headswitcher.entities.HighScore;
import hu.unideb.progtech.headswitcher.service.HighScoreServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.HighScoreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HighScoreController implements Initializable {

	private ObservableList<HighScore> obserableHSList = FXCollections.observableArrayList();

	@FXML
	private Button backButton;

	@FXML
	private TableView<HighScore> highScoreTable;

	@FXML
	private TableColumn<HighScore, String> usernameCol;
	@FXML
	private TableColumn<HighScore, Date> dateCol;
	@FXML
	private TableColumn<HighScore, String> goldCol;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<HighScore> hslist = new LinkedList<>();
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			HighScoreService hs = new HighScoreServiceImpl(entityManager);
			entityManager.getTransaction().begin();
			hslist = hs.getAllHighScore();
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		for (HighScore highScore : hslist)
			obserableHSList.add(highScore);

		usernameCol.setCellValueFactory(new PropertyValueFactory<HighScore, String>("username"));

		goldCol.setCellValueFactory(new PropertyValueFactory<HighScore, String>("gold"));

		dateCol.setCellValueFactory(new PropertyValueFactory<HighScore, Date>("dateOfTheGame"));

		highScoreTable.setItems(obserableHSList);

		goldCol.setComparator(goldCol.getComparator().reversed());
		highScoreTable.getSortOrder().add(goldCol);

	}

	@FXML
	private void handleBack(ActionEvent event) {
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/menu/MainMenu.fxml"));
			root = loader.load();
			loader.<MainMenuController> getController();
			stage = (Stage) backButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
