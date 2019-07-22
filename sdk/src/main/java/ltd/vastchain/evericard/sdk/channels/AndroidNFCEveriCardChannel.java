package ltd.vastchain.evericard.sdk.channels;

import android.os.Parcelable;

import ltd.vastchain.evericard.sdk.command.CommandInterface;

public class AndroidNFCEveriCardChannel extends EveriCardChannel {
    private Parcelable p;

    public AndroidNFCEveriCardChannel(Parcelable p) {
        this.p = p;
    }

    @Override
    public byte[] sendCommand(CommandInterface command) {
        return new byte[0];
    }
}
