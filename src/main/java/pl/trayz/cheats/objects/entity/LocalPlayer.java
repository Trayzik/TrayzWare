package pl.trayz.cheats.objects.entity;

import pl.trayz.cheats.objects.memory.Pointer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.utils.vec.Vec2f;

/**
 * @Author: Trayz
 **/

public class LocalPlayer extends Entity {

    public LocalPlayer(long address,int entityID) {
        super(address,entityID);
    }

    public void writeAttack(int mode) {
        OffsetsManager.getOffset("dwForceAttack").writeInt(mode,0);
    }

    public void writeViewAngles(Vec2f angels) {
        Pointer clientState = OffsetsManager.getOffset("dwClientState").getPointer(0);
        int viewAngleOffset = OffsetsManager.getOffset("dwClientState_ViewAngles").getOffset();

        clientState.writeFloat(angels.x, viewAngleOffset);
        clientState.writeFloat(angels.y, viewAngleOffset + 0x4);
    }

    public long getShotsFired() {
        return this.getPointer().readUnsignedInt(OffsetsManager.getNetvar("m_iShotsFired"));
    }

    public Vec2f getPunchAngles() {
        int netvar = OffsetsManager.getNetvar("m_aimPunchAngle");

        return new Vec2f(this.getPointer().readFloat(netvar), this.getPointer().readFloat(netvar + 0x4));
    }
}
