package ltd.vastchain.evericard.sdk.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.everitoken.sdk.java.Utils;

class PublicKeyReadTest {

    @Test
    void byIndexAndSymbolId() {
        PublicKeyRead command = PublicKeyRead.byIndexAndSymbolId(0, 207);
        System.out.println(Utils.HEX.encode(command.getBytes()));
        Assertions.assertEquals("800b000104000000cf41", Utils.HEX.encode(command.getBytes()));
    }
}