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

public class ChamsController implements Initializable {

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
    private final Configuration.Chams chams = this.configuration.getChams();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        java.awt.Color colorEnemy = this.chams.getEnemyColor().getColor();
        java.awt.Color colorTeam = this.chams.getTeammateColor().getColor();

        this.enabled.setSelected(this.configuration.getEnabledMods().contains("glow"));
        this.colorEnemy.setValue(Color.rgb(colorEnemy.getRed(),colorEnemy.getGreen(),colorEnemy.getBlue()));
        this.colorTeam.setValue(Color.rgb(colorTeam.getRed(),colorTeam.getGreen(),colorTeam.getBlue()));
        this.glowEnemy.setSelected(this.chams.isChamsOnEnemy());
        this.glowTeam.setSelected(this.chams.isChamsOnTeammate());
    }

    public void onAction() {
        if(!this.enabled.isSelected()) {
            this.configuration.getEnabledMods().remove("chams");
        }else {
            this.configuration.getEnabledMods().add("chams");
        }

        this.chams.setEnemyColor(new ColorUtil(new java.awt.Color((int) (this.colorEnemy.getValue().getRed() * 255), (int) (this.colorEnemy.getValue().getGreen() * 255), (int) (this.colorEnemy.getValue().getBlue() * 255))));
        this.chams.setTeammateColor(new ColorUtil(new java.awt.Color((int) (this.colorTeam.getValue().getRed() * 255), (int) (this.colorTeam.getValue().getGreen() * 255), (int) (this.colorTeam.getValue().getBlue() * 255))));
        this.chams.setChamsOnEnemy(this.glowEnemy.isSelected());
        this.chams.setChamsOnTeammate(this.glowTeam.isSelected());
    }
}
