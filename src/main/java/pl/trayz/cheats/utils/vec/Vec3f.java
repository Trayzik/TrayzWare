package pl.trayz.cheats.utils.vec;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Vec3f {

    public float x,y,z;

    public Vec3f add(Vec3f other) {
        return new Vec3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec2f add(Vec2f other) {
        return new Vec2f(this.x + other.x, this.y + other.y);
    }

    public boolean isValid() {
        return !(x == 0 && z == 0);
    }

    public Vec3f copy() {
        return new Vec3f(x, y, z);
    }

    public int length() {
        return (int) Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3f subtract(Vec3f v) {
        return new Vec3f(x - v.x, y - v.y, z - v.z);
    }

    public void divideAssign(float v) {
        x /= v;
        y /= v;
        z /= v;
    }

}
