package ltd.vastchain.evericard.sdk.command;

public class ConfigurationRead extends Command {
    public static final byte INS = (byte) 0x11;
    public static final byte CLA = (byte) 0x80;

    public ConfigurationRead(byte p1, byte p2) {
        super(CLA, INS, p1, p2, (byte) 0x00);
    }

    public static ConfigurationRead readConfigurationItem(byte item) {
        return new ConfigurationRead(Command.flagConfigurationItem(item), (byte) 0x00);
    }

    public static ConfigurationRead readConfigurationItemData(byte item) {
        return new ConfigurationRead(Command.flagConfigurationItem(item), (byte) 0x01);
    }
}
