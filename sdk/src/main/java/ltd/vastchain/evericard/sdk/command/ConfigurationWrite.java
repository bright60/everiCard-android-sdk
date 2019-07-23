package ltd.vastchain.evericard.sdk.command;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class ConfigurationWrite extends Command {
    public static byte INS = (byte) 0x06;

    public ConfigurationWrite(byte ins, byte p1, byte p2, byte lc, byte[] data) {
        super(ins, p1, p2, lc, data);
    }

    public static ConfigurationWrite configureSettings(List<byte[]> settings, boolean hasSettingData) {
        int sum = 0;
        byte[] settingsArray = new byte[]{};

        for (int i = 0; i < settings.size(); i++) {
            sum += settings.get(i).length;
            settingsArray = ArrayUtils.addAll(settingsArray, settings.get(i));
        }

        return new ConfigurationWrite(
                INS,
                (byte) settings.size(),
                hasSettingData ? (byte) 0x01 : (byte) 0x00,
                (byte) sum,
                settingsArray
        );
    }

    public static byte[] createTVSetting(byte id, boolean enable, boolean lock) {
        return new byte[]{id, enable ? (byte) 0x00 : (byte) 0x01, lock ? (byte) 0x01 : (byte) 0x00};
    }

    public static byte[] createTLVSetting(byte id, byte[] settingData) {
        return ArrayUtils.addAll(new byte[]{id, (byte) settingData.length}, settingData);
    }
}
