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
import pl.trayz.cheats.enums.Skins;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.enums.Weapons;
import pl.trayz.cheats.mods.SkinChangerMod;
import pl.trayz.cheats.objects.entity.Entity;
import pl.trayz.cheats.objects.skinchanger.SkinChangerElement;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @Author: Trayz
 **/


public class PlayersController implements Initializable {

    @FXML
    private VBox pnItems;
    @FXML
    private Label totalPlayers;
    @FXML
    private Label ctPlayers;
    @FXML
    private Label ttPlayers;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonClick();
    }

    public void buttonClick() {
        pnItems.getChildren().clear();
        this.totalPlayers.setText(String.valueOf(TrayzWare.getInstance().getEntityManager().getEntities().size()));
        this.ctPlayers.setText(String.valueOf(TrayzWare.getInstance().getEntityManager().getEntities().stream().filter(entity -> entity.getTeam() == Team.CT).count()));
        this.ttPlayers.setText(String.valueOf(TrayzWare.getInstance().getEntityManager().getEntities().stream().filter(entity -> entity.getTeam() == Team.TT).count()));

        for(Entity entities : TrayzWare.getInstance().getEntityManager().getEntities()) {
            addToList(entities);
        }
    }

    @SneakyThrows
    public void addToList(Entity entity) {
        HBox hBox = FXMLLoader.load(getClass().getResource("/fxmls/players/PlayersList.fxml"));
        Label name = (Label) hBox.getChildren().get(1);
        name.setText(entity.getName());
        Label team = (Label) hBox.getChildren().get(2);
        team.setText(entity.getTeam().name());
        Label rank = (Label) hBox.getChildren().get(3);
        rank.setText(entity.getRank().getName());
        Label hp = (Label) hBox.getChildren().get(4);
        hp.setText(entity.getHealth() <= 0 ? "\uD83D\uDC80" : entity.getHealth()+"hp");
        hBox.setOnMouseEntered(ee -> {
            hBox.setStyle("-fx-background-color : #0A0E3F");
        });
        hBox.setOnMouseExited(ee -> {
            hBox.setStyle("-fx-background-color : #02030A");
        });
        pnItems.getChildren().add(hBox);
    }
}
