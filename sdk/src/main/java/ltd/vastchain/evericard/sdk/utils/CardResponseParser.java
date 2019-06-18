package ltd.vastchain.evericard.sdk.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

import androidx.annotation.NonNull;
import ltd.vastchain.evericard.sdk.VCChipException;

public class CardResponseParser {
    /**
     * Get status code from
     * @param rawResponse
     * @return
     */
    public static short getResponseStatusCode(@NonNull byte[] rawResponse) {
        if (rawResponse.length < 2) throw new IllegalStateException();

        return ByteBuffer.wrap(Arrays.copyOfRange(rawResponse, rawResponse.length - 2, rawResponse.length - 1)).getShort();
    }
}
