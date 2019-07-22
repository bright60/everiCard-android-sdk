package ltd.vastchain.evericard.sdk.command;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.everitoken.sdk.java.Utils;

public class CommandTest {

    @Test
    public void testCommandWithoutData() {
        Command command = new Command((byte) 0x01, (byte) 0x00, (byte) 0x01);
        Assertions.assertEquals("8001000100", Utils.HEX.encode(command.getBytes()));
    }

    @Test
    public void testDataCombinedWithLc() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Command((byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x20, new byte[]{0x00, 0x00, 0x00});
        });
        Assertions.assertDoesNotThrow(() -> {
            new Command((byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x20, new byte[]{0x00, 0x00, 0x00, 0x01});
        });
    }
}