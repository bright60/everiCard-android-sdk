package ltd.vastchain.evericard.sdk;

import io.everitoken.sdk.java.PublicKey;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
import ltd.vastchain.evericard.sdk.command.Command;
import ltd.vastchain.evericard.sdk.response.Response;

public class Card {
    private EveriCardChannel channel;

    public Card(EveriCardChannel channel) {
        this.channel = channel;
    }

    public PublicKey getPublicKey(int keyIndex) throws VCChipException {
        Command command = new Command((byte) 0x80, (byte) 0x0b, (byte) 0x00, (byte) 0x00, (byte) 0x41); //"800b000041";
//        ConfigurationRead read = ConfigurationRead.readConfigurationItem((byte) 0x0a);

        byte[] ret = channel.sendCommand(command);
        Response res = new Response(ret);
        if (res.isSuccessful()) {
            PublicKey key = new PublicKey(res.getContent());
            return key;
        } else {
            throw new VCChipException("get_publicKey_fail", "could not get public key");
        }

    }
}
