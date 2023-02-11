package pl.trayz.cheats.mods;

import com.sun.jna.platform.win32.Win32Exception;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

/**
 * @Author: Trayz
 **/

public class RadarMod extends Modification {

    public RadarMod() {
        super("Radar","Shows all players on radar");
    }

    @Override
    public void tick() {
        TrayzWare.getInstance().getEntityManager().getEntities().forEach(entity -> {
            entity.getPointer().writeBoolean(true, OffsetsManager.getNetvar("m_bSpotted"));
        });
    }
}
