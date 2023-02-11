package pl.trayz.cheats.overlay.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML @Getter
    private Pane mainPane;

    @FXML
    private Pane leftBar;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnSkinChanger;

    @FXML
    private Button btnConfig;

    @FXML
    private Button btnCombat;

    public static Pane pane;


    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(location.getFile().contains("Layout.fxml")) {
            leftBar.getChildren().add(FXMLLoader.load(getClass().getResource("/fxmls/LeftBar.fxml")));
            mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml")));
            pane = mainPane;
        }
    }

    public void buttonClick(ActionEvent event) {
        if(pane == null) {
            return;
        }

        Object source = event.getSource();

        if(source.equals(btnHome)) {
            loadPage("/fxmls/Home.fxml");
        }else if(source.equals(btnSkinChanger)) {
            loadPage("/fxmls/skinchanger/SkinChanger.fxml");
        }else if(source.equals(btnConfig)) {
            loadPage("/fxmls/Configuration.fxml");
        }else if(source.equals(btnCombat)) {
            loadPage("/fxmls/combat/Combat.fxml");
        }
    }

    @SneakyThrows
    private void loadPage(String url) {
        URL fxml = new File(url).toURI().toURL();
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource(url)));
    }

}