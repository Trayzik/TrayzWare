package pl.trayz.cheats.overlay.controlers.combat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.jnativehook.keyboard.NativeKeyEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.objects.nativehook.listeners.GlobalKeyListener;

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

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.TriggerBot triggerBot = this.configuration.getTriggerBot();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("triggerbot"));
        this.bind.setText(NativeKeyEvent.getKeyText(this.triggerBot.getBind()));
        this.delay.setText(String.valueOf(this.triggerBot.getDelay()));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("triggerbot");
        }else {
            this.configuration.getEnabledMods().add("triggerbot");
        }

        this.triggerBot.setDelay(Integer.parseInt(this.delay.getText().replaceAll("\\D", "")));
    }

    public void bindAction() {
        int keyCode = GlobalKeyListener.lastKey;

        this.bind.setText(NativeKeyEvent.getKeyText(keyCode));
        this.triggerBot.setBind(keyCode);
    }
}
