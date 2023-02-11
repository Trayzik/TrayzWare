package pl.trayz.cheats.overlay;

import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.trayz.cheats.TrayzWare;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: Trayz
 **/

public class Overlay extends Application {

    public static Pane pane;
    public static Stage primaryStage;
    public static Parent overlay;

    public static WinDef.HWND csgoWindow;
    public static WinDef.HWND overlayWindow;

    public static boolean waitingForWindow = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        pane = new Pane();
        getHwnds();

        final WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(csgoWindow, rect);

        final int width = rect.right - rect.left;
        final int height = rect.bottom - rect.top;
        final int x = rect.left;
        final int y = rect.top;

        URL url = new File("src/main/java/pl/trayz/cheats/overlay/fxmls/Layout.fxml").toURI().toURL();
        Parent overlay = FXMLLoader.load(url);
        Scene scene = new Scene(pane, width, height);
        primaryStage.setTitle("TrayzWare");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        pane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setX(x);
        primaryStage.setY(y);
        primaryStage.setScene(scene);
        overlay.setLayoutX(width / 5f);
        overlay.setLayoutY(height / 5f);
        overlay.setVisible(false);
        pane.getChildren().add(overlay);
        primaryStage.sizeToScene();
        primaryStage.show();

        Overlay.primaryStage = primaryStage;
        Overlay.overlay = overlay;
    }

    private void getHwnds() {
        User32.INSTANCE.EnumWindows((hWnd, userData) -> {
            IntByReference processId = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(hWnd, processId);

            if(processId.getValue() == TrayzWare.getInstance().getProcess().id()) {
                csgoWindow = hWnd;
            }else if(processId.getValue() == Kernel32.INSTANCE.GetCurrentProcessId()) {
                overlayWindow = hWnd;
            }
            return !(csgoWindow != null && overlayWindow != null);
        }, null);
    }

    public static void showOverlay() {
        Platform.runLater(() -> {
            User32.INSTANCE.SetForegroundWindow(overlayWindow);

            Overlay.pane.setStyle("-fx-background-color: rgba(0, 1, 18, 0.4);");
            Overlay.overlay.setVisible(true);
        });
    }

    public static void hideOverlay() {
        Platform.runLater(() -> {
            Overlay.pane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
            Overlay.overlay.setVisible(false);
        });
    }
}
