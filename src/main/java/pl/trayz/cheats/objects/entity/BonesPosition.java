package pl.trayz.cheats.objects.entity;

import com.sun.jna.platform.win32.Win32Exception;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.trayz.cheats.enums.Bone;
import pl.trayz.cheats.objects.memory.Pointer;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.utils.vec.Vec3f;

/**
 * @Author: Trayz
 **/

@RequiredArgsConstructor
@Data
public class BonesPosition {

    private final Pointer pointer;

    private Vec3f headPos;
    private Vec3f neckPos;
    private Vec3f bodyPos;
    private Vec3f pelvisPos;
    private Vec3f legsPos;

    public void update() {
        this.headPos = readBonePos(Bone.HEAD.getId());
        this.neckPos = readBonePos(Bone.NECK.getId());
        this.bodyPos = readBonePos(Bone.CHEST.getId());
        this.pelvisPos = readBonePos(Bone.PELVIS.getId());
        this.legsPos = readBonePos(Bone.LOWER_CHEST.getId());
    }

    private Vec3f readBonePos(int bone) {
        try {
            Pointer boneMatrix = Pointer.of(pointer.readUnsignedInt(OffsetsManager.getNetvar("m_dwBoneMatrix")));
            if (boneMatrix.getAddress() == 0) return new Vec3f(0, 0, 0);

            float x = boneMatrix.readFloat(0x30 * bone + 0x0C);
            float z = boneMatrix.readFloat(0x30 * bone + 0x1C);
            float y = (boneMatrix.readFloat(0x30 * bone + 0x2C));
            return new Vec3f(x, y, z);
        } catch (Win32Exception e) {
            e.printStackTrace();
            return new Vec3f(0, 0, 0);
        }
    }

}
