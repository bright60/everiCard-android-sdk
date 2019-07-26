package ltd.vastchain.evericard.sdk.command;

// 6.1.9
public class SeedBackup extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x1e;

    public SeedBackup() {
        super(CLA, INS, (byte) 0x00, (byte) 0x00, (byte) 0x00);
    }
}
