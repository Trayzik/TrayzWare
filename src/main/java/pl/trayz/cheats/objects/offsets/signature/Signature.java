package pl.trayz.cheats.objects.offsets.signature;

import com.github.jonatino.process.Module;

public class Signature {


    public Module module;
    public int flags;
    public int pattern_offset;
    public int address_offset;
    public int[] pattern;
    public String mask;

    public String name;

    public Signature(String nameIn, Module moduleIn, int[] patternIn, String maskIn, int flagsIn, int pattern_offsetIn, int address_offsetIn) {
        this.name = nameIn;
        this.module = moduleIn;
        this.flags = flagsIn;
        this.pattern_offset = pattern_offsetIn;
        this.address_offset = address_offsetIn;
        this.pattern = patternIn;
        if (maskIn == null) maskIn = maskFromPattern(patternIn);
        this.mask = maskIn;
    }

    public Signature(String nameIn, Module moduleIn, String patternIn, String maskIn, int flagsIn, int pattern_offsetIn, int address_offsetIn) {
        this(nameIn, moduleIn, patternFromString(patternIn), maskIn, flagsIn, pattern_offsetIn, address_offsetIn);
    }

    public static String maskFromPattern(int[] pattern) {
        StringBuilder sb = new StringBuilder();
        for (int j : pattern) {
            sb.append(j != 0x00 ? "x" : "?");
        }
        return sb.toString();
    }

    public static int[] patternFromString(String values) {
        if (values.equals("")) return new int[0];
        String[] s_values = values.split(" +");
        int[] i_values = new int[s_values.length];
        for (int i = 0; i < s_values.length; i++) {
            String s = s_values[i];
            if (s.equals("?"))
                i_values[i] = 0x0;
            else
                i_values[i] = Integer.parseInt(s_values[i], 16);
        }
        return i_values;
    }

    public static String maskFromPatternString(String values) {
        return values.replaceAll("(?<=^|\\b)[\\w|\\d]{1,2}(?=$|\\b)", "x")
                     .replaceAll("[^x|?]", "")
                     .replaceAll("\\s", "");
    }


}
