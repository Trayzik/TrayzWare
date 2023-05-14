package pl.trayz.cheats.overlay.controlers.combat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.enums.Bone;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Aimbot aimbot = configuration.getAimbot();

        this.enabled.setSelected(configuration.getEnabledMods().contains("aimbot"));
        this.bind.setText(String.valueOf(aimbot.getBind()));
        this.delay.setText(String.valueOf(aimbot.getDelay()));
        this.bone.getItems().addAll(Bone.values());
        this.bone.getSelectionModel().select(aimbot.getBone());
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Aimbot aimbot = configuration.getAimbot();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("aimbot");
        }else {
            configuration.getEnabledMods().add("aimbot");
        }

        aimbot.setBind(Integer.parseInt(this.bind.getText().replaceAll("\\D", "")));
        aimbot.setDelay(Integer.parseInt(this.delay.getText().replaceAll("\\D", "")));
        aimbot.setBone(Bone.valueOf(this.bone.getSelectionModel().getSelectedItem().toString()));
    }
}
