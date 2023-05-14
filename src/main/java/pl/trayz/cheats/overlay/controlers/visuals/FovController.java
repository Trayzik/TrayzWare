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
    private TextField fov;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Fov fov = configuration.getFov();

        this.enabled.setSelected(configuration.getEnabledMods().contains("fov"));
        this.fov.setText(String.valueOf(fov.getFov()));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Fov fov = configuration.getFov();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("fov");
        }else {
            configuration.getEnabledMods().add("fov");
        }

        fov.setFov(Integer.parseInt(this.fov.getText().replaceAll("\\D", "")));
    }
}
