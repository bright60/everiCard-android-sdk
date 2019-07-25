package ltd.vastchain.evericard.sdk.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ConfigurationResponseTest {

    @Test
    void statusReadCorrectly() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ConfigurationResponse configurationResponse = new ConfigurationResponse(new byte[]{(byte) 0x90, (byte) 0x00});
            configurationResponse.isActivated();
        });

        ConfigurationResponse configurationResponse = new ConfigurationResponse(
                new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x90, (byte) 0x00}
        );

        Assertions.assertTrue(configurationResponse.isActivated());
        Assertions.assertFalse(configurationResponse.isUnlocked());
    }

    @Test
    void getId() {
        ConfigurationResponse configurationResponse = new ConfigurationResponse(
                new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x90, (byte) 0x00}
        );

        Assertions.assertEquals((byte) 0x01, configurationResponse.getId());
        Assertions.assertTrue(Arrays.equals(configurationResponse.getConfigurationDataWithLength(), new byte[]{}));
    }

    @Test
    void getConfigurationData() {
        ConfigurationResponse configurationResponse = new ConfigurationResponse(
                new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x04, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x90, (byte) 0x00,}
        );

        Assertions.assertEquals(4, configurationResponse.getConfigurationData().length);
    }
}