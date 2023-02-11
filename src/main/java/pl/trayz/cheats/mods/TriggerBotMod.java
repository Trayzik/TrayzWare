package pl.trayz.cheats.mods;

import com.sun.jna.platform.win32.Win32Exception;
import org.jnativehook.keyboard.NativeKeyEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.utils.MathHelper;
import pl.trayz.cheats.utils.vec.Vec2f;

/**
 * @Author: Trayz
 **/

public class TriggerBotMod extends Modification {

    public TriggerBotMod() {
        super("TriggerBot", "Automatically shoots when you look at an enemy");
    }

    private long lastShot = System.currentTimeMillis();
    private boolean isPressed = false;

    @Override
    public void tick() {
        try {
            if ((System.currentTimeMillis() - lastShot) < configuration().getTriggerBot().getDelay() || (configuration().getTriggerBot().getBind() != -1 && !isPressed))
                return;

            LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
            if (entity == null) return;

            int crosshairID = entity.getPointer().readInt(OffsetsManager.getNetvar("m_iCrosshairId"));
            int crosshairTeam = (int) TrayzWare.getInstance().getProcess().readUnsignedInt(OffsetsManager.getOffset("dwEntityList").readInt((int) ((crosshairID - 1) * 0x10L)) + OffsetsManager.getNetvar("m_iTeamNum"));

            if (crosshairID <= 0 || crosshairID > 64 || crosshairTeam == (entity.getTeam().ordinal() + 1)) return;

            entity.writeAttack(6);

            this.lastShot = System.currentTimeMillis();
        }catch (Win32Exception ignored) {}
    }

    @Override
    public void keyPressed(NativeKeyEvent event) {
        if(event.getKeyCode() == configuration().getTriggerBot().getBind()) this.isPressed = true;
    }

    @Override
    public void keyReleased(NativeKeyEvent event) {
        if(event.getKeyCode() == configuration().getTriggerBot().getBind()) this.isPressed = false;
    }
}
