package pl.trayz.cheats.mods;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.utils.MathUtil;
import pl.trayz.cheats.utils.vec.Vec2f;

/**
 * @Author: Trayz
 **/

public class RcsMod extends Modification {

    public RcsMod() {
        super("Rcs", "Recoil control system");
    }

    private Vec2f oldViewAngles;

    @Override
    public void tick() {
        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        if(entity.getShotsFired() < 2) {
            this.oldViewAngles = new Vec2f(0,0);
            return;
        }

        Vec2f currentAngles = entity.getViewAngles();
        Vec2f punchAngles = entity.getPunchAngles();
        Vec2f angles = new Vec2f(currentAngles.getX() - (punchAngles.getX() - oldViewAngles.getX()) * 2, currentAngles.getY() - (punchAngles.getY() - oldViewAngles.getY()) * 2);

        this.oldViewAngles = punchAngles;

        if(angles.isValid() && MathUtil.checkAngles(angles))
            entity.writeViewAngles(angles);

    }
}
