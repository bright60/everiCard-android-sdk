package ltd.vastchain.evericard.sdk.command;

public class CreationEnd extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x0c;

    public CreationEnd() {
        super(CLA, INS, (byte) 0x00, (byte) 0x00, (byte) 0x00);
    }
}
