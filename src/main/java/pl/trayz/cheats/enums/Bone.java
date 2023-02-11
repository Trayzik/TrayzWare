package pl.trayz.cheats.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Bone {

    HEAD(8),
    NECK(7),
    CHEST(6),
    LOWER_CHEST(3),
    PELVIS(0);

    private final int id;

}
