package ltd.vastchain.evericard.sdk.channels;

import android.os.Parcelable;

public class AndroidNFCEveriCardChannel extends EveriCardChannel {
    private Parcelable p;

    public AndroidNFCEveriCardChannel(Parcelable p) {
        this.p = p;
    }

    @Override
    public byte[] sendCommand(byte[] request) {
        return new byte[0];
    }
}
