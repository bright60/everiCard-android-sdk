package ltd.vastchain.evericard.sdk.utils;

public class HexHelper {
    /**
     * Convert hex to byte array
     * @param hex hex-format byte stream
     * @return
     */
    public static byte[] hexTobyteArray(String hex) {
        if (hex == null) {
            return null;
        }
        hex = hex.trim();
        int len = hex.length();
        if (len % 2 == 1) {
            hex = "0" + hex;
            len++;
        }
        byte[] result = new byte[len/ 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) Integer.decode("0x" + hex.substring(i, i + 2)).intValue();
        }
        return result;
    }

    /**
     * Convert byte array to hex string
     * @param bytes byte array to be converted
     * @return
     */
    public static String byteArrayToHex(byte[] bytes) {
        return byteArrayToHex(bytes, 0, bytes.length);
    }

    /**
     * Convert byte array to hex string
     * @param bytes byte array to be converted
     * @param offset
     * @param length
     * @return
     */
    public static String byteArrayToHex(byte[] bytes, int offset, int length) {
        char[] hexChars = new char[length * 2];

        for (int j = offset; j < offset + length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[(j - offset) * 2] = hexArray[v >>> 4];
            hexChars[(j - offset) * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
}
