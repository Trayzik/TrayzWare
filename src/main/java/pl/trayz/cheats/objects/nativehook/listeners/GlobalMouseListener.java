package pl.trayz.cheats.objects.nativehook.listeners;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import pl.trayz.cheats.TrayzWare;

public class GlobalMouseListener implements NativeMouseInputListener {

    @Override
    public void nativeMouseClicked(NativeMouseEvent event) {
        TrayzWare.getInstance().getModifications().forEach(mod -> mod.mouseClicked(event));
}

    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
        TrayzWare.getInstance().getModifications().forEach(mod -> mod.mousePressed(event));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {

    }

}
