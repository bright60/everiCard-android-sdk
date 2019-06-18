package ltd.vastchain.sdk_demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ltd.vastchain.evericard.sdk.Card;
import ltd.vastchain.evericard.sdk.CardManager;
import ltd.vastchain.evericard.sdk.VCChipException;

public class MainActivity extends AppCompatActivity implements CardManager.OnCardSwipeListener {
    private CardManager cardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cardManager = new CardManager(this);
        cardManager.setOnCardSwipeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cardManager.onActivityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
         cardManager.onActivityPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        cardManager.onActivityNewIntent(intent);
    }

    @Override
    public boolean onCardSwipe(CardManager cardManager, Card card) {
        try {
            Toast.makeText(this, "卡已连接", Toast.LENGTH_LONG).show();
            Toast.makeText(this, card.getPublicKey(0).toString(), Toast.LENGTH_LONG).show();
        } catch (VCChipException e) {
            e.printStackTrace();
        }
        return false;
    }
}
