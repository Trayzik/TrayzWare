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
public class TriggerBotController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private TextField bind;
    @FXML
    private TextField delay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.TriggerBot triggerBot = configuration.getTriggerBot();

        this.enabled.setSelected(configuration.getEnabledMods().contains("triggerbot"));
        this.bind.setText(String.valueOf(triggerBot.getBind()));
        this.delay.setText(String.valueOf(triggerBot.getDelay()));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.TriggerBot triggerBot = configuration.getTriggerBot();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("triggerbot");
        }else {
            configuration.getEnabledMods().add("triggerbot");
        }

        triggerBot.setBind(Integer.parseInt(this.bind.getText().replaceAll("\\D", "")));
        triggerBot.setDelay(Integer.parseInt(this.delay.getText().replaceAll("\\D", "")));
    }
}
