package ltd.vastchain.evericard.sdk.channels;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Parcelable;

import java.io.IOException;

import ltd.vastchain.evericard.sdk.command.CommandInterface;

public class AndroidNFCEveriCardChannel extends EveriCardChannel {
    private Parcelable p;

    public AndroidNFCEveriCardChannel(Parcelable p) {
        this.p = p;
    }

    @Override
    public byte[] sendCommand(CommandInterface command) {
        Tag tag = (Tag) p;
        final IsoDep isoDep = IsoDep.get(tag);
        byte[] ret = new byte[]{};
        if (isoDep != null) {
            try {
                isoDep.connect();
                ret = isoDep.transceive(command.getBytes());
                isoDep.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}
