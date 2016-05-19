package hu.unideb.progtech.headswitcher.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.unideb.progtech.headswitcher.main.MainApp;
import hu.unideb.progtech.headswitcher.service.PlayerServiceImpl;
import hu.unideb.progtech.headswitcher.service.interfaces.PlayerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

	@FXML
	private TextField userName;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField confirmPassword;

	@FXML
	private Button registerButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private boolean isItNotUniqueUsername() {
		return MainApp.getPlayersData().stream().anyMatch(l -> l.getUsername().equals(userName.getText()));
	}

	private boolean isItConfirmedPassword() {
		return password.getText().equals(confirmPassword.getText());
	}

	private boolean isRegisterFieldsNotNulls() {
		if (confirmPassword.getText().isEmpty() || password.getText().isEmpty() || userName.getText().isEmpty())
			return false;
		else
			return true;
	}

	@FXML
	private void handleRegister(ActionEvent event) {
		try {
			if (isRegisterFieldsNotNulls() && isItConfirmedPassword() && !isItNotUniqueUsername()) {
				registerUser();
				MainApp.updatePlayersData();
			} else {
				if (!isRegisterFieldsNotNulls()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("HIBA");
					alert.setHeaderText("Nem töltötted ki a mezőket!");
					alert.showAndWait();
				} else if (!isItConfirmedPassword()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("HIBA");
					alert.setHeaderText("A jelszavak nem egyeztek meg!");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("HIBA");
					alert.setHeaderText("Ez a felhasználónév már foglalt!");
					alert.showAndWait();
				}
				Exception e = new Exception();
				throw e;
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Siker");
			alert.setHeaderText("Sikeresen regisztráltál a rendszerbe!");
			alert.showAndWait();

			Stage stage;
			Parent root;

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/menu/Login.fxml"));
			root = loader.load();
			loader.<LoginController> getController();
			stage = (Stage) registerButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.out.println("TodoLogg");
			//Loggolok
			
		}
	}

	private void registerUser() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oracle");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			
			PlayerService playerService = new PlayerServiceImpl(entityManager);
			entityManager.getTransaction().begin();
			playerService.createPlayer(userName.getText(), password.getText());
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

}
