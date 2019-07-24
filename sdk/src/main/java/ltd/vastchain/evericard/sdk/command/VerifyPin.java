package ltd.vastchain.evericard.sdk.command;

public class VerifyPin extends Command {
    public static byte CLA = (byte) 0x00;
    public static byte INS = (byte) 0x20;

    public VerifyPin(byte[] data) {
        super(CLA, INS, (byte) 0x00, (byte) 0x00, (byte) data.length, data);
    }

    public static VerifyPin of(byte[] pin) {
        if (pin.length == 0) {
            throw new IllegalArgumentException("Pin is empty.");
        }

        if (pin.length > 8) {
            throw new IllegalArgumentException("The maximum size of Pin is 8 bytes.");
        }

        return new VerifyPin(pin);
    }
}
