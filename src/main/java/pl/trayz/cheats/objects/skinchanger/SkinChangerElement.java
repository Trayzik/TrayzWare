package pl.trayz.cheats.objects.skinchanger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.trayz.cheats.enums.Skins;
import pl.trayz.cheats.enums.Team;
import pl.trayz.cheats.enums.Weapons;

/**
 * @Author: Trayz
 **/

@AllArgsConstructor@Getter
public class SkinChangerElement {

    private Skins skin;
    private Weapons weapon;
    private Team team;
    private boolean enabled;
}
