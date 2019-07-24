package ltd.vastchain.evericard.sdk.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.everitoken.sdk.java.Utils;

class VerifyPinTest {

    @Test
    void of() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            VerifyPin.of(new byte[]{});
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            VerifyPin.of(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        });

        Assertions.assertDoesNotThrow(() -> {
            VerifyPin command = VerifyPin.of(new byte[]{1, 2, 3, 4});
            Assertions.assertEquals("002000000401020304", Utils.HEX.encode(command.getBytes()));
        });
    }
}