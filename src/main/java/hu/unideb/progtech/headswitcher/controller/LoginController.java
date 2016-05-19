package hu.unideb.progtech.headswitcher.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.unideb.progtech.headswitcher.main.MainApp;
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

public class LoginController implements Initializable {

	private MainApp mainApp;

	@FXML
	Button registerButton;

	@FXML
	private Button loginButton;

	@FXML
	private TextField userName;

	@FXML
	private PasswordField password;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MainApp.updatePlayersData();
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleRegister(ActionEvent event) {
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/menu/Register.fxml"));
			root = loader.load();
			loader.<RegisterController> getController();
			stage = (Stage) registerButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleLogin(ActionEvent event) {

		String userName;
		String password;
		if (this.userName.getText() != null)
			userName = this.userName.getText();
		else
			userName = " ";

		if (this.password.getText() != null)
			password = this.password.getText();
		else
			password = " ";
		System.out.println("stream:");
		System.out.println(userName);
		System.out.println(password);

		if (MainApp.getPlayersData().stream()
				.filter(l -> ((userName.equalsIgnoreCase(l.getUsername())) && (password.equals(l.getPassword()))))
				.count() > 0)
			goMainMenu(event);
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Hibás felhasználónév vagy jelszó");
			alert.showAndWait();
		}
	}

	private void goMainMenu(ActionEvent event) {
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/menu/MainMenu.fxml"));
			root = loader.load();
			loader.<MainMenuController> getController();
			stage = (Stage) loginButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TextField getUserName() {
		return userName;
	}

	public void setUserName(TextField userName) {
		this.userName = userName;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

}
