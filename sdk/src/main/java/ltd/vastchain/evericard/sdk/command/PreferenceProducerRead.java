package ltd.vastchain.evericard.sdk.command;

// 6.1.5
public class PreferenceProducerRead extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x05;

    public PreferenceProducerRead() {
        super(CLA, INS, (byte) 0x00, (byte) 0x00, (byte) 0x12);
    }
}
