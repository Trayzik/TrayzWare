package pl.trayz.cheats.objects.entity;

import com.github.jonatino.process.Process;
import com.sun.jna.platform.win32.Win32Exception;
import lombok.Getter;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.enums.Rank;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.objects.memory.Pointer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.utils.vec.Vec2f;
import pl.trayz.cheats.utils.vec.Vec3f;

/**
 * @Author: Trayz
 **/

@Getter
public class Entity {

    private final Pointer pointer;
    private final int entityId;
    private final String name;
    private final Rank rank;
    private final BonesPosition bonesPosition;

    private Team team;
    private int health = 100;

    private Vec3f position;
    private Vec3f viewOffset;
    private Vec2f viewAngles;


    public Entity(long address, int entityID) {
        this.pointer = Pointer.of(address);
        this.entityId = entityID;

        /*
         * Load information's about entity
         */
        Process process = TrayzWare.getInstance().getProcess();

        int playerInfo = process.readInt(process.readInt(process.readInt(OffsetsManager.getOffset("dwClientState").getPointer(0).readInt(OffsetsManager.getNetvar("dwClientState_PlayerInfo")) + 0x40) + 0xC) + 0x28 + (entityID * 0x34L));

        this.name = process.readString(playerInfo + 0x10, 80);
        this.rank = Rank.getById(OffsetsManager.getOffset("dwPlayerResource").getPointer(0).readInt(OffsetsManager.getNetvar("m_iCompetitiveRanking") + (entityID * 4)));
        this.bonesPosition = new BonesPosition(this.pointer);

        update();
    }

    public void update() {
        this.team = Team.getById(pointer.readInt(OffsetsManager.getNetvar("m_iTeamNum")));
        this.health = pointer.readInt(OffsetsManager.getNetvar("m_iHealth"));

        int positionNetvar = OffsetsManager.getNetvar("m_vecOrigin");
        this.position = new Vec3f(pointer.readFloat(positionNetvar), pointer.readFloat(positionNetvar + 0x8), pointer.readFloat(positionNetvar + 0x4));

        if (name == null || team == null || health < 0 || health > 100 || pointer.getAddress() == 0 || position == null) {
            System.out.println("Removed entity: " + this.name);
            TrayzWare.getInstance().getEntityManager().getEntities().remove(this);
            return;
        }

        this.bonesPosition.update();

        int viewOffsetNetvar = OffsetsManager.getNetvar("m_vecViewOffset");
        this.viewOffset = new Vec3f(pointer.readFloat(viewOffsetNetvar), pointer.readFloat(viewOffsetNetvar + 0x8), pointer.readFloat(viewOffsetNetvar + 0x4));

        int viewAngelsNetvar = OffsetsManager.getNetvar("m_thirdPersonViewAngles");
        this.viewAngles = new Vec2f(pointer.readFloat(viewAngelsNetvar), pointer.readFloat(viewAngelsNetvar + 0x4));
    }
}
