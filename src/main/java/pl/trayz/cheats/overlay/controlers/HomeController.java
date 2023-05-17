package pl.trayz.cheats.overlay.controlers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.SneakyThrows;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText("Welcome, "+System.getProperty("user.name")+"!");
    }

    @SneakyThrows
    public void githubButton() {
        Desktop.getDesktop().browse(URI.create("https://github.com/Trayzik"));
    }

}