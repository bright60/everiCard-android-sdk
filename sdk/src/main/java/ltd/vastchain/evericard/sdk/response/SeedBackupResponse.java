package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;
import org.spongycastle.crypto.digests.SHA256Digest;

import java.util.Arrays;

public class SeedBackupResponse extends Response {

    private final byte flag;
    private final byte cnt;
    private final byte[] seed; // 32 bytes
    private final byte[] mac; // 4 bytes

    public SeedBackupResponse(byte[] raw) {
        super(raw);

        byte[] content = this.getContent();
        this.flag = content[0];
        this.cnt = content[1];
        this.seed = ArrayUtils.subarray(content, 2, 2 + 32);
        this.mac = ArrayUtils.subarray(content, content.length - 4, content.length);
    }

    // a network call to vastchain cloud
    public boolean isValid() {
        // TODO, current implementation is wrong. Need to implement own hash function
        SHA256Digest sha256Digest = new SHA256Digest();
        sha256Digest.update(seed, 0, seed.length);
        byte[] output = new byte[32];
        sha256Digest.doFinal(output, 0);

        return Arrays.equals(ArrayUtils.subarray(output, 0, 4), this.mac);
    }

    public byte getFlag() {
        return flag;
    }

    public byte getCnt() {
        return cnt;
    }

    public byte[] getSeed() {
        return seed;
    }

    public byte[] getMac() {
        return mac;
    }
}