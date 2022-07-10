package Structure.Utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cypher {
    private static final String KEY = "Some random keys";

    public static byte[] getEncryptedBytes(byte[] originalArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(originalArray);
    }

    public static byte[] getDecryptedBytes(byte[] encryptedArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedArray);
    }
}
