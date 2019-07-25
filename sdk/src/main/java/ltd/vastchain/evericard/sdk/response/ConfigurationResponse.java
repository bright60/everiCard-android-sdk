package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

public class ConfigurationResponse extends Response {
    public boolean hasConfigurationData;
    public byte[] configurationData = new byte[]{};

    public ConfigurationResponse(byte[] raw) {
        super(raw);
        byte[] content = this.getContent();
        hasConfigurationData = content.length > 3;

        if (hasConfigurationData) {
            configurationData = ArrayUtils.subarray(content, 3, content.length);
        }
    }

    public boolean isActivated() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("Failed to get settings (Card didn't return data).");
        }

        byte status = ByteBuffer.wrap(this.getContent()).get(1);

        return (int) status == 0;
    }

    public boolean isUnlocked() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("Failed to get settings (Card didn't return data).");
        }

        byte status = ByteBuffer.wrap(this.getContent()).get(2);

        return (int) status == 0;
    }

    public byte getId() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("Failed to get settings (Card didn't return data).");
        }

        return ByteBuffer.wrap(this.getContent()).get(0);
    }

    public byte[] getConfigurationDataWithLength() {
        return configurationData;
    }

    public byte[] getConfigurationData() {
        byte[] data = this.getConfigurationDataWithLength();
        return ArrayUtils.subarray(data, 1, data.length);
    }
}
