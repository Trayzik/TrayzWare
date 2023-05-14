package pl.trayz.cheats.overlay.controlers.visuals;

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

public class VisualsController implements Initializable {

    @FXML
    private GridPane pnItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pnItems.setHgap(5);
        pnItems.setVgap(5);

        pnItems.add(getElement("Glow"),0,0);
        pnItems.add(getElement("Chams"),1,0);
        pnItems.add(getElement("AntiFlash"),2,0);
        pnItems.add(getElement("Esp"),0,1);
        pnItems.add(getElement("Fov"),1,1);
    }

    @SneakyThrows
    private AnchorPane getElement(final String name) {
        return FXMLLoader.load(getClass().getResource("/fxmls/visuals/"+name+".fxml"));
    }
}
