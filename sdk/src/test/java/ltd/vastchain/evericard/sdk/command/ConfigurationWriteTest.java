package ltd.vastchain.evericard.sdk.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import ltd.vastchain.evericard.sdk.utils.Utils;


class ConfigurationWriteTest {

    @Test
    void configureSettings() {
        byte[] tlvSetting = ConfigurationWrite.createTLVSetting((byte) 0x0d, new byte[]{0x00, 0x01});
        byte[] tlvSetting2 = ConfigurationWrite.createTLVSetting((byte) 0x0e, new byte[]{0x03, 0x04});
        ConfigurationWrite configurationWrite = ConfigurationWrite.configureSettings(Arrays.asList(tlvSetting, tlvSetting2), true);
        Assertions.assertEquals("80060201080d0200010e020304", Utils.HEX.encode(configurationWrite.getBytes()));
    }

    @Test
    void createTVSetting() {
        byte[] tvSetting = ConfigurationWrite.createTVSetting((byte) 0x01, true, false);
        Assertions.assertEquals("010000", Utils.HEX.encode(tvSetting));
    }

    @Test
    void createTLVSetting() {
        byte[] tlvSetting = ConfigurationWrite.createTLVSetting((byte) 0x0d, new byte[]{0x00, 0x01});
        Assertions.assertEquals("0d020001", Utils.HEX.encode(tlvSetting));
    }
}