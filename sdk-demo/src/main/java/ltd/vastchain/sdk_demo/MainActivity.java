package ltd.vastchain.sdk_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ltd.vastchain.evericard.sdk.Card;
import ltd.vastchain.evericard.sdk.CardManager;
import ltd.vastchain.evericard.sdk.VCChipException;

public class MainActivity extends AppCompatActivity implements CardManager.OnCardSwipeListener {
    private CardManager cardManager;
    private Button getPubKey0;
    private Button getDisplayName;
    private Button setDisplayName;
    private EditText commandInput;
    private TextView outputText;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getPubKey0 = (Button) findViewById(R.id.getPubKey0);
        getDisplayName = (Button) findViewById(R.id.getDisplayName);
        setDisplayName = (Button) findViewById(R.id.setDisplayName);
        outputText = (TextView) findViewById(R.id.outputText);
        outputText.setTextIsSelectable(true);

        this.cardManager = new CardManager(this);
        getPubKey0.setOnClickListener((v) -> {
            this.handleClick(this, "get_pubkey_0");
        });
        getDisplayName.setOnClickListener((v) -> {
            this.handleClick(this, "get_display_name");
        });
        setDisplayName.setOnClickListener((v) -> {
            this.handleClick(this, "set_display_name");
        });

        cardManager.setOnCardSwipeListener(this);
    }

    private void handleClick(Context ctx, String command) {
        try {
            if (card != null) {
                if (command.equals("get_pubkey_0")) {
                    this.outputText.setText(card.getPublicKeyByIndex(0).toString());
                } else if (command.equals("get_display_name")) {
                    this.outputText.setText(card.getDisplayName());
                } else if (command.equals("set_display_name")) {
                    card.setDisplayName("杭州宇链科技有限公司");
                } else {
                    Toast.makeText(this, String.format("Command '%s' is not handled", command), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Card is not available", Toast.LENGTH_LONG).show();
            }
        } catch (VCChipException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
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
        this.card = card;

        return false;
    }
}
