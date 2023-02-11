package pl.trayz.cheats.objects.memory;

import com.github.jonatino.misc.Cacheable;
import pl.trayz.cheats.TrayzWare;

public class Pointer {

    private final long address;

    private Pointer(long addressIn) {
        this.address = addressIn;
    }

    public static Pointer of(long addressIn) {
        return new Pointer(addressIn);
    }

    public long getAddress() {
        return this.address;
    }

    /*
      *  Read methods
    */
    public boolean readBoolean(int off) {
        return TrayzWare.getInstance().getProcess().readBoolean(address + off);
    }

    public int readByte(int off) {
        return TrayzWare.getInstance().getProcess().readByte(address + off);
    }

    public int readInt(int off) {
        return TrayzWare.getInstance().getProcess().readInt(address + off);
    }

    public long readUnsignedInt(int off) {
        return TrayzWare.getInstance().getProcess().readUnsignedInt(address + off);
    }

    public short readShort(int off) {
        return (short)TrayzWare.getInstance().getProcess().readShort(address + off);
    }

    public long readLong(int off) {
        return TrayzWare.getInstance().getProcess().readLong(address + off);
    }

    public float readFloat(int off) {
        return TrayzWare.getInstance().getProcess().readFloat(address + off);
    }

    public double readDouble(int off) {
        return TrayzWare.getInstance().getProcess().readDouble(address + off);
    }

    public String readString(int off,int length) {
        byte[] bytes = Cacheable.array(length);
        TrayzWare.getInstance().getProcess().read(address+off, bytes.length).get(bytes);
        for(int i = 0; i < bytes.length; ++i) {
            if (bytes[i] == 0) {
                bytes[i] = 32;
            }
        }

        return new String(bytes).replaceAll("\\s+$", "");
    }


    /*
      *  Write methods
    */

    public void writeBoolean(boolean val, int off) {
        TrayzWare.getInstance().getProcess().writeBoolean(address + off, val);
    }

    public void writeByte(byte val, int off) {
        TrayzWare.getInstance().getProcess().writeByte(address + off, val);
    }

    public void writeInt(int val, int off) {
        TrayzWare.getInstance().getProcess().writeInt(address + off, val);
    }

    public void writeUnsignedInt(long val, int off) {
        TrayzWare.getInstance().getProcess().writeInt(address + off, (int)val);
    }

    public void writeShort(short val, int off) {
        TrayzWare.getInstance().getProcess().writeShort(address + off, val);
    }

    public void writeLong(long val, int off) {
        TrayzWare.getInstance().getProcess().writeLong(address + off, val);
    }

    public void writeFloat(float val, int off) {
        TrayzWare.getInstance().getProcess().writeFloat(address + off, val);
    }

    public void writeDouble(double val, int off) {
        TrayzWare.getInstance().getProcess().writeDouble(address + off, val);
    }

}
