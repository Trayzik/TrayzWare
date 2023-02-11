package pl.trayz.cheats.mods;

import org.jnativehook.keyboard.NativeKeyEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.enums.Bone;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.BonesPosition;
import pl.trayz.cheats.objects.entity.Entity;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.memory.Pointer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.utils.MathHelper;
import pl.trayz.cheats.utils.Utils;
import pl.trayz.cheats.utils.vec.Vec2f;
import pl.trayz.cheats.utils.vec.Vec3f;

/**
 * @Author: Trayz
 **/

public class AimbotMod extends Modification {

    public AimbotMod() {
        super("Aimbot", "Automatically aims at the enemy");
    }

    private boolean isPressed = false;
    private boolean findNewTarget;

    private long lastCheck = System.currentTimeMillis();

    private Entity target;

    @Override
    public void tick() {
        if ((System.currentTimeMillis() - lastCheck) < configuration().getAimbot().getDelay() || !isPressed) return;

        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        if (findNewTarget) {
            this.target = Utils.closestToCrosshair(entity);
            this.findNewTarget = false;
        }

        if (this.target == null || target.getHealth() <= 0) return;

        Bone bone = configuration().getAimbot().getBone();
        BonesPosition bonesPosition = this.target.getBonesPosition();

        Vec2f currentAngles = new Vec2f(entity.getViewAngles().getX(), entity.getViewAngles().getY());
        Vec2f angles = new Vec2f(0, 0);
        Vec3f myPos = entity.getPosition().add(entity.getViewOffset());
        Vec3f targetPos = bone.equals(Bone.HEAD) ? bonesPosition.getHeadPos() : bone.equals(Bone.NECK) ? bonesPosition.getNeckPos() : bone.equals(Bone.CHEST) ? bonesPosition.getBodyPos() : bone.equals(Bone.PELVIS) ? bonesPosition.getPelvisPos() : bonesPosition.getLegsPos();
        MathHelper.calcAngles(myPos, targetPos, angles);

        float diffYaw = MathHelper.differenceBetweenAngles(currentAngles.x, angles.x);
        float diffPitch = MathHelper.differenceBetweenAngles(currentAngles.y, angles.y);
        float distanceFromCrosshair = (float)Math.sqrt(diffYaw*diffYaw + diffPitch*diffPitch);

        if (angles.isValid() && distanceFromCrosshair < configuration().getAimbot().getFov())
            entity.writeViewAngles(angles);

        this.lastCheck = System.currentTimeMillis();

    }

    @Override
    public void keyPressed(NativeKeyEvent event) {
        if(event.getKeyCode() == configuration().getAimbot().getBind()) {
            this.isPressed = true;
            this.findNewTarget = true;
        }
    }

    @Override
    public void keyReleased(NativeKeyEvent event) {
        if(event.getKeyCode() == configuration().getAimbot().getBind()) {
            this.isPressed = false;
            this.findNewTarget = false;
        }
    }

}
