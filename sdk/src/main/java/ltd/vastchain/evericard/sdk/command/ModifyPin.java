package ltd.vastchain.evericard.sdk.command;

import java.nio.ByteBuffer;

public class ModifyPin extends Command {
    public static byte CLA = (byte) 0x80;
    public static byte INS = (byte) 0x5e;

    public ModifyPin(byte[] oldPin, byte[] newPin) {
        super(
                CLA,
                INS,
                (byte) 0x01,
                (byte) 0x00,
                (byte) (oldPin.length + newPin.length + 1),
                ByteBuffer.allocate(oldPin.length + newPin.length + 1).put(newPin).put((byte) 0xff).put(oldPin).array()
        );
    }

    public static ModifyPin of(byte[] oldPin, byte[] newPin) {
        if (oldPin.length == 0 || newPin.length == 0) {
            throw new IllegalArgumentException("Pin can't have zero length.");
        }

        if (oldPin.length > 8 || newPin.length > 8) {
            throw new IllegalArgumentException("Pin size exceeds the maximum size (8 bytes).");
        }

        return new ModifyPin(oldPin, newPin);
    }
}
