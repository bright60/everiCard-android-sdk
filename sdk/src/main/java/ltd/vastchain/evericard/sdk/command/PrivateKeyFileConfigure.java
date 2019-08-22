package ltd.vastchain.evericard.sdk.command;

public class PrivateKeyFileConfigure extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x0A;

    public PrivateKeyFileConfigure(byte p1, byte p2, byte lc, byte[] data) {
        super(CLA, INS, p1, p2, lc, data);
    }

    public static PrivateKeyFileConfigure generateInternalKey(int index) {
        if (index < 0 || index > 255) {
            throw new IllegalArgumentException("Index must be within the range 0~255.");
        }

        return new PrivateKeyFileConfigure((byte) 0x01, (byte) index, (byte) 0x00, new byte[]{});
    }
}
