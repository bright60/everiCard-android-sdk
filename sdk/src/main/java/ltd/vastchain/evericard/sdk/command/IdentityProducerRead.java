package ltd.vastchain.evericard.sdk.command;

// 6.1.4
public class IdentityProducerRead extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x04;

    public IdentityProducerRead(byte[] random) {
        super(CLA, INS, (byte) 0x00, (byte) 0x00, (byte) 0x20, random);
    }

    public static IdentityProducerRead of(byte[] random) {
        if (random.length != 0x20) {
            throw new IllegalArgumentException("Length of random number must be 32 bytes");
        }

        return new IdentityProducerRead(random);
    }
}
