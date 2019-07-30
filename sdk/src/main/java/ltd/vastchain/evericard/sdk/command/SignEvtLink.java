package ltd.vastchain.evericard.sdk.command;

public class SignEvtLink extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x13;

    public SignEvtLink(int keyIndex, byte[] data) {
        super(CLA, INS, (byte) 0x00, (byte) keyIndex, (byte) data.length, data);
    }

    public static SignEvtLink of(int keyIndex, byte[] data) {
        if (keyIndex < 0 || keyIndex > 255) {
            throw new IllegalArgumentException("Key index can only be within 0~255");
        }

        if (data.length == 0) {
            throw new IllegalArgumentException("EvtLink can't be empty");
        }

        return new SignEvtLink(keyIndex, data);
    }
}
