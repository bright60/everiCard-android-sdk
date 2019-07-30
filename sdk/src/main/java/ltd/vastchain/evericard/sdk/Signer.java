package ltd.vastchain.evericard.sdk;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.spongycastle.crypto.digests.SHA1Digest;

import java.math.BigInteger;
import java.util.Arrays;

import io.everitoken.sdk.java.PublicKey;
import io.everitoken.sdk.java.Signature;
import io.everitoken.sdk.java.Utils;
import ltd.vastchain.evericard.sdk.channels.EveriCardChannel;
import ltd.vastchain.evericard.sdk.command.CommandInterface;
import ltd.vastchain.evericard.sdk.response.SignResponse;

public class Signer {
    private final EveriCardChannel channel;

    public Signer(EveriCardChannel channel) {
        this.channel = channel;
    }

    // TODO: replace this method when evt4j sdk update
    public static int getRecId(ECKey.ECDSASignature signature, byte[] hash, PublicKey publicKey) {
        Sha256Hash dataHash = Sha256Hash.wrap(hash);

        String refPubKey = publicKey.getEncoded(true);

        int recId = -1;
        for (int i = 0; i < 4; i++) {
            ECKey k = ECKey.recoverFromSignature(i, signature, dataHash, true);
            try {
                if (k != null && Utils.HEX.encode(k.getPubKey()).equals(refPubKey)) {
                    return i;
                }
            } catch (Exception ex) {
                // no need to handle anything here
            }
        }

        return recId;
    }

    public static byte[] sha1(byte[] inputs) {
        SHA1Digest digest = new SHA1Digest();
        digest.update(inputs, 0, inputs.length);
        byte[] ret = new byte[]{};
        digest.doFinal(ret, 0);
        return ret;
    }

    public Signature sign(CommandInterface cardCommand, PublicKey publicKey, boolean asHash) throws VCChipException {
        ECKey.ECDSASignature signature = null;
        BigInteger r = null;
        BigInteger s = null;

        System.out.println(Utils.HEX.encode(cardCommand.getBytes()));
        while (true) {
            byte[] ret = channel.sendCommand(cardCommand);
            SignResponse res = new SignResponse(ret);

            if (!res.isSuccessful()) {
                throw new VCChipException("sign_hash_failed", String.format("Fail to sign hash (%s).", Utils.HEX.encode(res.getStatus())));
            }

            byte[] rawSignature = res.getSignature();
            r = new BigInteger(1, Arrays.copyOfRange(rawSignature, 0, 32));
            s = new BigInteger(1, Arrays.copyOfRange(rawSignature, 32, rawSignature.length));

            signature = new ECKey.ECDSASignature(r, s);

            // loop until get both r and s have 32 bytes
            if (r.toByteArray().length == 32 && s.toByteArray().length == 32 && signature.isCanonical()) {
                break;
            }
        }

        // TODO: handle recId can't be found
        int recId = getRecId(signature, asHash ? cardCommand.getData() : Utils.hash(cardCommand.getData()), publicKey);

        return new Signature(r, s, recId + 4 + 27);
    }
}
