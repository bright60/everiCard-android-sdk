package ltd.vastchain.evericard.sdk.command;

import java.nio.ByteBuffer;

public class PublicKeyRead extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x0b;

    public PublicKeyRead(byte p1, byte p2, byte le) {
        super(CLA, INS, p1, p2, le);
    }

    public PublicKeyRead(byte p1, byte p2, byte lc, byte[] data, byte le) {
        super(CLA, INS, p1, p2, lc, data, le);
    }

    public static PublicKeyRead byIndex(int index) {
        if (index < 0 || index > 255) {
            throw new IllegalArgumentException("Index must be within the range 0~255.");
        }

        return new PublicKeyRead((byte) index, (byte) 0x00, (byte) 0x41);
    }

    public static PublicKeyRead byIndexAndSymbolId(int index, int symbolId) {
        if (index < 0 || index > 255) {
            throw new IllegalArgumentException("Index must be within the range 0~255.");
        }

        byte[] bb = ByteBuffer.allocate(4).putInt(symbolId).array();

        return new PublicKeyRead((byte) index, (byte) 0x01, (byte) 0x04, bb, (byte) 0x41);
    }
}
