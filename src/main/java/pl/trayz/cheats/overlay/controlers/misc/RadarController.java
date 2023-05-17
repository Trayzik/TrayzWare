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

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("radar"));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("radar");
        }else {
            this.configuration.getEnabledMods().add("radar");
        }
    }
}
