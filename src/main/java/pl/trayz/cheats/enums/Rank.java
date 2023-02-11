package pl.trayz.cheats.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @Author: Trayz
 **/

@Getter @AllArgsConstructor
public enum Rank {

    UNRANKED("Unranked"),
    SILVER_1("Silver I"),
    SILVER_2("Silver II"),
    SILVER_3("Silver III"),
    SILVER_4("Silver IV"),
    SILVER_ELITE("Silver Elite"),
    SILVER_ELITE_MASTER("Silver Elite Master"),
    GOLD_NOVA_1("Gold Nova I"),
    GOLD_NOVA_2("Gold Nova II"),
    GOLD_NOVA_3("Gold Nova III"),
    GOLD_NOVA_MASTER("Gold Nova Master"),
    MASTER_GUARDIAN_1("Master Guardian I"),
    MASTER_GUARDIAN_2("Master Guardian II"),
    MASTER_GUARDIAN_ELITE("Master Guardian Elite"),
    DISTINGUISHED_MASTER_GUARDIAN("Distinguished Master Guardian"),
    LEGENDARY_EAGLE("Legendary Eagle"),
    LEGENDARY_EAGLE_MASTER("Legendary Eagle Master"),
    SUPREME_MASTER_FIRST_CLASS("Supreme Master First Class"),
    THE_GLOBAL_ELITE("The Global Elite");

    private final String name;
    public static Rank getById(int id) {
        return Arrays.stream(values()).filter(rank -> rank.ordinal() == id).findFirst().orElse(Rank.UNRANKED);
    }

}
