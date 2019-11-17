package ltd.vastchain.evericard.sdk;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.everitoken.sdk.java.EvtLink;
import io.everitoken.sdk.java.PublicKey;
import io.everitoken.sdk.java.Signature;
import io.everitoken.sdk.java.Utils;
import io.everitoken.sdk.java.dto.Transaction;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
import ltd.vastchain.evericard.sdk.command.ConfigurationRead;
import ltd.vastchain.evericard.sdk.command.ConfigurationWrite;
import ltd.vastchain.evericard.sdk.command.CreationEnd;
import ltd.vastchain.evericard.sdk.command.IdentityIssuerRead;
import ltd.vastchain.evericard.sdk.command.IdentityProducerRead;
import ltd.vastchain.evericard.sdk.command.ModifyPin;
import ltd.vastchain.evericard.sdk.command.PreferenceProducerRead;
import ltd.vastchain.evericard.sdk.command.PrivateKeyFileConfigure;
import ltd.vastchain.evericard.sdk.command.PrivateKeyFileCreate;
import ltd.vastchain.evericard.sdk.command.PublicKeyRead;
import ltd.vastchain.evericard.sdk.command.SeedBackup;
import ltd.vastchain.evericard.sdk.command.SignEvtLink;
import ltd.vastchain.evericard.sdk.command.SignHash;
import ltd.vastchain.evericard.sdk.command.VerifyPin;
import ltd.vastchain.evericard.sdk.response.ConfigurationResponse;
import ltd.vastchain.evericard.sdk.response.IdentityIssuerResponse;
import ltd.vastchain.evericard.sdk.response.IdentityProducerResponse;
import ltd.vastchain.evericard.sdk.response.PreferenceProducerResponse;
import ltd.vastchain.evericard.sdk.response.Response;
import ltd.vastchain.evericard.sdk.response.SeedBackupResponse;

public class Card {
    private EveriCardChannel channel;

    public Card(EveriCardChannel channel) {
        this.channel = channel;
    }

    public void createKeyWithIndexAndSymbolId(int index, int symbolId, boolean pinProtected) throws VCChipException {
        PrivateKeyFileCreate command = PrivateKeyFileCreate.setByIndexAndSymbolId(index, symbolId, pinProtected);

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);
        byte[] status = res.getStatus();
        String statusText = Utils.HEX.encode(status);

        if (!res.isSuccessful()) {
            throw new VCChipException("create_key_with_index_symbolId", String.format("Failed to create key for symbol %d at index %d (%s)", symbolId, index, statusText));
        }
    }

    public void confitureKeyWithIndex(int index) throws VCChipException {
        PrivateKeyFileConfigure command = PrivateKeyFileConfigure.generateInternalKey(index);

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("configure_key_with_index", String.format("Failed to set key for index %d", index));
        }
    }

    public PublicKey getPublicKeyByIndexAndSymbolId(int keyIndex, int symbolId) throws VCChipException {
        PublicKeyRead publicKeyRead = PublicKeyRead.byIndexAndSymbolId(keyIndex, symbolId);

        byte[] ret = channel.sendCommand(publicKeyRead);
        Response res = Response.of(ret);
        String statusText = Utils.HEX.encode(res.getStatus());

        if (!res.isSuccessful()) {
            throw new VCChipException("get_publicKey_fail", String.format("could not get public key at index %s (%s)", keyIndex, statusText));
        }

        return new PublicKey(res.getContent());
    }

    public PublicKey getPublicKeyByIndex(int keyIndex) throws VCChipException {
        PublicKeyRead publicKeyRead = PublicKeyRead.byIndex(keyIndex);

        byte[] ret = channel.sendCommand(publicKeyRead);
        Response res = Response.of(ret);
        String statusText = Utils.HEX.encode(res.getStatus());

        if (!res.isSuccessful()) {
            throw new VCChipException("get_publicKey_fail", String.format("could not get public key at index %s (%s)", keyIndex, statusText));
        }

        return new PublicKey(res.getContent());
    }

    public String getDisplayName() throws VCChipException {
        ConfigurationRead read = ConfigurationRead.readConfigurationItemData((byte) 0x0a);

        byte[] ret = channel.sendCommand(read);
        ConfigurationResponse res = new ConfigurationResponse(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("get_display_name_failed", "Failed to get display name");
        }

        return new String(res.getConfigurationData(), StandardCharsets.UTF_8);
    }

    public void setDisplayName(String name) throws VCChipException {
        ConfigurationWrite command = ConfigurationWrite.configureSettings(Arrays.asList(
                ConfigurationWrite.createTLVSetting((byte) 0x0a, name.getBytes())
        ), true);

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("set_display_name_failed", String.format("Failed to set display name to %s (%s).", name, Utils.HEX.encode(res.getStatus())));
        }
    }

    public void setSymbolData(int slotId, int symbolId, int precision, long maxAllowedAmount) throws VCChipException {
        if (slotId < 0 || slotId > 2) {
            throw new IllegalArgumentException("Slot Id can only within 0~2");
        }
        if (symbolId < 0) {
            throw new IllegalArgumentException("Symbol Id must be 0 or a positive number.");
        }

        if (precision < 0 || precision > 18) {
            throw new IllegalArgumentException("Precision can only within 0~18");
        }

        if (maxAllowedAmount < 0 && maxAllowedAmount != -1) {
            throw new IllegalArgumentException("Max Allowed Amount is not valid.");
        }

        byte[] bytes = ByteBuffer.allocate(14).put((byte) slotId).putInt(symbolId).put((byte) precision).putLong(maxAllowedAmount).array();

        ConfigurationWrite command = ConfigurationWrite.configureSettings(Arrays.asList(
                ConfigurationWrite.createTLVSetting((byte) 0x0d, bytes)
        ), true);

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("set_symbol_data_failed", String.format("Failed to set symbol data (%s).", Utils.HEX.encode(res.getStatus())));
        }
    }

    public boolean verifyPin(String pinInHex) throws VCChipException {
        VerifyPin command = VerifyPin.of(Utils.HEX.decode(pinInHex));

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        return res.isSuccessful();
    }

    public boolean modifyPin(byte[] oldPin, byte[] newPin) throws VCChipException {
        ModifyPin command = ModifyPin.of(oldPin, newPin);
        byte[] ret = channel.sendCommand(command);

        Response res = Response.of(ret);

        return res.isSuccessful();
    }

    public String getIdentityProducer() throws VCChipException {
        byte[] random = Utils.random32Bytes();
        IdentityProducerRead command = IdentityProducerRead.of(random);

        byte[] ret = channel.sendCommand(command);
        IdentityProducerResponse res = new IdentityProducerResponse(ret, random);

        if (!res.isSuccessful()) {
            throw new VCChipException("get_identity_producer_failed", "Failed to get producer identity");
        }

        return Utils.HEX.encode(res.getContent());
    }

    public String getIdentityIssuer() throws VCChipException {
        byte[] random = Utils.random32Bytes();
        IdentityIssuerRead command = IdentityIssuerRead.of(random);

        byte[] ret = channel.sendCommand(command);
        IdentityIssuerResponse res = new IdentityIssuerResponse(ret, random);
        return Utils.HEX.encode(res.getContent());
    }

    public String getPreferenceProducer() throws VCChipException {
        PreferenceProducerRead command = new PreferenceProducerRead();

        byte[] ret = channel.sendCommand(command);
        PreferenceProducerResponse res = new PreferenceProducerResponse(ret);
        return Utils.HEX.encode(res.getContent());
    }

    public void endCreation() throws VCChipException {
        CreationEnd command = new CreationEnd();
        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("creation_end_failed", String.format("Failed to on creation end command (%s).", Utils.HEX.encode(res.getStatus())));
        }
    }

    public String getSeedBackup() throws VCChipException {
        SeedBackup command = new SeedBackup();
        byte[] ret = channel.sendCommand(command);
        SeedBackupResponse res = new SeedBackupResponse(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("backup_seed_failed", String.format("Failed to back up seed (%s).", Utils.HEX.encode(res.getStatus())));
        }

        return Utils.HEX.encode(res.getSeed());
    }

    public Signature signHash(byte[] hash, int keyIndex) throws VCChipException {
        SignHash command = SignHash.of(keyIndex, hash);
        PublicKey publicKey = getPublicKeyByIndex(keyIndex);

        Signer signer = new Signer(channel);
        return signer.sign(command, publicKey, true);
    }

    // TODO
    public List<Signature> signTransaction(Transaction trx, int keyIndex) {
        return new ArrayList<>();
    }

    public Signature signEvtLink(String evtLink, int keyIndex, int symbolId) throws VCChipException {
        SignEvtLink command = SignEvtLink.of(keyIndex, EvtLink.decode(evtLink));
        PublicKey publicKey = getPublicKeyByIndexAndSymbolId(keyIndex, symbolId);
        Signer signer = new Signer(channel);
        return signer.sign(command, publicKey, false);
    }
}
