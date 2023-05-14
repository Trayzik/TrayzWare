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
public class AntiFlashController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private TextField alpha;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.AntiFlash antiFlash = configuration.getAntiFlash();

        this.enabled.setSelected(configuration.getEnabledMods().contains("antiflash"));
        this.alpha.setText(String.valueOf(antiFlash.getAlpha()));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.AntiFlash antiFlash = configuration.getAntiFlash();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("antiflash");
        }else {
            configuration.getEnabledMods().add("antiflash");
        }

        antiFlash.setAlpha(Float.parseFloat(this.alpha.getText().replaceAll("\\D", "")));
    }
}
