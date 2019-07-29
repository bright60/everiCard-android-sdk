package ltd.vastchain.evericard.sdk.command;

public class SignHash extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x0d;

    public SignHash(int keyIndex, byte[] data) {
        super(CLA, INS, (byte) keyIndex, (byte) 0x00, (byte) 0x20, data);
    }

    public static SignHash of(int keyIndex, byte[] data) {
        if (keyIndex < 0 || keyIndex > 255) {
            throw new IllegalArgumentException("Key index can only be within 0~255");
        }

        return new SignHash(keyIndex, data);
    }
}
