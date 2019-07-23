package ltd.vastchain.evericard.sdk.response;

import java.nio.ByteBuffer;

public class ConfigurationItemResponse extends Response {
    public ConfigurationItemResponse(byte[] raw) {
        super(raw);
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
}
