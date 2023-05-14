package pl.trayz.cheats.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.enums.Bone;
import pl.trayz.cheats.objects.skinchanger.SkinChangerElement;
import pl.trayz.cheats.utils.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Trayz
 **/

@Data
public class Configuration {

    private final List<String> enabledMods;
    private final AntiFlash antiFlash;
    private final Glow glow;
    private final Esp esp;
    private final Chams chams;
    private final Aimbot aimbot;
    private final TriggerBot triggerBot;
    private final Fov fov;
    private final SkinChanger skinChanger;

    public Configuration() {
        this.enabledMods = new CopyOnWriteArrayList<>();
        this.antiFlash = new AntiFlash(0f);
        this.glow = new Glow(new ColorUtil(Color.WHITE),new ColorUtil(Color.GREEN),true,true);
        this.esp = new Esp(new ColorUtil(Color.WHITE),true, 3);
        this.chams = new Chams(new ColorUtil(Color.RED),new ColorUtil(Color.GREEN),true,false);
        this.aimbot = new Aimbot(50,5,56,Bone.HEAD);
        this.triggerBot = new TriggerBot(0,-1);
        this.fov = new Fov(120);
        this.skinChanger = new SkinChanger(new ArrayList<>());
    }

    public void load(File file) {
        if(!file.exists())  return;

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            TrayzWare.getInstance().setConfiguration(gson.fromJson(Files.readString(file.toPath()), Configuration.class));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean save(File file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

            if (!file.exists())
                file.createNewFile();

            try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
                writer.println(gson.toJson(this));
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Failed to save configuration!");
        }
        return false;
    }

    @AllArgsConstructor@Data
    public static class AntiFlash {
        private float alpha;
    }

    @AllArgsConstructor@Data
    public static class Glow {
        private ColorUtil enemyColor;
        private ColorUtil teammateColor;

        private boolean glowOnEnemy;
        private boolean glowOnTeammate;
    }

    @AllArgsConstructor@Data
    public static class Esp {
        private ColorUtil borderColor;

        private boolean showHp;
        private int hpBarSize;
    }

    @AllArgsConstructor@Data
    public static class Chams {
        private ColorUtil enemyColor;
        private ColorUtil teammateColor;

        private boolean chamsOnEnemy;
        private boolean chamsOnTeammate;
    }

    @AllArgsConstructor@Data
    public static class Aimbot {
        private int fov;
        private int delay;
        private int bind;
        private Bone bone;
    }

    @AllArgsConstructor@Data
    public static class TriggerBot {

        private int delay;
        private int bind;
    }

    @AllArgsConstructor@Data
    public static class Fov {

        private int fov;
    }


    @AllArgsConstructor@Data
    public static class SkinChanger {

        private List<SkinChangerElement> skins;
    }


}

