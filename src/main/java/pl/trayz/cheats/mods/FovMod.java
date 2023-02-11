package pl.trayz.cheats.mods;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

/**
 * @Author: Trayz
 **/

public class FovMod extends Modification {

    private int fov = 0;

    public FovMod() {
        super("Fov","changes your fov");
    }

    @Override
    public void tick() {
        if(fov == configuration().getFov().getFov()) return;

        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        this.fov = configuration().getFov().getFov();

        entity.getPointer().writeInt(this.fov, OffsetsManager.getNetvar("m_iDefaultFOV"));
    }
}
