package ltd.vastchain.evericard.sdk.utils;

import org.spongycastle.crypto.digests.SHA1Digest;

public class Utils {
    public static byte[] sha1(byte[] inputs) {
        SHA1Digest digest = new SHA1Digest();
        digest.update(inputs, 0, inputs.length);
        byte[] ret = new byte[]{};
        digest.doFinal(ret, 20);
        return ret;
    }
}
