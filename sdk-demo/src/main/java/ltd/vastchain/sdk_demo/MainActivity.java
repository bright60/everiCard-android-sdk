package ltd.vastchain.sdk_demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.Arrays;
import java.util.Map;

import io.everitoken.sdk.java.Api;
import io.everitoken.sdk.java.PublicKey;
import io.everitoken.sdk.java.Signature;
import io.everitoken.sdk.java.abi.TransferFungibleAction;
import io.everitoken.sdk.java.dto.NodeInfo;
import io.everitoken.sdk.java.dto.Transaction;
import io.everitoken.sdk.java.dto.TransactionData;
import io.everitoken.sdk.java.dto.TransactionDigest;
import io.everitoken.sdk.java.exceptions.ApiResponseException;
import io.everitoken.sdk.java.param.TestNetNetParams;
import io.everitoken.sdk.java.service.TransactionConfiguration;
import io.everitoken.sdk.java.service.TransactionService;
import ltd.vastchain.evericard.sdk.Card;
import ltd.vastchain.evericard.sdk.CardManager;
import ltd.vastchain.evericard.sdk.VCChipException;
import ltd.vastchain.evericard.sdk.utils.Utils;

public class MainActivity extends AppCompatActivity implements CardManager.OnCardSwipeListener {
    private CardManager cardManager;
    private Button getPubKey0;
    private Button getDisplayName;
    private Button setDisplayName;
    private Button verifyPin;
    private Button modifyPin;
    private Button getIdentityProducer;
    private Button getPrefProducer;
    private Button getIdentityIssuer;
    private Button creationEnd;
    private Button seedBackup;
    private Button signHash;
    private Button signEvtLink;
    private Button setSymbolData;
    private Button transferFt;
    private Button createKeyByIndexAndSymbolId;
    private Button configureKeyByIndex;
    private FloatingActionButton settingBtn;
    private TextView outputText;
    private Card card;
    private String log = "";
    private Map<String, ?> pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getPubKey0 = findViewById(R.id.getPubKey0);
        getDisplayName = findViewById(R.id.getDisplayName);
        setDisplayName = findViewById(R.id.setDisplayName);
        verifyPin = findViewById(R.id.verifyPin);
        modifyPin = findViewById(R.id.modifyPin);
        getIdentityProducer = findViewById(R.id.getIdentityProducer);
        getPrefProducer = findViewById(R.id.getPrefProducer);
        getIdentityIssuer = findViewById(R.id.getIdentityIssuer);
        creationEnd = findViewById(R.id.creationEnd);
        seedBackup = findViewById(R.id.seedBackup);
        signHash = findViewById(R.id.signHash);
        signEvtLink = findViewById(R.id.signEvtLink);
        setSymbolData = findViewById(R.id.setSymbolData);
        transferFt = findViewById(R.id.transferFt);
        outputText = findViewById(R.id.outputText);
        createKeyByIndexAndSymbolId = findViewById(R.id.keyCreateByIndexAndSymbolId);
        configureKeyByIndex = findViewById(R.id.keyConfigureByIndex);
        settingBtn = findViewById(R.id.settingBtn);

        outputText.setTextIsSelectable(true);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        pref = shared.getAll();

        this.cardManager = new CardManager(this);

        settingBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
        getPubKey0.setOnClickListener((v) -> this.handleClick(this, "get_pubkey_0"));
        getDisplayName.setOnClickListener((v) -> this.handleClick(this, "get_display_name"));
        setDisplayName.setOnClickListener((v) -> this.handleClick(this, "set_display_name"));
        setSymbolData.setOnClickListener((v) -> this.handleClick(this, "set_symbol_data"));
        verifyPin.setOnClickListener(v -> this.handleClick(this, "verify_pin"));
        getIdentityProducer.setOnClickListener(v -> this.handleClick(this, "get_identity_producer"));
        getPrefProducer.setOnClickListener(v -> this.handleClick(this, "get_pref_producer"));
        getIdentityIssuer.setOnClickListener(v -> this.handleClick(this, "get_identity_issuer"));
        creationEnd.setOnClickListener(v -> this.handleClick(this, "creation_end"));
        seedBackup.setOnClickListener(v -> this.handleClick(this, "seed_backup"));
        signHash.setOnClickListener(v -> this.handleClick(this, "sign_hash"));
        signEvtLink.setOnClickListener(v -> this.handleClick(this, "sign_evtlink"));
        modifyPin.setOnClickListener(v -> this.handleClick(this, "modify_pin"));
        transferFt.setOnClickListener(v -> this.handleClick(this, "transferft"));
        createKeyByIndexAndSymbolId.setOnClickListener(v -> this.handleClick(this, "create_key_by_index_symbolId"));
        configureKeyByIndex.setOnClickListener(v -> this.handleClick(this, "configure_key_by_index"));

        cardManager.setOnCardSwipeListener(this);
    }

    private void handleClick(Context ctx, String command) {

        int index = 0;
        int symbolId = 1;

        String keyIndex = (String) pref.get("keyIndex");
        String symbolIdFromSetting = (String) pref.get("symbolId");
        if (keyIndex != null) {
            index = Integer.valueOf(keyIndex);
        }

        if (symbolIdFromSetting != null) {
            symbolId = Integer.valueOf(symbolIdFromSetting);
        }

        try {
            if (card != null) {
                if (command.equals("get_pubkey_0")) {
                    this.outputText.setText(card.getPublicKeyByIndex(index).toString());
                } else if (command.equals("get_display_name")) {
                    this.outputText.setText(StringEscapeUtils.unescapeXml(card.getDisplayName()));
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
                                    card.setDisplayName(StringEscapeUtils.escapeXml(userInputValue));
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
                } else if (command.equals("set_symbol_data")) {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("Set Symbol data");
                    inputAlert.setMessage("Format: Slot Id,Symbol Id,Precision,Max Allowed");
                    final EditText userInput = new EditText(ctx);
                    inputAlert.setView(userInput);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String input = userInput.getText().toString();
                            if (input.length() > 0) {
                                try {
                                    String[] parts = input.split(",");
                                    card.setSymbolData(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]), Integer.parseInt(parts[2]), Long.valueOf(parts[3]));
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
                            try {
                                boolean success = card.verifyPin(userInputValue);
                                outputText.setText(success ? "Verified" : "Invalid");
                            } catch (VCChipException ex) {
                                Toast.makeText(ctx, ex.toString(), Toast.LENGTH_LONG).show();
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
                } else if (command.equals("get_identity_producer")) {
                    this.outputText.setText(card.getIdentityProducer());
                } else if (command.equals("get_pref_producer")) {
                    this.outputText.setText(card.getPreferenceProducer());
                } else if (command.equals("get_identity_issuer")) {
                    this.outputText.setText(card.getIdentityIssuer());
                } else if (command.equals("creation_end")) {
                    card.endCreation();
                } else if (command.equals("seed_backup")) {
                    this.outputText.setText(card.getSeedBackup());
                } else if (command.equals("modify_pin")) {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("Modify Pin");
                    inputAlert.setMessage("Format: oldPin,newPin (hex)");
                    final EditText pin = new EditText(ctx);
                    inputAlert.setView(pin);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String pinText = pin.getText().toString();
                            String[] pins = pinText.split(",");
                            byte[] oldPinHex = Utils.HEX.decode(pins[0]);
                            byte[] newPinHex = Utils.HEX.decode(pins[1]);
                            try {
                                boolean success = card.modifyPin(oldPinHex, newPinHex);
                                outputText.setText(success ? "Success" : "Failed");
                            } catch (VCChipException ex) {
                                Toast.makeText(ctx, ex.toString(), Toast.LENGTH_LONG).show();
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
                } else if (command.equals("sign_evtlink")) {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("Sign EvtLink");
                    inputAlert.setMessage("Input evtlink");
                    final EditText hash = new EditText(ctx);
                    inputAlert.setView(hash);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String evtlink = hash.getText().toString();
                            try {
                                Signature signature = card.signEvtLink(evtlink, 0, 207);
                                outputText.setText(signature.toString());
                            } catch (VCChipException e) {
                                Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
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
                } else if (command.equals("sign_hash")) {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(ctx);
                    inputAlert.setTitle("Sign Hash");
                    inputAlert.setMessage("Input the signing hash in hex");
                    final EditText hash = new EditText(ctx);
                    inputAlert.setView(hash);
                    inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String signingHash = hash.getText().toString();
                            try {
                                Signature signature = card.signHash(Utils.hash(Utils.HEX.decode(signingHash)), 0);
                                outputText.setText(signature.toString());
                            } catch (VCChipException e) {
                                Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
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
                } else if (command.equals("transferft")) {
                    new Thread(() -> {
                        outputText.setText("started");
                        transferFungibleToken();
                    }).start();
                } else if (command.equals("create_key_by_index_symbolId")) {
                    card.createKeyWithIndexAndSymbolId(index, symbolId, false);
                    outputText.setText(String.format("Created key with index (%s) and symbolId (%s)", index, symbolId));
                } else if (command.equals("configure_key_by_index")) {
                    card.confitureKeyWithIndex(index);
                    outputText.setText(String.format("Set key with index %d", index));
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

    private void transferFungibleToken() {
        TestNetNetParams netParams = new TestNetNetParams();
        outputText.setText("");

        try {
            NodeInfo nodeInfo = new Api(netParams).getInfo();

            writeLog("created nodeInfo");
            TransferFungibleAction transferFungibleAction = TransferFungibleAction.of("1.00000 S#207",
                    "EVT5EQiiyU9RoZkQUMnoAqqT86wHuDW5T37QqFDuCMPpPXVY3sw2e",
                    "EVT6Qz3wuRjyN6gaU3P3XRxpnEZnM4oPxortemaWDwFRvsv2FxgND", "test offline sign");

            writeLog("created transferFungibleAction");
            // Init transaction service with net parameters
            TransactionService transactionService = TransactionService.of(netParams);

            // Construct transaction configuration
            TransactionConfiguration trxConfig = TransactionConfiguration.of(nodeInfo, 1000000,
                    PublicKey.of("EVT5EQiiyU9RoZkQUMnoAqqT86wHuDW5T37QqFDuCMPpPXVY3sw2e"), false, null);
            writeLog("created trxConfig");

            // Construct raw transaction
            Transaction rawTrx = transactionService.buildRawTransaction(trxConfig, Arrays.asList(transferFungibleAction),
                    true);
            writeLog("built rawTrx");

            // get signatures from offline wallet rpc
            TransactionDigest trxDigest = TransactionService.getTransactionSignableDigest(netParams, rawTrx);
            writeLog(String.format("getting hash: %s", Utils.HEX.encode(trxDigest.getDigest())));

            Signature signature = card.signHash(trxDigest.getDigest(), 0);
            writeLog(signature.toString());

            // Push the raw transaction together with the signature to chain
            TransactionData push = transactionService.push(rawTrx, Arrays.asList(signature.toString()));

            writeLog(push.getTrxId());
        } catch (ApiResponseException ex) {
            writeLog(ex.getRaw().toJSONString());
        } catch (VCChipException ex) {
            writeLog(ex.toString());
        }

    }

    private void writeLog(String message) {
        if (log.length() == 0) {
            log = message;
        } else {
            log = String.format("%s\n%s", log, message);
        }
        outputText.setText(log);
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
