package pl.trayz.cheats.objects.bsp;

import pl.trayz.cheats.utils.vec.Vec3f;

/**
 * @Author: Trayz
 **/

public class BSPStructure {
    public static class dplane_t {
        public Vec3f normal;
        public float dist;
        public int type;
    }

    public static class dnode_t {
        public int planenum;
        public int[] children = new int[2];
        public short[] mins = new short[3];
        public short[] maxs = new short[3];
        public int firstface;
        public int numfaces;
        public short area;
        public short paddding;
    }

    public static class dleaf_t {
        public int contents;
        public short cluster;
        public int area;
        public int flags;
        public short[] mins = new short[3];
        public short[] maxs = new short[3];
        public int firstleafface;
        public int numleaffaces;
        public int firstleafbrush;
        public int numleafbrushes;
        public short leafWaterDataID;
    }

    public static class dheader_t {
        public int ident;
        public int version;
        public lump_t[] lumps = new lump_t[0];
        public int mapRevision;
    }

    public static class lump_t {
        public int fileofs;
        public int filelen;
        public int version;
        public int fourCC;
    }
}
