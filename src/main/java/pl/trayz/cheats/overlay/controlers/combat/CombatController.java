package pl.trayz.cheats.overlay.controlers.combat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

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
        pnItems.add(getElement("TriggerBot"),1,0);
        pnItems.add(getElement("RCS"),2,0);
    }

    @SneakyThrows
    private AnchorPane getElement(final String name) {
        return FXMLLoader.load(getClass().getResource("/fxmls/combat/"+name+".fxml"));
    }
}
