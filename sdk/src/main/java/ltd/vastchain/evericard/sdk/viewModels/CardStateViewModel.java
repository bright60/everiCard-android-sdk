package ltd.vastchain.evericard.sdk.viewModels;

import android.app.Application;
import android.app.PendingIntent;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import androidx.lifecycle.AndroidViewModel;

public class CardStateViewModel extends AndroidViewModel {
    public boolean isInitialized;
    public NfcAdapter nfcAdapter;
    public PendingIntent pendingIntent;
    public Parcelable parcelableExtra;

    public CardStateViewModel(Application application) {
        super(application);
    }
}
