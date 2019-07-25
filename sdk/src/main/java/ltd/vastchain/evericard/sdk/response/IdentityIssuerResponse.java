package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;

public class IdentityIssuerResponse extends Response {

    private final byte[] random;
    private final byte[] customId; // 8 bytes
    private final byte[] clientPublicKey; // 65 bytes
    private final byte[] issuerSignature; // 64 bytes
    private final byte[] signature; // 64 bytes

    public IdentityIssuerResponse(byte[] raw, byte[] random) {
        super(raw);
        this.random = random;

        byte[] content = this.getContent();
        this.customId = ArrayUtils.subarray(content, 0, 8);
        this.clientPublicKey = ArrayUtils.subarray(content, 8, 8 + 65);
        this.issuerSignature = ArrayUtils.subarray(content, 8 + 65, 8 + 65 + 64);
        this.signature = ArrayUtils.subarray(content, 8 + 65 + 64, content.length);
    }

    public byte[] getRandom() {
        return random;
    }

    public byte[] getCustomId() {
        return customId;
    }

    public byte[] getClientPublicKey() {
        return clientPublicKey;
    }

    public byte[] getIssuerSignature() {
        return issuerSignature;
    }

    public byte[] getSignature() {
        return signature;
    }
}