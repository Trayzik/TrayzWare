package pl.trayz.cheats.overlay.controlers.visuals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.utils.ColorUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/
public class GlowController implements Initializable {

    @FXML
    private CheckBox enabled;
    @FXML
    private ColorPicker colorEnemy;
    @FXML
    private ColorPicker colorTeam;
    @FXML
    private CheckBox glowEnemy;
    @FXML
    private CheckBox glowTeam;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.Glow glow = this.configuration.getGlow();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        java.awt.Color colorEnemy = this.glow.getEnemyColor().getColor();
        java.awt.Color colorTeam = this.glow.getTeammateColor().getColor();

        this.enabled.setSelected(this.configuration.getEnabledMods().contains("glow"));
        this.colorEnemy.setValue(Color.rgb(colorEnemy.getRed(),colorEnemy.getGreen(),colorEnemy.getBlue()));
        this.colorTeam.setValue(Color.rgb(colorTeam.getRed(),colorTeam.getGreen(),colorTeam.getBlue()));
        this.glowEnemy.setSelected(this.glow.isGlowOnEnemy());
        this.glowTeam.setSelected(this.glow.isGlowOnTeammate());
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("glow");
        }else {
            this.configuration.getEnabledMods().add("glow");
        }

        this.glow.setEnemyColor(new ColorUtil(new java.awt.Color((int) (this.colorEnemy.getValue().getRed() * 255), (int) (this.colorEnemy.getValue().getGreen() * 255), (int) (this.colorEnemy.getValue().getBlue() * 255))));
        this.glow.setTeammateColor(new ColorUtil(new java.awt.Color((int) (this.colorTeam.getValue().getRed() * 255), (int) (this.colorTeam.getValue().getGreen() * 255), (int) (this.colorTeam.getValue().getBlue() * 255))));
        this.glow.setGlowOnEnemy(this.glowEnemy.isSelected());
        this.glow.setGlowOnTeammate(this.glowTeam.isSelected());
    }
}
