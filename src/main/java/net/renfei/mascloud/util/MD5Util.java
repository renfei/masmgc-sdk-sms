package net.renfei.mascloud.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author renfei
 */
public class MD5Util {
    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String md5Encode(String origin, String charsetname) {
        String resultString = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname != null && !"".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(origin.getBytes(charsetname)));
            } else {
                resultString = byteArrayToHexString(md.digest(origin.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultString;
    }

    public static String md5Encode(String origin) {
        return md5Encode(origin, (String)null);
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
