package pl.trayz.cheats.objects.entity;

import lombok.Getter;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.mods.SkinChangerMod;
import pl.trayz.cheats.objects.offsets.OffsetsManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Trayz
 **/

@Getter
public class EntityManager {

    private final List<Entity> entities;

    public EntityManager() {
        this.entities = new CopyOnWriteArrayList<>();
    }

    public void updateEntities() {
        long localPlayerBase = OffsetsManager.getOffset("dwLocalPlayer").readUnsignedInt(0);
        if (localPlayerBase == 0 || this.isEntityTeamValid(localPlayerBase)) return;

        for (int i = 0; i < 32; i++) {
            long entityBase = OffsetsManager.getOffset("dwEntityList").readUnsignedInt(i * 0x10);

            if (entityBase == 0 || entities.stream().anyMatch(entity -> entity.getPointer().getAddress() == entityBase) || this.isEntityTeamValid(entityBase))
                continue;

            Entity entity = entityBase == localPlayerBase ? new LocalPlayer(entityBase, i) : new Entity(entityBase, i);

            this.entities.add(entity);
            System.out.println("Loaded entity: " + entity.getName() + " (" + entity.getRank().getName() + ") ");
        }

        // Tick entities update
        TrayzWare.getInstance().getEntityManager().getEntities().forEach(Entity::update);
    }


    public LocalPlayer getLocalPlayer() {
        return entities.stream().filter(entity -> entity instanceof LocalPlayer).map(entity -> (LocalPlayer) entity).findFirst().orElse(null);
    }

    private boolean isEntityTeamValid(long entityBase) {
        int team = TrayzWare.getInstance().getProcess().readInt(entityBase + OffsetsManager.getNetvar("m_iTeamNum"));
        return team != 2 && team != 3;
    }
}
