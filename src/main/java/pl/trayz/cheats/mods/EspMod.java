package pl.trayz.cheats.mods;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.Entity;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.overlay.Overlay;
import pl.trayz.cheats.utils.Utils;
import pl.trayz.cheats.utils.vec.Vec2f;
import pl.trayz.cheats.utils.vec.Vec3f;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Trayz
 **/

public class EspMod extends Modification {

    private final ConcurrentHashMap<Entity, Pane> esps = new ConcurrentHashMap<>();

    public EspMod() {
        super("Esp", "See enemies through walls");
    }

    //todo: fix this
    @Override
    public void tick() {
        LocalPlayer localPlayer = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (localPlayer == null) return;

        if(Overlay.overlay.isVisible() || Overlay.waitingForWindow) {
            if(this.esps.size() > 0) {
                Platform.runLater(() -> {
                    for (Pane pane : this.esps.values()) {
                        Overlay.pane.getChildren().remove(pane);
                    }
                    this.esps.clear();
                });
            }
            return;
        }

        TrayzWare.getInstance().getEntityManager().getEntities().forEach(entity -> {
            if (localPlayer != entity && entity.getHealth() > 0 && entity.getTeam() != localPlayer.getTeam() && !(entity instanceof LocalPlayer)) {
                drawBox(entity);
            }else if(this.esps.containsKey(entity)){
                Platform.runLater(() -> {
                    Overlay.pane.getChildren().remove(this.esps.get(entity));
                    this.esps.remove(entity);
                });
            }
        });
    }

    private void drawBox(Entity entity) {
        Vec3f bottom = entity.getPosition();
        Vec3f offset = entity.getViewOffset();
        Vec3f top = bottom.add(offset);

        Vec2f topPos = Utils.toScreen(top);
        Vec2f bottomPos = Utils.toScreen(bottom);

        float height = Math.abs(topPos.getY() - bottomPos.getY());
        float width = height;

        topPos.y -= height * 0.2f;
        height *= 1.3f;

        Pane pane = new Pane();
        Rectangle box = new Rectangle();
        Line line = new Line();
        if(this.esps.containsKey(entity)) {
            pane = esps.get(entity);
            box = (Rectangle) pane.getChildren().get(0);
            line = (Line) pane.getChildren().get(1);
        }
        box.setX(topPos.getX() - width / 1.6f + (width/2));
        box.setY(topPos.getY());
        box.setWidth(width/2);
        box.setHeight(height);
        box.setFill(Color.TRANSPARENT);
        box.setStroke(Color.WHITE);
        box.setVisible(true);

        double calculation = (0.01 * height) * entity.getHealth();
        line.setStartX(topPos.getX() - width / 1.6f + (width/2) - 5);
        line.setEndX(topPos.getX() - width / 1.6f + (width/2) - 5);
        line.setStrokeWidth(3);
        line.setStartY(topPos.getY() + height);
        line.setEndY(topPos.getY() + (height-calculation));
        line.setFill(Color.TRANSPARENT);
        line.setStroke(entity.getHealth() >= 50 ? Color.GREEN : entity.getHealth()<50 && entity.getHealth()> 25 ? Color.ORANGE : Color.RED);
        line.setVisible(true);

        if (Overlay.pane != null) {
            Pane finalPane = pane;
            Rectangle finalBox = box;
            Line finalLine = line;
            Platform.runLater(() -> {
                if(!this.esps.containsKey(entity)) {
                    if(!finalPane.getChildren().contains(finalBox)) {
                        finalPane.getChildren().add(finalBox);
                    }
                    if(!finalPane.getChildren().contains(finalLine)) {
                        finalPane.getChildren().add(finalLine);
                    }
                    if(!Overlay.pane.getChildren().contains(finalPane)) {
                        Overlay.pane.getChildren().add(finalPane);
                    }
                    this.esps.put(entity,finalPane);
                }
            });
        }
    }
}
