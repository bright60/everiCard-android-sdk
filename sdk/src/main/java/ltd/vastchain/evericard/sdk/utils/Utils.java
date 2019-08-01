package ltd.vastchain.evericard.sdk.utils;

import com.google.common.io.BaseEncoding;

import org.bitcoinj.core.Sha256Hash;

import java.security.SecureRandom;

public class Utils {
    public static final BaseEncoding HEX = BaseEncoding.base16().lowerCase();

    public static String random32BytesAsHex() {
        byte[] randomBytes = random32Bytes();
        return HEX.encode(randomBytes);
    }

    public static byte[] hash(byte[] data) {
        return Sha256Hash.hash(data);
    }

    public static byte[] random32Bytes() {
        SecureRandom random = new SecureRandom();
        byte[] values = new byte[32];
        random.nextBytes(values);
        return values;
    }
}
