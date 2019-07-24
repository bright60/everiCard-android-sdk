package ltd.vastchain.evericard.sdk;

import io.everitoken.sdk.java.PublicKey;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
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
        Response res = new Response(ret);

        if (!res.isSuccessful()) {
            throw new VCChipException("get_publicKey_fail", "could not get public key");
        }

        return new PublicKey(res.getContent());
    }
}
