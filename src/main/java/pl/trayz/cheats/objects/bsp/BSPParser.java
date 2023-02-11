package pl.trayz.cheats.objects.bsp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Getter;
import lombok.SneakyThrows;
import pl.trayz.cheats.TrayzWare;
import pl.trayz.cheats.objects.bsp.BSPStructure.*;
import pl.trayz.cheats.utils.vec.Vec3f;

/**
 * @Author: Trayz
 **/

public class BSPParser {

    private final int LUMP_LEAFS = 10;
    private final int LUMP_NODES = 5;
    private final int LUMP_PLANES = 1;

    @Getter private final String filePath;
    private ByteBuffer stream;

    private final dheader_t header = new dheader_t();

    private dleaf_t[] leafs;
    private dnode_t[] nodes;
    private dplane_t[] planes;

    @SneakyThrows
    public BSPParser(String filePath) {
        this.filePath = filePath;
        try {
            this.stream = ByteBuffer.wrap(Files.readAllBytes(Paths.get(filePath)));
        }catch (Exception e) {
//            TrayzWare.getInstance().setBspParser(null);
            return;
        }

        parse();
    }

    public dleaf_t getLeafForPoint(Vec3f point) {
        int index = 0;

        dnode_t node;
        dplane_t plane;

        while (index >= 0) {
            node = nodes[index];
            plane = planes[node.planenum];

            float d = (point.x * plane.normal.x + point.y * plane.normal.y + point.z * plane.normal.z) - plane.dist;

            index = (d > 0) ? node.children[0] : node.children[1];

        }

        if (-index - 1 < leafs.length) {
            return leafs[-index - 1];
        } else {
            dleaf_t newLeaf = new dleaf_t();

            newLeaf.area = -1;
            newLeaf.contents = 0;
            return newLeaf;
        }
    }

    public void parse() {
        header.ident = stream.getInt();

        if (header.ident == 'V' + ('B' << 8) + ('S' << 16) + ('P' << 24)) {
            stream.order(ByteOrder.BIG_ENDIAN);
        } else {
            stream.order(ByteOrder.nativeOrder());
        }

        header.version = stream.getInt();

        header.lumps = new lump_t[64];
        for (int i = 0; i < header.lumps.length-1; i++) {
            lump_t lump = new lump_t();
            lump.fileofs = stream.getInt();
            lump.filelen = stream.getInt();
            lump.version = stream.getInt();
            lump.fourCC = stream.getInt();

            header.lumps[i] = lump;
        }

        header.mapRevision = stream.getInt();

        stream.position(header.lumps[LUMP_LEAFS].fileofs);
        leafs = new dleaf_t[header.lumps[LUMP_LEAFS].filelen / 56];
        for (int i = 0; i < leafs.length-1; i++) {
            dleaf_t leaf = new dleaf_t();

            leaf.contents = stream.getInt();
            leaf.cluster = stream.getShort();

            int areaFlags = stream.getInt();
            leaf.area = areaFlags & 0x1FF;
            leaf.flags = (areaFlags >> 9) & 0x3F;

            leaf.flags = stream.getShort();

            for (int j = 0; j <= leaf.mins.length-1; j++) leaf.mins[j] = stream.getShort();
            for (int j = 0; j <= leaf.maxs.length-1; j++) leaf.maxs[j] = stream.getShort();

            leaf.firstleafface = stream.getShort() & 0xffff;
            leaf.numleaffaces = stream.getShort() & 0xffff;
            leaf.firstleafbrush = stream.getShort() & 0xffff;
            leaf.numleafbrushes = stream.getShort() & 0xffff;
            leaf.leafWaterDataID = stream.getShort();

            leafs[i] = leaf;
        }

        stream.position(header.lumps[LUMP_PLANES].fileofs);
        planes = new dplane_t[header.lumps[LUMP_PLANES].filelen / 20];
        for (int i = 0; i < planes.length-1; i++) {
            dplane_t plane = new dplane_t();

            plane.normal = new Vec3f(stream.getFloat(), stream.getFloat(), stream.getFloat());
            plane.dist = stream.getFloat();
            plane.type = stream.getInt();

            planes[i] = plane;
        }

        stream.position(header.lumps[LUMP_NODES].fileofs);
        nodes = new dnode_t[header.lumps[LUMP_NODES].filelen / 32];
        for (int i = 0; i < nodes.length-1; i++) {
            dnode_t node = new dnode_t();

            node.planenum = stream.getInt();

            for (int j = 0; j <= node.children.length-1; j++) {
                node.children[j] = stream.getInt();
            }

            for (int j = 0; j <= node.mins.length-1; j++) node.mins[j] = stream.getShort();

            for (int j = 0; j <= node.maxs.length-1; j++) node.maxs[j] = stream.getShort();

            node.firstface = stream.getShort() & 0xffff;
            node.numfaces = stream.getShort() & 0xffff;
            node.area = stream.getShort();
            node.paddding = stream.getShort();

            nodes[i] = node;
        }
    }

    public boolean visible(Vec3f vStart, Vec3f vEnd) {
        Vec3f vDirection = vEnd.subtract(vStart);

        float steps = vDirection.length();
        vDirection.divideAssign(steps);

        dleaf_t leaf;
        Vec3f vPoint = vEnd.copy();
        while (steps >= 0) {
            vPoint = vPoint.subtract(vDirection);

            leaf = getLeafForPoint(vPoint);

            if (leaf.area != -1) {
                if ((leaf.contents & 0x1) != 0) {
                    return false;
                }
            }
            steps--;
        }
        return true;
    }
}
