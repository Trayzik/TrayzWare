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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Esp esp = configuration.getEsp();

        java.awt.Color color = esp.getBorderColor().getColor();

        this.enabled.setSelected(configuration.getEnabledMods().contains("esp"));
        this.color.setValue(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        this.showHP.setSelected(esp.isShowHp());
        this.hpSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, esp.getHpBarSize()));
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Esp esp = configuration.getEsp();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("esp");
        }else {
            configuration.getEnabledMods().add("esp");
        }

        esp.setBorderColor(new ColorUtil(new java.awt.Color((int) (this.color.getValue().getRed() * 255), (int) (this.color.getValue().getGreen() * 255), (int) (this.color.getValue().getBlue() * 255))));
        esp.setShowHp(this.showHP.isSelected());
        esp.setHpBarSize((int) this.hpSize.getValue());
    }
}
