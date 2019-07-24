package ltd.vastchain.evericard.sdk.command;

public class PublicKeyRead extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x0b;

    public PublicKeyRead(byte p1, byte p2, byte le) {
        super(CLA, INS, p1, p2, le);
    }

    public static PublicKeyRead byIndex(int index) {
        return new PublicKeyRead((byte) index, (byte) 0x00, (byte) 0x41);
    }
}
