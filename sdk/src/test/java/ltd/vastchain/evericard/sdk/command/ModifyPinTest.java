package ltd.vastchain.evericard.sdk.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ModifyPinTest {

    @Test
    void validateInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ModifyPin.of(new byte[0], new byte[]{1, 2});
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ModifyPin.of(new byte[]{1, 2}, new byte[0]);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ModifyPin.of(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new byte[]{1, 2});
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ModifyPin.of(new byte[]{1, 2}, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        });
    }

    @Test
    void constructPinCorrectly() {
        ModifyPin modifyPin = ModifyPin.of(new byte[]{1, 2}, new byte[]{1, 2, 3, 4, 5, 6, 7, 8});
        Assertions.assertTrue(Arrays.equals(
                new byte[]{(byte) 0x80, (byte) 0x5e, 1, 0, 11, 1, 2, 3, 4, 5, 6, 7, 8, (byte) 0xff, 1, 2},
                modifyPin.getBytes()
        ));
    }
}