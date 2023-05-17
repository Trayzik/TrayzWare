package pl.trayz.cheats.overlay.controlers.visuals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.utils.ColorUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/

public class EspController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private ColorPicker color;
    @FXML
    private CheckBox showHP;
    @FXML
    private Spinner hpSize;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.Esp esp = this.configuration.getEsp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        java.awt.Color color = this.esp.getBorderColor().getColor();

        this.enabled.setSelected(this.configuration.getEnabledMods().contains("esp"));
        this.color.setValue(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        this.showHP.setSelected(this.esp.isShowHp());
        this.hpSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, this.esp.getHpBarSize()));
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("esp");
        }else {
            this.configuration.getEnabledMods().add("esp");
        }

        this.esp.setBorderColor(new ColorUtil(new java.awt.Color((int) (this.color.getValue().getRed() * 255), (int) (this.color.getValue().getGreen() * 255), (int) (this.color.getValue().getBlue() * 255))));
        this.esp.setShowHp(this.showHP.isSelected());
        this.esp.setHpBarSize((int) this.hpSize.getValue());
    }
}
