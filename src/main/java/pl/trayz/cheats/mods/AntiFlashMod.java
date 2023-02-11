package pl.trayz.cheats.mods;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

/**
 * @Author: Trayz
 **/

public class AntiFlashMod extends Modification {

    public AntiFlashMod() {
        super("AntiFlash","Disables or reduces flash effect");
    }

    @Override
    public void tick() {
        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        if(entity.getPointer().readFloat(OffsetsManager.getNetvar("m_flFlashMaxAlpha")) > this.configuration().getAntiFlash().getAlpha())
            entity.getPointer().writeFloat(this.configuration().getAntiFlash().getAlpha(),OffsetsManager.getNetvar("m_flFlashMaxAlpha"));
    }
}
