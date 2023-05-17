package pl.trayz.cheats.overlay.controlers.visuals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/
public class FovController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private TextField fovField;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.Fov fov = this.configuration.getFov();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("fov"));
        this.fovField.setText(String.valueOf(this.fov.getFov()));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("fov");
        }else {
            this.configuration.getEnabledMods().add("fov");
        }

        this.fov.setFov(Integer.parseInt(this.fovField.getText().replaceAll("\\D", "")));
    }
}
