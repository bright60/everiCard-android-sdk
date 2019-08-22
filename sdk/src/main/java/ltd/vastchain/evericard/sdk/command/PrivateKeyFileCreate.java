package ltd.vastchain.evericard.sdk.command;

import java.nio.ByteBuffer;

public class PrivateKeyFileCreate extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x09;

    public PrivateKeyFileCreate(byte p1, byte p2, byte lc, byte[] data) {
        super(CLA, INS, p1, p2, lc, data);
    }

    public static PrivateKeyFileCreate setByIndexAndSymbolId(int index, int symbolId, boolean pinProtected) {
        if (index < 0 || index > 255) {
            throw new IllegalArgumentException("Index must be within the range 0~255.");
        }

        byte[] bb = ByteBuffer.allocate(7)
                .put((byte) index)
                .putInt(symbolId)
                .put(pinProtected ? (byte) 0x03 : (byte) 0x00)
                .put((byte) 0x01)
                .array();

        return new PrivateKeyFileCreate((byte) 0x01, (byte) 0x00, (byte) 0x07, bb);
    }
}
