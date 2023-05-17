package pl.trayz.cheats.mods;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.memory.Pointer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

/**
 * @Author: Trayz
 **/

public class GlowMod extends Modification {

    public GlowMod() {
        super("Glow","See glow on players through walls");
    }

    @Override
    public void tick() {
        TrayzWare.getInstance().getEntityManager().getEntities().forEach(entity -> {
            if(entity.getTeam() == null || !(entity.getHealth() > 0 && !entity.getTeam().equals(Team.SPEC)) || entity.getPosition() == null) return;

            LocalPlayer localPlayer = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
            if(localPlayer == null || localPlayer.getPosition() == null || localPlayer.equals(entity) || (!this.configuration().getGlow().isGlowOnEnemy() && !localPlayer.getTeam().equals(entity.getTeam()) || (!this.configuration().getGlow().isGlowOnTeammate() && localPlayer.getTeam().equals(entity.getTeam())))) return;

            Pointer glowObj = OffsetsManager.getOffset("dwGlowObjectManager").getPointer(0);
            int glowIndex = (int) entity.getPointer().readUnsignedInt(OffsetsManager.getNetvar("m_iGlowIndex"));
            boolean isEnemy = !entity.getTeam().equals(localPlayer.getTeam());

            glowObj.writeFloat(isEnemy ? this.configuration().getGlow().getEnemyColor().getRed() : this.configuration().getGlow().getTeammateColor().getRed(), (glowIndex * 0x38) + 0x8);
            glowObj.writeFloat(isEnemy ? this.configuration().getGlow().getEnemyColor().getGreen() : this.configuration().getGlow().getTeammateColor().getGreen(), (glowIndex * 0x38) + 0xC);
            glowObj.writeFloat(0f, (glowIndex * 0x38) + 0x10);
            glowObj.writeFloat(1f, (glowIndex * 0x38) + 0x14);

            glowObj.writeBoolean(true, (glowIndex * 0x38) + 0x28);
        });
    }
}
