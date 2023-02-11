package pl.trayz.cheats.overlay.controlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/

public class CombatController implements Initializable {

    @FXML
    private GridPane pnItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnItems.setHgap(5);
        pnItems.setVgap(5);

        pnItems.add(getElement("Aimbot"),0,0);
    }

    @SneakyThrows
    private AnchorPane getElement(final String name) {
        return FXMLLoader.load(getClass().getResource("/fxmls/combat/"+name+".fxml"));
    }
}
