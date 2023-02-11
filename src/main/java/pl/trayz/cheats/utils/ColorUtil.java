package pl.trayz.cheats.utils;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;

/**
 * @Author: Trayz
 **/

@Data
public class ColorUtil implements Serializable {

    private int red,green,blue;

    public ColorUtil(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public Color getColor() {
        return new Color(red,green,blue);
    }
}
