package ltd.vastchain.evericard.sdk.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ResponseTest {

    @Test
    void throwErrorIfEmptyArrayPassedIn() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Response.of(new byte[]{});
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Response.of(new byte[]{1});
        });
    }

    @Test
    void isSuccessful() {
        Response response = Response.of(new byte[]{(byte) 0x90, (byte) 0x00});
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(0, response.getContent().length);
    }

    @Test
    void getContent() {
        Response response = Response.of(new byte[]{(byte) 0x11, (byte) 0x20, (byte) 0x90, (byte) 0x00});
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertTrue(Arrays.equals(response.getContent(), new byte[]{(byte) 0x11, (byte) 0x20}));
    }
}