package pl.trayz.cheats.overlay.controlers.combat;

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
public class RCSController implements Initializable {

    @FXML
    private CheckBox enabled;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("rcs"));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("rcs");
        }else {
            this.configuration.getEnabledMods().add("rcs");
        }
    }
}
