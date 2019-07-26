package ltd.vastchain.evericard.sdk.response;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import io.everitoken.sdk.java.Utils;

class SeedBackupResponseTest {

    @Test
    void isValid() {
        ByteBuffer raw = ByteBuffer.allocate(38).put(0, (byte) 0x00).put(1, (byte) 0x01)
                .put(Utils.random32Bytes())
                .put(new byte[]{0, 1, 2, 3});

        SeedBackupResponse seedBackupResponse = new SeedBackupResponse(raw.array());
        seedBackupResponse.isValid();
    }
}