package pl.trayz.cheats.overlay.controlers.combat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.enums.Bone;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/
public class RCSController implements Initializable {

    @FXML
    private CheckBox enabled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();

        this.enabled.setSelected(configuration.getEnabledMods().contains("rcs"));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("rcs");
        }else {
            configuration.getEnabledMods().add("rcs");
        }
    }
}
