package ltd.vastchain.evericard.sdk.viewModels;

import android.app.Application;
import android.app.PendingIntent;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import androidx.lifecycle.AndroidViewModel;
import ltd.vastchain.evericard.sdk.channels.AndroidNFCEveriCardChannel;

public class CardStateViewModel extends AndroidViewModel {
    public boolean isInitialized;
    public NfcAdapter nfcAdapter;
    public PendingIntent pendingIntent;
    public AndroidNFCEveriCardChannel cardChannel;

    public CardStateViewModel(Application application) {
        super(application);
    }
}
