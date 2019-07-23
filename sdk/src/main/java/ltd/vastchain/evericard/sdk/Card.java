package ltd.vastchain.evericard.sdk;

import java.util.Arrays;

import io.everitoken.sdk.java.PublicKey;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
import ltd.vastchain.evericard.sdk.command.Command;
import ltd.vastchain.evericard.sdk.utils.CardResponseParser;

public class Card {
    private EveriCardChannel channel;

    public Card(EveriCardChannel channel) {
        this.channel = channel;
    }

    public PublicKey getPublicKey(int keyIndex) throws VCChipException {
        Command command = new Command((byte) 0x0b, (byte) 0x00, (byte) 0x00, (byte) 0x41); //"800b000041";

        byte[] ret = channel.sendCommand(command);
        short statusCode = CardResponseParser.getResponseStatusCode(ret);

        if (statusCode == (short) 0x9000) {
            PublicKey key = new PublicKey(Arrays.copyOfRange(ret, 0, ret.length - 2));
            return key;
        } else {
            throw new VCChipException("get_publicKey_fail", "could not get public key");

        }
    }
}
