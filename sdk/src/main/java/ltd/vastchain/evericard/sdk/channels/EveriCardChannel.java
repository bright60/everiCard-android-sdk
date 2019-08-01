package ltd.vastchain.evericard.sdk.channels;

import ltd.vastchain.evericard.sdk.VCChipException;
import ltd.vastchain.evericard.sdk.command.CommandInterface;

abstract public class EveriCardChannel {
    public abstract byte[] sendCommand(CommandInterface command) throws VCChipException;
}
