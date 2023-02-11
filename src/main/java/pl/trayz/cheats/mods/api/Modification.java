package pl.trayz.cheats.mods.api;

import lombok.Data;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.configuration.Configuration;

/**
 * @Author: Trayz
 **/

public abstract class Modification {

    public final String name;
    public final String description;

    public Modification(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void tick();


    public void keyPressed(NativeKeyEvent event) {
    }

    public void keyReleased(NativeKeyEvent event) {
    }

    public void mousePressed(NativeMouseEvent event) {
    }

    public void mouseClicked(NativeMouseEvent event) {
    }

    public Configuration configuration() {
        return TrayzWare.getInstance().getConfiguration();
    }
}
