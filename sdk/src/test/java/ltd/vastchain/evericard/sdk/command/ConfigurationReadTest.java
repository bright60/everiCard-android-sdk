package ltd.vastchain.evericard.sdk.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ltd.vastchain.evericard.sdk.utils.Utils;


class ConfigurationReadTest {

    @Test
    void readConfiguration() {
        ConfigurationRead configurationRead = ConfigurationRead.readConfigurationItem((byte) 0x08);
        Assertions.assertEquals("8011880000", Utils.HEX.encode(configurationRead.getBytes()));
    }

    @Test
    void readConfigurationData() {
        ConfigurationRead configurationRead = ConfigurationRead.readConfigurationItemData((byte) 0x08);
        Assertions.assertEquals("8011880100", Utils.HEX.encode(configurationRead.getBytes()));
    }
}