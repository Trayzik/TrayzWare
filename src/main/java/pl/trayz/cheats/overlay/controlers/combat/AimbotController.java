package pl.trayz.cheats.overlay.controlers.combat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.jnativehook.keyboard.NativeKeyEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.enums.Bone;
import pl.trayz.cheats.objects.nativehook.listeners.GlobalKeyListener;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/
public class AimbotController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private TextField bind;
    @FXML
    private TextField delay;
    @FXML
    private ComboBox bone;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.Aimbot aimbot = this.configuration.getAimbot();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.enabled.setSelected(this.configuration.getEnabledMods().contains("aimbot"));
        this.bind.setText(String.valueOf(this.aimbot.getBind()));
        this.bind.setText(NativeKeyEvent.getKeyText(this.aimbot.getBind()));
        this.delay.setText(String.valueOf(this.aimbot.getDelay()));
        this.bone.getItems().addAll(Bone.values());
        this.bone.getSelectionModel().select(this.aimbot.getBone());
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("aimbot");
        }else {
            this.configuration.getEnabledMods().add("aimbot");
        }

        this.aimbot.setDelay(Integer.parseInt(this.delay.getText().replaceAll("\\D", "")));
        this.aimbot.setBone(Bone.valueOf(this.bone.getSelectionModel().getSelectedItem().toString()));
    }

    public void bindAction() {
        int keyCode = GlobalKeyListener.lastKey;

        this.bind.setText(NativeKeyEvent.getKeyText(keyCode));
        this.aimbot.setBind(keyCode);
    }
}
