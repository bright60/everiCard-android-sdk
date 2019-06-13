package ltd.vastchain.evericard.sdk.channels;

abstract class EveriCardChannel {
    public abstract byte[] sendCommand(byte[] request);
}
