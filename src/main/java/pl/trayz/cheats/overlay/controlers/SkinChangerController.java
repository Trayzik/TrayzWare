package pl.trayz.cheats.overlay.controlers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.enums.Skins;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.enums.Weapons;
import pl.trayz.cheats.mods.SkinChangerMod;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.objects.skinchanger.SkinChangerElement;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @Author: Trayz
 **/


public class SkinChangerController implements Initializable {

    @FXML
    private VBox pnItems = null;

    @FXML
    private ComboBox teamList;
    @FXML
    private ComboBox weaponType;
    @FXML
    private ComboBox weaponName;
    @FXML
    private Button addSkin;

    @FXML
    private Button update;

    private final Configuration configuration = TrayzWare.getInstance().getConfiguration();
    private final Configuration.SkinChanger skinChanger = this.configuration.getSkinChanger();

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamList.setItems(FXCollections.observableArrayList("CT","TT","BOTH"));
        teamList.setValue("BOTH");

        weaponType.setItems(FXCollections.observableArrayList(FXCollections.observableList(Arrays.stream(Weapons.values()).sorted(Comparator.comparing(Enum::toString)).collect(Collectors.toList()))));
        weaponType.setValue("AWP");

        weaponName.setItems(FXCollections.observableList(Arrays.stream(Skins.values()).sorted(Comparator.comparing(Enum::toString)).collect(Collectors.toList())));
        weaponName.setValue("Dragon_Lore");

        //LOAD SKINS FROM CFG
        for(SkinChangerElement skins : this.skinChanger.getSkins()) {
            addToList(skins);
        }

    }

    public void buttonClick(ActionEvent event) {
        //add skin
        if(event.getSource() == addSkin) {
            Weapons type = Weapons.valueOf(weaponType.getValue().toString());
            Skins name = Skins.valueOf(weaponName.getValue().toString());

            for(SkinChangerElement skins : this.skinChanger.getSkins()) {
                if (skins.getTeam().equals(teamList.getValue()) && skins.getWeapon().equals(type) && skins.getSkin().equals(name)) {
                    return;
                }
            }

            SkinChangerElement skinChanger = new SkinChangerElement(name, type, teamList.getValue().equals("BOTH") ? Team.SPEC : Team.valueOf(teamList.getValue().toString()), true);
            this.skinChanger.getSkins().add(skinChanger);
            addToList(skinChanger);
        }else if(event.getSource() == update) {
            SkinChangerMod.updated = false;
        }
    }

    @SneakyThrows
    public void addToList(SkinChangerElement skinChanger) {
        HBox hBox = FXMLLoader.load(getClass().getResource("/fxmls/skinchanger/SkinChangerList.fxml"));
        Label weaponType = (Label) hBox.getChildren().get(1);
        weaponType.setText(skinChanger.getWeapon().name());
        Label weaponName = (Label) hBox.getChildren().get(2);
        weaponName.setText(skinChanger.getSkin().name());
        Label team = (Label) hBox.getChildren().get(3);
        team.setText(skinChanger.getTeam().name());
        CheckBox enabled = (CheckBox) hBox.getChildren().get(4);
        enabled.setSelected(skinChanger.isEnabled());
        hBox.setOnMouseEntered(ee -> {
            hBox.setStyle("-fx-background-color : #0A0E3F");
        });
        hBox.setOnMouseExited(ee -> {
            hBox.setStyle("-fx-background-color : #02030A");
        });
        pnItems.getChildren().add(hBox);
    }
}
