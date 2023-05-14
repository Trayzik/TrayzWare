package pl.trayz.cheats.mods;

import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.LocalPlayer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

import com.github.jonatino.process.Process;
import pl.trayz.cheats.objects.skinchanger.SkinChangerElement;

/**
 * @Author: Trayz
 **/

/*
 Detected by VAC
 DON"T USE IT!!!!!
 */
public class SkinChangerMod extends Modification {

    public SkinChangerMod() {
        super("SkinChanger","Get skins for your weapons");
    }

    public static boolean updated;

    @Override
    public void tick() {
        LocalPlayer entity = TrayzWare.getInstance().getEntityManager().getLocalPlayer();
        if (entity == null) return;

        Process process = TrayzWare.getInstance().getProcess();

        for(int i = 0; i <= 8; i++){
            int myWeapons = entity.getPointer().readInt(OffsetsManager.getNetvar("m_hMyWeapons") + i * 0x4) & 0xFFF;
            int currentWeapon = OffsetsManager.getOffset("dwEntityList").readInt((myWeapons - 1) * 0x10);

            if(currentWeapon == 0) continue;

            short weaponID = (short)process.readShort(currentWeapon + OffsetsManager.getNetvar("m_iItemDefinitionIndex"));

            for(SkinChangerElement skins : configuration().getSkinChanger().getSkins()) {
                if (skins.getWeapon().getId() == weaponID && (skins.getTeam().equals(Team.SPEC) || skins.getTeam().equals(entity.getTeam())) && skins.isEnabled()) {
                    process.writeInt(currentWeapon + OffsetsManager.getNetvar("m_iAccountID"), process.readInt(currentWeapon+OffsetsManager.getNetvar("m_OriginalOwnerXuidLow")));
                    process.writeInt(currentWeapon + OffsetsManager.getNetvar("m_nFallbackPaintKit"), skins.getSkin().getId());
                    process.writeInt(currentWeapon + OffsetsManager.getNetvar("m_iItemIDHigh"), -1);
                    process.writeInt(currentWeapon + OffsetsManager.getNetvar("m_nFallbackSeed"), 0);
                    process.writeInt(currentWeapon + OffsetsManager.getNetvar("m_iEntityQuality"), 1);
                    process.writeFloat(currentWeapon + OffsetsManager.getNetvar("m_flFallbackWear"), 0.01f);
                }
            }
        }

        if(!updated) {
            TrayzWare.getInstance().getProcess().writeInt(OffsetsManager.getOffset("dwClientState").getPointer(0).getAddress()+0x174,-1);
            updated = true;
        }



    }


}
