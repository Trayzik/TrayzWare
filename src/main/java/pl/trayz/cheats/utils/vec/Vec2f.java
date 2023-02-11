package pl.trayz.cheats.utils.vec;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Vec2f {

    public float x,y;

    public boolean isValid() {
        return !Double.isNaN(this.x) &&  !Double.isNaN(this.y);
    }
}
