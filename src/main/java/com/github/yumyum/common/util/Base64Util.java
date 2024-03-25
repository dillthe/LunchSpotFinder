package menu.yumyum.yumyum.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    /**
     * byte[]를 Base64로 인코딩하여 반환한다.
     *
     * @param bytes byte[]
     * @return Base64로 인코딩된 문자열
     */
    public static String bytesToBase64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * Base64를 디코딩하여 byte[]를 반환한다.
     *
     * @param base64 Base64로 인코딩된 문자열
     * @return Base64로 디코딩된 byte[]
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
    }

}
