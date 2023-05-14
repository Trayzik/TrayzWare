package pl.trayz.cheats.mods;

import org.jnativehook.keyboard.NativeKeyEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

/**
 * @Author: Trayz
 **/

public class BhopMod extends Modification {

    public BhopMod() {
        super("Bhop","Disables or reduces flash effect");
    }

    private boolean isJumping;

    @Override
    public void tick() {
        if(!isJumping) return;

        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        if(entity.getPointer().readUnsignedInt(OffsetsManager.getNetvar("m_fFlags")) == 257)
            OffsetsManager.getOffset("dwForceJump").writeInt(6, 0);

    }

    @Override
    public void keyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) this.isJumping = true;
    }

    @Override
    public void keyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) this.isJumping = false;
    }
}
