package ltd.vastchain.evericard.sdk;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import io.everitoken.sdk.java.PublicKey;
import io.everitoken.sdk.java.Utils;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
import ltd.vastchain.evericard.sdk.command.ConfigurationRead;
import ltd.vastchain.evericard.sdk.command.ConfigurationWrite;
import ltd.vastchain.evericard.sdk.command.PublicKeyRead;
import ltd.vastchain.evericard.sdk.response.Response;

public class Card {
    private EveriCardChannel channel;

    public Card(EveriCardChannel channel) {
        this.channel = channel;
    }

    public PublicKey getPublicKeyByIndex(int keyIndex) throws VCChipException {
        PublicKeyRead publicKeyRead = PublicKeyRead.byIndex(keyIndex);

        byte[] ret = channel.sendCommand(publicKeyRead);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("get_publicKey_fail", String.format("could not get public key at index %s", keyIndex));
        }

        return new PublicKey(res.getContent());
    }

    public String getDisplayName() throws VCChipException {
        ConfigurationRead read = ConfigurationRead.readConfigurationItemData((byte) 0x0a);

        byte[] ret = channel.sendCommand(read);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("get_display_name_failed", "Failed to get display name");
        }

        String utf = new String(ArrayUtils.subarray(res.getContent(), 4, res.getContent().length), StandardCharsets.UTF_8);
        return StringEscapeUtils.unescapeXml(utf);
    }

    public void setDisplayName(String name) throws VCChipException {
        // TODO: validate name a bit

        ConfigurationWrite command = ConfigurationWrite.configureSettings(Arrays.asList(
                ConfigurationWrite.createTLVSetting((byte) 0x0a, StringEscapeUtils.escapeXml(name).getBytes())
        ), true);

        byte[] ret = channel.sendCommand(command);
        Response res = Response.of(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("set_display_name_failed", String.format("Failed to set display name to %s (%s).", name, Utils.HEX.encode(res.getStatus())));
        }
    }
}
