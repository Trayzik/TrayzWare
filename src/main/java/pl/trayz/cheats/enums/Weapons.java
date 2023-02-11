package pl.trayz.cheats.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Trayz
 * @Created 27.04.2022
 **/

@Getter
@AllArgsConstructor
public enum Weapons {

    DEAGL(1),
    BERETS(2),
    FIVESEVEN(3),
    GLOCK(4),
    AK47(7),
    AUG(8),
    AWP(9),
    FAMAS(10),
    G3SG1(11),
    GALIL(13),
    M249(14),
    M4A4(16),
    MAC10(17),
    P90(19),
    MP5(23),
    UMP45(24),
    XM1014(25),
    BIZON(26),
    MAG(27),
    NEGEV(28),
    SAWED(29),
    TEC(30),
    ZEUS(31),
    P2000(32),
    MP7(33),
    MP9(34),
    NOVA(35),
    P250(36),
    SCAR20(38),
    SG553(39),
    KNIFE(42),
    SSG08(40),
    M4A1S(60),
    USP(61),
    CZ75(63),
    REVOLVER(64),
    KNIFE_BAYONET(500),
    KNIFE_FLIP(505),
    KNIFE_GUT(506),
    KNIFE_KARAMBIT(507),
    KNIFE_M9_BAYONET(508),
    KNIFE_HUNTSMAN(509),
    KNIFE_FALCHION(512),
    KNIFE_BOWIE(514),
    KNIFE_BUTTERFLY(515),
    KNIFE_DAGGERS(516),
    KNIFE_URSUS(519),
    KNIFE_NAVAJA(520),
    KNIFE_STILETTO(522),
    KNIFE_TALON(523);

    private final int id;


}
