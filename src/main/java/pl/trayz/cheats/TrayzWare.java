package pl.trayz.cheats;

import com.github.jonatino.process.Processes;
import com.github.jonatino.process.Process;
import com.github.jonatino.process.Module;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import lombok.Data;
import lombok.Getter;
import pl.trayz.cheats.configuration.Configuration;
import pl.trayz.cheats.mods.*;
import pl.trayz.cheats.mods.api.Modification;
import pl.trayz.cheats.objects.entity.EntityManager;
import pl.trayz.cheats.objects.nativehook.NativeHookManager;
import pl.trayz.cheats.objects.offsets.OffsetsManager;
import pl.trayz.cheats.overlay.Overlay;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @Author: Trayz
 **/

@Data
public class TrayzWare {

    @Getter private static TrayzWare instance;

    /*
      *  Memory
    */
    private Process process;
    private final Module client;
    private final Module engine;

    /*
      *  Managers
    */
    private final OffsetsManager offsetsManager;
    private final String gameDir;
    private final EntityManager entityManager;
//    private BSPParser bspParser;
    private Configuration configuration;

    /*
      *  Mods
    */
    private final List<Modification> modifications = Arrays.asList(
            new RadarMod(),
            new AntiFlashMod(),
            new BhopMod(),
            new GlowMod(),
            new AimbotMod(),
            new RcsMod(),
            new TriggerBotMod(),
            new FovMod(),
            new ChamsMod(),
            new EspMod()
//            new SkinChangerMod()      Don't use, probably vacable

    );

    public TrayzWare() {
        instance = this;

        System.out.println("Loading TrayzWare...");

        /*
         *  Load process and modules
         */
        try {
            this.process = Processes.byName(Platform.isWindows() ? "csgo.exe" : Platform.isLinux() ? "csgo_linux" : "csgo_osx");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, "Failed to find csgo process!");
            System.exit(1);
        }

        String nativeName = Platform.isWindows() ? ".dll" : Platform.isLinux() ? "_client.so" : ".dylib";
        this.client = process.findModule("client" + nativeName);
        this.engine = process.findModule("engine" + nativeName);

        /*
         *  Load managers
         */
        this.offsetsManager = new OffsetsManager();
        this.gameDir = OffsetsManager.getOffset("dwGameDir").readString(0, 260);
        this.entityManager = new EntityManager();
        this.configuration = new Configuration();
        new NativeHookManager();

        Executors.newSingleThreadExecutor().execute(() -> Overlay.launch(Overlay.class));

        /*
         *  Tick
         */
        while (true) {
            try {
                // Hiding the overlay when the game is not in focus
                if (Overlay.overlay != null && Overlay.overlay.isVisible() && !this.isGameWindowActive()) {
                    Overlay.hideOverlay();
                    Overlay.waitingForWindow = true;
                } else if (Overlay.overlay != null && !Overlay.overlay.isVisible() && this.isGameWindowActive() && Overlay.waitingForWindow) {
                    Overlay.showOverlay();
                    Overlay.waitingForWindow = false;
                }

                if (this.getGameState() != 6) {
                    this.entityManager.getEntities().clear();
//                    TrayzWare.getInstance().setBspParser(null);
                    continue;
                }

//                if (TrayzWare.getInstance().getBspParser() == null)
//                    TrayzWare.getInstance().setBspParser(new BSPParser(TrayzWare.getInstance().getGameDir() + "\\" + OffsetsManager.getOffset("dwClientState").getPointer(0).readString(OffsetsManager.getNetvar("dwClientState_MapDirectory"), 260)));

                this.entityManager.updateEntities();

                this.modifications.forEach(modification -> {
                    if (this.getConfiguration().getEnabledMods().contains(modification.name.toLowerCase()))
                        modification.tick();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getGameState() {
        return OffsetsManager.getOffset("dwClientState").getPointer(0).readInt(OffsetsManager.getNetvar("dwClientState_State"));
    }

    public boolean isGameWindowActive() {
        WinDef.HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
        if(foregroundWindow == null) return false;

        IntByReference processId = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(foregroundWindow, processId);

        return process.id() == processId.getValue() || Kernel32.INSTANCE.GetCurrentProcessId() == processId.getValue();
    }

    public static void main(String[] args) {
        new TrayzWare();
    }

}
