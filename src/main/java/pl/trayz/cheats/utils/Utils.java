package pl.trayz.cheats.utils;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.objects.entity.Entity;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.overlay.Overlay;
import pl.trayz.cheats.utils.vec.*;

public class Utils {

    public static Vec2f toScreen(Vec3f vec){
        float[][] viewMatrix = new float[4][4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                viewMatrix[i][j] = OffsetsManager.getOffset("dwViewMatrix").readFloat(count * 4);
                count++;
            }
        }

        float x = viewMatrix[0][0] * vec.x + viewMatrix[0][1] * vec.z + viewMatrix[0][2] * vec.y + viewMatrix[0][3];
        float y = viewMatrix[1][0] * vec.x + viewMatrix[1][1] * vec.z + viewMatrix[1][2] * vec.y + viewMatrix[1][3];
        double w = viewMatrix[3][0] * vec.x + viewMatrix[3][1] * vec.z + viewMatrix[3][2] * vec.y + viewMatrix[3][3];

        double invw = 1.0 / w;

        x *= invw;
        y *= invw;

        int width = (int) Overlay.primaryStage.getScene().getWidth();
        int height = (int) Overlay.primaryStage.getScene().getHeight();

        int x2 = width / 2;
        int y2 = height / 2;

        x2 += 0.5 * x * width + 0.5;
        y2 -= 0.5 * y * height + 0.5;

        return new Vec2f((float)x2, (float)y2);
    }

    public static Entity closestToCrosshair(LocalPlayer localPlayer) {
        Vec2f center = new Vec2f((float) (Overlay.primaryStage.getScene().getWidth()/2), (float) (Overlay.primaryStage.getScene().getHeight()/2));

        return TrayzWare.getInstance().getEntityManager().getEntities().stream().filter(entity -> !entity.equals(localPlayer) && !entity.getTeam().equals(localPlayer.getTeam()) && entity.getHealth() > 0).min((o1, o2) -> {
            float dist1 = MathUtil.distanceBetweenPoints(center,toScreen(o1.getBonesPosition().getBodyPos()));
            float dist2 = MathUtil.distanceBetweenPoints(center,toScreen(o2.getBonesPosition().getBodyPos()));

            return Float.compare(dist1,dist2);
        }).orElse(null);
    }

}
