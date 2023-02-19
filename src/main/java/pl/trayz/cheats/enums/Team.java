package pl.trayz.cheats.enums;

import java.util.Arrays;

/**
 * @Author: Trayz
 **/

public enum Team {

    SPEC,
    TT,
    CT;


    public static Team getById(int id) {
        return Arrays.stream(values()).filter(team -> team.ordinal()+1 == id).findFirst().orElse(null);
    }
}
