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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Glow glow = configuration.getGlow();

        java.awt.Color colorEnemy = glow.getEnemyColor().getColor();
        java.awt.Color colorTeam = glow.getTeammateColor().getColor();

        this.enabled.setSelected(configuration.getEnabledMods().contains("glow"));
        this.colorEnemy.setValue(Color.rgb(colorEnemy.getRed(),colorEnemy.getGreen(),colorEnemy.getBlue()));
        this.colorTeam.setValue(Color.rgb(colorTeam.getRed(),colorTeam.getGreen(),colorTeam.getBlue()));
        this.glowEnemy.setSelected(glow.isGlowOnEnemy());
        this.glowTeam.setSelected(glow.isGlowOnTeammate());
    }

    public void onAction() {
        Configuration configuration = TrayzWare.getInstance().getConfiguration();
        Configuration.Glow glow = configuration.getGlow();

        if(!this.enabled.isSelected()) {
            configuration.getEnabledMods().remove("glow");
        }else {
            configuration.getEnabledMods().add("glow");
        }

        glow.setEnemyColor(new ColorUtil(new java.awt.Color((int) (this.colorEnemy.getValue().getRed() * 255), (int) (this.colorEnemy.getValue().getGreen() * 255), (int) (this.colorEnemy.getValue().getBlue() * 255))));
        glow.setTeammateColor(new ColorUtil(new java.awt.Color((int) (this.colorTeam.getValue().getRed() * 255), (int) (this.colorTeam.getValue().getGreen() * 255), (int) (this.colorTeam.getValue().getBlue() * 255))));
        glow.setGlowOnEnemy(this.glowEnemy.isSelected());
        glow.setGlowOnTeammate(this.glowTeam.isSelected());
    }
}
