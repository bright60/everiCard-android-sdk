package ltd.vastchain.evericard.sdk.response;

public interface ResponseInterface {
    boolean isSuccessful();

    byte[] getContent();

    byte[] getStatus();
}
