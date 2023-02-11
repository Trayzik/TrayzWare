package pl.trayz.cheats.objects.nativehook.listeners;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.overlay.Overlay;

public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        TrayzWare.getInstance().getModifications().forEach(mod -> mod.keyPressed(event));

        if (event.getKeyCode() == 3666) {
            if (!Overlay.overlay.isVisible() && TrayzWare.getInstance().isGameWindowActive()) {
                Overlay.showOverlay();
            } else {
                Overlay.hideOverlay();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
        TrayzWare.getInstance().getModifications().forEach(mod -> mod.keyReleased(event));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
        TrayzWare.getInstance().getModifications().forEach(mod -> mod.keyPressed(event));
    }

}
