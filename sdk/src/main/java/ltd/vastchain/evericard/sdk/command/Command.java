package ltd.vastchain.evericard.sdk.command;

import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class Command implements CommandInterface {

    private final byte cla;
    private final byte ins; // instruction sequence
    private final byte p1; // parameter 1
    private final byte p2; // parameter 2

    private final Byte lc; // length of input data
    private final byte[] data;
    private final Byte le; // length of return data

    public Command(byte cla, byte ins, byte p1, byte p2, @Nullable Byte lc, @Nullable byte[] data, @Nullable Byte le) {

//        if (lc != null && data != null && lc.intValue() != data.length) {
//            throw new IllegalArgumentException("lc and the length of data don't match.");
//        }

        this.cla = cla;
        this.ins = ins;
        this.p1 = p1;
        this.p2 = p2;
        this.lc = lc;
        this.data = data;
        this.le = le;
    }

    public Command(byte cla, byte ins, byte p1, byte p2) {
        this(cla, ins, p1, p2, (byte) 0x00, null, null);
    }

    public Command(byte cla, byte ins, byte p1, byte p2, byte lc, byte[] data) {
        this(cla, ins, p1, p2, lc, data, null);
    }

    public Command(byte cla, byte ins, byte p1, byte p2, byte le) {
        this(cla, ins, p1, p2, null, null, le);
    }

    public static byte flagConfigurationItem(byte b) {
        return (byte) (0x80 | b);
    }

    public byte[] getBytes() {
        byte[] result = new byte[]{cla, ins, p1, p2};

        if (lc != null) {
            result = ArrayUtils.add(result, lc);
        }

        if (data != null) {
            result = ArrayUtils.addAll(result, data);
        }

        if (le != null) {
            result = ArrayUtils.add(result, le);
        }

        return result;
    }

    public byte[] getData() {
        if (data == null) {
            return new byte[]{};
        }

        return data;
    }
}
