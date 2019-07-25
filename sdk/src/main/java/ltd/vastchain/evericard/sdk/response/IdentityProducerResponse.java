package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;

public class IdentityProducerResponse extends Response {

    private final byte[] random;
    private final byte[] chipId; // 8 bytes
    private final byte[] chipPublicKey; // 65 bytes
    private final byte[] producerSignature; // 64 bytes
    private final byte[] signature; // 64 bytes

    public IdentityProducerResponse(byte[] raw, byte[] random) {
        super(raw);
        this.random = random;

        byte[] content = this.getContent();
        this.chipId = ArrayUtils.subarray(content, 0, 8);
        this.chipPublicKey = ArrayUtils.subarray(content, 8, 8 + 65);
        this.producerSignature = ArrayUtils.subarray(content, 8 + 65, 8 + 65 + 64);
        this.signature = ArrayUtils.subarray(content, 8 + 65 + 64, content.length);
    }

    public byte[] getRandom() {
        return random;
    }

    public byte[] getChipId() {
        return chipId;
    }

    public byte[] getChipPublicKey() {
        return chipPublicKey;
    }

    public byte[] getProducerSignature() {
        return producerSignature;
    }

    public byte[] getSignature() {
        return signature;
    }
}