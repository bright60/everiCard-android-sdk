package ltd.vastchain.sdk_demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ltd.vastchain.evericard.sdk.Card;
import ltd.vastchain.evericard.sdk.CardManager;
import ltd.vastchain.evericard.sdk.VCChipException;

public class MainActivity extends AppCompatActivity implements CardManager.OnCardSwipeListener {
    private CardManager cardManager;
    private Button getPubKey0;
    private Button getDisplayName;
    private Button setDisplayName;
    private Button verifyPin;
    private Button getIdentityProducer;
    private Button getPrefProducer;
    private Button getIdentityIssuer;
    private Button creationEnd;
    private TextView outputText;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getPubKey0 = (Button) findViewById(R.id.getPubKey0);
        getDisplayName = (Button) findViewById(R.id.getDisplayName);
        setDisplayName = (Button) findViewById(R.id.setDisplayName);
        verifyPin = (Button) findViewById(R.id.verifyPin);
        getIdentityProducer = (Button) findViewById(R.id.getIdentityProducer);
        getPrefProducer = (Button) findViewById(R.id.getPrefProducer);
        getIdentityIssuer = (Button) findViewById(R.id.getIdentityIssuer);
        creationEnd = (Button) findViewById(R.id.creationEnd);
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
        verifyPin.setOnClickListener(v -> {
            this.handleClick(this, "verify_pin");
        });
        getIdentityProducer.setOnClickListener(v -> {
            this.handleClick(this, "get_identity_producer");
        });
        getPrefProducer.setOnClickListener(v -> {
            this.handleClick(this, "get_pref_producer");
        });
        getIdentityIssuer.setOnClickListener(v -> {
            this.handleClick(this, "get_identity_issuer");
        });
        creationEnd.setOnClickListener(v -> {
            this.handleClick(this, "creation_end");
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
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("Set Display Name");
                    final EditText userInput = new EditText(ctx);
                    inputAlert.setView(userInput);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String userInputValue = userInput.getText().toString();
                            if (userInputValue.length() > 0) {
                                try {
                                    card.setDisplayName(userInputValue);
                                } catch (VCChipException ex) {
                                    Toast.makeText(ctx, ex.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                    inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = inputAlert.create();
                    alertDialog.show();
                } else if (command.equals("verify_pin")) {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("验证密码");
                    inputAlert.setMessage("输入密码的hex值");
                    final EditText userInput = new EditText(ctx);
                    inputAlert.setView(userInput);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String userInputValue = userInput.getText().toString();
                            boolean success = card.verifyPin(userInputValue);
                            outputText.setText(success ? "Verified" : "Invalid");
                        }
                    });
                    inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = inputAlert.create();
                    alertDialog.show();
                } else if (command.equals("get_identity_producer")) {
                    this.outputText.setText(card.getIdentityProducer());
                } else if (command.equals("get_pref_producer")) {
                    this.outputText.setText(card.getPreferenceProducer());
                } else if (command.equals("get_identity_issuer")) {
                    this.outputText.setText(card.getIdentityIssuer());
                } else if (command.equals("creation_end")) {
                    card.endCreation();
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
