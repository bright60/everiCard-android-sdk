package ltd.vastchain.evericard.sdk.channels;

abstract public class EveriCardChannel {
    public abstract byte[] sendCommand(byte[] request);
}
