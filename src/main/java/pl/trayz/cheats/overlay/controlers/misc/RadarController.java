package pl.trayz.cheats.overlay.controlers.misc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/

public class RadarController implements Initializable {

    @FXML
    private CheckBox enabled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();

        this.enabled.setSelected(configuration.getEnabledMods().contains("radar"));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("radar");
        }else {
            configuration.getEnabledMods().add("radar");
        }
    }
}
