package pl.trayz.cheats.overlay.controlers.misc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/

public class MiscController implements Initializable {

    @FXML
    private GridPane pnItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnItems.setHgap(5);
        pnItems.setVgap(5);

        pnItems.add(getElement("Bhop"),0,0);
        pnItems.add(getElement("Radar"),1,0);
    }

    @SneakyThrows
    private AnchorPane getElement(final String name) {
        return FXMLLoader.load(getClass().getResource("/fxmls/misc/"+name+".fxml"));
    }
}
