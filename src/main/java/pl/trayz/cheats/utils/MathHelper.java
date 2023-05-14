package pl.trayz.cheats.utils;

import pl.trayz.cheats.utils.vec.Vec2f;
import pl.trayz.cheats.utils.vec.Vec3f;

public class MathHelper {

    public static float clamp(float val, float min, float max) {
        if (val < min) return min;
        return Math.min(val, max);
    }

    public static float normalizeAngle(float a) {
        while (a > 180f) a -= 360f;
        while (a < -180f) a += 360f;
        return a;
    }

    public static float vecToAngle(Vec2f vec) {
        return (float)Math.toDegrees(Math.atan2(vec.y, vec.x));
    }

    public static float distanceBetweenPoints(Vec2f a, Vec2f b) {
        float diffX = b.x - a.x;
        float diffY = b.y - a.y;
        return (float)Math.sqrt(diffX*diffX + diffY*diffY);
    }

    public static boolean checkAngles(Vec2f angles) {
        if(angles.x > 89f || angles.x < -89f || angles.y > 360f) return false;
        return !(angles.y < -360f);
    }

    public static float differenceBetweenAngles(final float ang1, final float ang2) {
        return Math.abs(((ang1 - ang2 + 180) % 360 + 360) % 360 - 180);
    }

    public static void calcAngles(Vec3f src, Vec3f dst, Vec2f angles) {
        double[] delta = { (src.x-dst.x), (src.z-dst.z), (src.y-dst.y) };
        double hyp = Math.sqrt(delta[0]*delta[0] + delta[1]*delta[1]);

        angles.x = (float) Math.toDegrees(Math.asin(delta[2]/hyp));
        angles.y = (float) Math.toDegrees(Math.atan(delta[1]/delta[0]));

        if(delta[0] >= 0.0) {
            angles.y += 180.0f;
        }

        angles.x = MathHelper.clamp(MathHelper.normalizeAngle(angles.x), -89f, 89f);
        angles.y = MathHelper.normalizeAngle(angles.y);
    }
}
