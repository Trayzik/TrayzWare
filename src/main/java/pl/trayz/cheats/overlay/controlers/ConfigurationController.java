package pl.trayz.cheats.overlay.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.overlay.Overlay;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: Trayz
 **/

public class ConfigurationController implements Initializable {

    @FXML
    private Button loadConfig;
    @FXML
    private Button saveConfig;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void buttonClick(ActionEvent event) {
        if(event.getSource().equals(loadConfig)) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select config to load");

            File file = fileChooser.showOpenDialog(Overlay.primaryStage);
            if(file != null && file.getName().endsWith(".json")) {
                TrayzWare.getInstance().getConfiguration().load(file);
            }
        } else if(event.getSource().equals(saveConfig)) {
            final DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Select directory to save");

            File file = fileChooser.showDialog(Overlay.primaryStage);
            if(file != null) {
                Overlay.hideOverlay();

                TextInputDialog td = new TextInputDialog("Enter config name");
                td.setResizable(false);
                td.setGraphic(null);
                td.showAndWait().ifPresentOrElse(s -> {
                    TrayzWare.getInstance().getConfiguration().save(new File(file.getPath()+"/"+s+".json"));
                    Overlay.showOverlay();
                }, Overlay::showOverlay);
            }
        }
    }

}
