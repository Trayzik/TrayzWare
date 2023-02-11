package pl.trayz.cheats.objects.offsets;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.objects.offsets.signature.Signature;
import pl.trayz.cheats.objects.offsets.signature.SignatureScanner;
import pl.trayz.cheats.utils.WebsiteUtil;
import com.github.jonatino.process.Module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Trayz
 **/

@Data
public class OffsetsManager {

    private final Map<String, Integer> netvars;
    private final Map<String, Offset> offsets;

    public OffsetsManager() {
        this.netvars = new ConcurrentHashMap<>();
        this.offsets = new ConcurrentHashMap<>();

        /*
          *  Load current offsets from website
        */

        JsonObject netvars = new JsonParser().parse(WebsiteUtil.getConfigFromWebsite("https://raw.githubusercontent.com/frk1/hazedumper/master/csgo.json")).getAsJsonObject();
        JsonArray offsets = new JsonParser().parse(WebsiteUtil.getConfigFromWebsite("https://raw.githubusercontent.com/frk1/hazedumper/master/config.json")).getAsJsonObject().getAsJsonArray("signatures");

        netvars.getAsJsonObject("netvars").entrySet().forEach(netvarsEntry -> {
            this.netvars.put(netvarsEntry.getKey(),netvarsEntry.getValue().getAsInt());
        });

        netvars.getAsJsonObject("signatures").entrySet().forEach(netvarsEntry -> {
            this.netvars.put(netvarsEntry.getKey(),netvarsEntry.getValue().getAsInt());
        });

        for(int i = 0; i<offsets.size(); i++) {
            JsonObject object = offsets.get(i).getAsJsonObject();
            String offsetName = object.get("name").getAsString();
            String moduleString = object.get("module").getAsString();

            if(!moduleString.equalsIgnoreCase("client.dll") && !moduleString.equalsIgnoreCase("engine.dll")) {
                continue;
            }

            Module module = moduleString.equalsIgnoreCase("client.dll") ? TrayzWare.getInstance().getClient() : TrayzWare.getInstance().getEngine();

            int flags = object.get("relative").getAsBoolean() ? 3 : 1;

            int pattern_offset = object.has("offsets") && object.get("offsets").getAsJsonArray().size() == 1 ? object.get("offsets").getAsInt() : 0;
            int address_offset = object.get("extra").getAsInt();

            String pattern = object.get("pattern").getAsString()
                    .replaceAll("\\?+(?=$)|(?<=^)\\?+", "")
                    .replaceAll("\\?{2,}", "?")
                    .trim();
            String mask = Signature.maskFromPatternString(pattern);

            Signature signature = new Signature(offsetName, module, pattern, mask, flags, pattern_offset, address_offset);

            this.offsets.put(offsetName, new Offset(offsetName, SignatureScanner.findOffset(signature),signature.module));
        }

        System.out.println("Loaded "+this.netvars.size()+" netvars, "+this.offsets.size()+" offsets!");
        SignatureScanner.freeMemory();
    }

    public static Offset getOffset(String offsetName) {
        return TrayzWare.getInstance().getOffsetsManager().getOffsets().getOrDefault(offsetName,new Offset(null,0,null));
    }

    public static int getNetvar(String netvarName) {
        return TrayzWare.getInstance().getOffsetsManager().getNetvars().getOrDefault(netvarName,-1);
    }
}
