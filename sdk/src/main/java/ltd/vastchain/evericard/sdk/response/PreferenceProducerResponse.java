package ltd.vastchain.evericard.sdk.response;

import org.apache.commons.lang3.ArrayUtils;


public class PreferenceProducerResponse extends Response {

    private final byte[] orderId; // 8 bytes
    private final byte[] clientId; // 2 bytes
    private final byte[] internalSerialNumber; // 3 bytes
    private final byte[] chipVersionNumber; // 2 bytes
    private final byte cosVersionNumber;
    private final byte privateVersionNumber;
    private final byte lifecycleNumber;


    public PreferenceProducerResponse(byte[] raw) {
        super(raw);

        byte[] content = this.getContent();
        orderId = ArrayUtils.subarray(content, 0, 8);
        clientId = ArrayUtils.subarray(content, 8, 10);
        internalSerialNumber = ArrayUtils.subarray(content, 10, 13);
        chipVersionNumber = ArrayUtils.subarray(content, 13, 15);
        cosVersionNumber = raw[15];
        privateVersionNumber = raw[16];
        lifecycleNumber = raw[17];
    }

    public byte[] getOrderId() {
        return orderId;
    }

    public byte[] getClientId() {
        return clientId;
    }

    public byte[] getInternalSerialNumber() {
        return internalSerialNumber;
    }

    public byte[] getChipVersionNumber() {
        return chipVersionNumber;
    }

    public byte getCosVersionNumber() {
        return cosVersionNumber;
    }

    public byte getPrivateVersionNumber() {
        return privateVersionNumber;
    }

    public byte getLifecycleNumber() {
        return lifecycleNumber;
    }
}