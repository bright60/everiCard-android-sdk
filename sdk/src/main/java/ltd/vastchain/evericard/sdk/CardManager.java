package ltd.vastchain.evericard.sdk;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import ltd.vastchain.evericard.sdk.viewModels.CardStateViewModel;

public class CardManager {
    private FragmentActivity activity;
    private CardStateViewModel cardState;

    private static String[][] TECH_LISTS;
    private static IntentFilter[] FILTERS;

    static {
        try {
            TECH_LISTS = new String[][]{{IsoDep.class.getName()}};
            FILTERS = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED, "*/*")};
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CardManager(FragmentActivity activity) {
        this.activity = activity;
        this.cardState = ViewModelProviders.of(activity).get(CardStateViewModel.class);
    }

    public void onActivityResume() {
        this.cardState.nfcAdapter.enableForegroundDispatch(activity, this.cardState.pendingIntent, FILTERS, TECH_LISTS);
        // TODO add option to decide whether we should keep screen on
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onActivityPause() {
        this.cardState.nfcAdapter.disableForegroundDispatch(activity);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onActivityNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            this.cardState.parcelableExtra = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    private void initState() {
        if (!this.cardState.isInitialized) {
            this.cardState.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
            this.cardState.pendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            this.cardState.isInitialized = true;
        }
    }
}
