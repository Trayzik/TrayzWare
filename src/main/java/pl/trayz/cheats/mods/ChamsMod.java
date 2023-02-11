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

public class ChamsMod extends Modification {

    public ChamsMod() {
        super("Chams","See painted players");
    }

    @Override
    public void tick() {
        TrayzWare.getInstance().getEntityManager().getEntities().forEach(entity -> {
            if(!(entity.getHealth() > 0 && !entity.getTeam().equals(Team.SPEC))) return;

            LocalPlayer localPlayer = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
            if(localPlayer == null || localPlayer.equals(entity) || (!this.configuration().getChams().isChamsOnEnemy() && !localPlayer.getTeam().equals(entity.getTeam()) || (!this.configuration().getChams().isChamsOnTeammate() && localPlayer.getTeam().equals(entity.getTeam())))) return;

            Pointer pointer = entity.getPointer();
            int clrRender = OffsetsManager.getNetvar("m_clrRender");
            boolean isEnemy = !entity.getTeam().equals(localPlayer.getTeam());

            pointer.writeInt(isEnemy ? this.configuration().getChams().getEnemyColor().getRed() : this.configuration().getChams().getTeammateColor().getRed(), clrRender);
            pointer.writeInt(isEnemy ? this.configuration().getChams().getEnemyColor().getGreen() : this.configuration().getChams().getTeammateColor().getGreen(),clrRender+1);
            pointer.writeInt(isEnemy ? this.configuration().getChams().getEnemyColor().getBlue() : this.configuration().getChams().getTeammateColor().getBlue(),clrRender+2);
            pointer.writeInt(255,clrRender+3);
        });
    }
}
