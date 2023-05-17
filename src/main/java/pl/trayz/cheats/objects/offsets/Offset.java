package pl.trayz.cheats.objects.offsets;

import com.github.jonatino.misc.Cacheable;
import com.github.jonatino.process.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.trayz.cheats.objects.memory.Pointer;

@Getter@Setter@AllArgsConstructor
public class Offset {

    private String name;
    private int offset;
    private Module module;


    public int readByte(int off) {
        return module.readByte(offset + off);
    }

    public boolean readBoolean(int off) {
        return module.readBoolean(offset + off);
    }

    public short readShort(int off) {
        return (short)module.readShort(offset + off);
    }

    public int readInt(int off) {
        return module.readInt(offset + off);
    }

    public long readUnsignedInt(int off) {
        return module.readUnsignedInt(offset + off);
    }

    public long readLong(int off) {
        return module.readLong(offset + off);
    }

    public float readFloat(int off) {
        return module.readFloat(offset + off);
    }

    public double readDouble(int off) {
        return module.readDouble(offset + off);
    }

    public String readString(int off,int length) {
        byte[] bytes = Cacheable.array(length);
        module.read(offset+off, bytes.length).get(bytes);
        for(int i = 0; i < bytes.length; ++i) {
            if (bytes[i] == 0) {
                bytes[i] = 32;
            }
        }
        return new String(bytes).replaceAll("\\s+$", "");
    }

    public Pointer getPointer(int off) {
        return Pointer.of(module.readUnsignedInt(offset + off));
    }

    public void writeBoolean(boolean val, int off) {
        module.writeBoolean(offset + off, val);
    }

    public void writeByte(byte val, int off) {
        module.writeByte(offset + off, val);
    }

    public void writeInt(int val, int off) {
        module.writeInt(offset + off, val);
    }

    public void writeShort(short val, int off) {
        module.writeShort(offset + off, val);
    }

    public void writeLong(long val, int off) {
        module.writeLong(offset + off, val);
    }

    public void writeFloat(float val, int off) {
        module.writeFloat(offset + off, val);
    }

    public void writeDouble(double val, int off) {
        module.writeDouble(offset + off, val);
    }


}
