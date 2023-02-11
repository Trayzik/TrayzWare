package pl.trayz.cheats.objects.offsets.signature;

import com.github.jonatino.misc.MemoryBuffer;
import com.github.jonatino.process.Module;

import java.util.HashMap;
import java.util.Map;


public class SignatureScanner {

    private byte[] region;
    private final Module module;

    private static final Map<Module, SignatureScanner> moduleScannerMap = new HashMap<>();

    public SignatureScanner(Module moduleIn) {
        this.module = moduleIn;
    }

    public void dumpMemory() {
        MemoryBuffer buff = this.module.read(0, module.size());
        region = buff.getArray();
    }

    public static int findOffset(Signature sig) {
        SignatureScanner scanner = moduleScannerMap.get(sig.module);
        if (scanner == null) {
            scanner = new SignatureScanner(sig.module);
            scanner.dumpMemory();
            moduleScannerMap.put(sig.module, scanner);
        }
        return scanner.findOffset(sig.pattern, sig.mask, sig.flags, sig.pattern_offset, sig.address_offset);
    }

    public int findOffset(int[] pattern, String mask, int flags, int pattern_offset, int address_offset) {
        if (mask == null) mask = Signature.maskFromPattern(pattern);
        if (pattern.length != mask.length()) throw new IllegalArgumentException("pattern length not equal to mask length");

        byte[] subRegion = new byte[pattern.length];
        for (int i = 0; i < region.length - pattern.length; i++) {
            System.arraycopy(region, i, subRegion, 0, subRegion.length);
            if (regionMatches(subRegion, pattern, mask)) {
                i += pattern_offset;
                if ((flags & 1) == 1) {
                    i = module.readInt(i);
                }
                if ((flags & 2) == 2) {
                    i -= module.address();
                }
                return i + address_offset;
            }
        }

        return -1; // not found
    }

    private boolean regionMatches(byte[] subRegion, int[] pattern, String mask) {
        for (int i = 0; i < subRegion.length; i++) {
            if (mask.charAt(i) == 'x' && subRegion[i] != (byte)pattern[i]) {
                return false;
            }
        }

        return true;
    }

    public static void freeMemory() {
        moduleScannerMap.clear();
    }

}