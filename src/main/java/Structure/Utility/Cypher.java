package Structure.Utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Cypher {
    private static final String KEY = "Some random keys";

    public static String getEncryptedBytes(String pass) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] newPass = cipher.doFinal(pass.getBytes("UTF-8"));
        return new String(newPass);
    }

    public static String getDecryptedBytes(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(bytes));
    }
}
