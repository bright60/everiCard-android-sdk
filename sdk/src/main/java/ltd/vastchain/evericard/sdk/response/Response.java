package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Response implements ResponseInterface {
    private final byte[] data;
    private final byte[] status;

    public Response(byte[] raw) {
        if (raw.length < 2) {
            throw new IllegalArgumentException("Raw bytes passed in are invalid.");
        }

        if (raw.length == 2) {
            status = raw;
            data = new byte[]{};
        } else {
            status = ArrayUtils.subarray(raw, raw.length - 2, raw.length);
            data = ArrayUtils.subarray(raw, 0, raw.length - 2);
        }
    }

    @Override
    public boolean isSuccessful() {
        return Arrays.equals(new byte[]{(byte) 0x90, (byte) 0x00}, status);
    }

    @Override
    public byte[] getContent() {
        return data;
    }

    @Override
    public byte[] getStatus() {
        return status;
    }
}
