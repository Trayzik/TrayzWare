package pl.trayz.cheats.objects.nativehook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import pl.trayz.cheats.objects.nativehook.listeners.GlobalKeyListener;
import pl.trayz.cheats.objects.nativehook.listeners.GlobalMouseListener;

import java.util.logging.LogManager;

/**
 * @Author: Trayz
 **/

public class NativeHookManager {

    public NativeHookManager() {
        LogManager.getLogManager().reset();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register JNativeHook");
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

        GlobalMouseListener m = new GlobalMouseListener();
        GlobalScreen.addNativeMouseListener(m);
    }
}
