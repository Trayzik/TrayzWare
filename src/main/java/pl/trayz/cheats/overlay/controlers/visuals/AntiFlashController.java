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

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.AntiFlash antiFlash = this.configuration.getAntiFlash();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("antiflash"));
        this.alpha.setText(String.valueOf(this.antiFlash.getAlpha()));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("antiflash");
        }else {
            this.configuration.getEnabledMods().add("antiflash");
        }

        this.antiFlash.setAlpha(Float.parseFloat(this.alpha.getText().replaceAll("\\D", "")));
    }
}
