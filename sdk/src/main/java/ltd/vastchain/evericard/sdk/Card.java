package ltd.vastchain.evericard.sdk;

import io.everitoken.sdk.java.PublicKey;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;

public class Card {
    private EveriCardChannel channel;

    public Card(EveriCardChannel channel) {
        this.channel = channel;
    }

    public PublicKey getPublicKey(int keyIndex) throws VCChipException {
//        String str_send = "800b000041";
//
//        byte[] ret = channel.sendCommand(HexHelper.hexTobyteArray(str_send));
//        short statusCode = CardResponseParser.getResponseStatusCode(ret);
//
//        if (statusCode == (short) 0x9000) {
//            PublicKey key = new PublicKey(Arrays.copyOfRange(ret, 0, ret.length - 2));
//            return key;
//        } else {
//            throw new VCChipException("get_publicKey_fail", "could not get public key");
//        }
        return PublicKey.of("");
    }
}
