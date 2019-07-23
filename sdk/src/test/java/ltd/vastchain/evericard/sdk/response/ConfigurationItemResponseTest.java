package ltd.vastchain.evericard.sdk.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigurationItemResponseTest {

    @Test
    void statusReadCorrectly() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ConfigurationItemResponse configurationItemResponse = new ConfigurationItemResponse(new byte[]{(byte) 0x90, (byte) 0x00});
            configurationItemResponse.isActivated();
        });

        ConfigurationItemResponse configurationItemResponse = new ConfigurationItemResponse(
                new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x90, (byte) 0x00}
        );

        Assertions.assertTrue(configurationItemResponse.isActivated());
        Assertions.assertFalse(configurationItemResponse.isUnlocked());
    }

    @Test
    void getId() {
        ConfigurationItemResponse configurationItemResponse = new ConfigurationItemResponse(
                new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x90, (byte) 0x00}
        );

        Assertions.assertEquals((byte) 0x01, configurationItemResponse.getId());
    }
}