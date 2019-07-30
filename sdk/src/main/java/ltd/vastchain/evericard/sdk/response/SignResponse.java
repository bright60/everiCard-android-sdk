package ltd.vastchain.evericard.sdk.response;

public class SignResponse extends Response {

    public SignResponse(byte[] raw) {
        super(raw);
    }

    public byte[] getSignature() {
        return this.getContent();
    }
}