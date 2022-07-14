package Structure.Utility;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

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

    public static byte[] crypt(byte[] packet, boolean encryptMode) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance("AES");

            Key key = new SecretKeySpec(KEY.getBytes(), "AES");
            if(encryptMode)
                cipher.init(Cipher.ENCRYPT_MODE, key);
            else
                cipher.init(Cipher.DECRYPT_MODE, key);

            int massageOffset = 24;
            int length = packet.length-massageOffset-Short.BYTES;

            ByteBuffer buffer = ByteBuffer.wrap(packet);
            byte[] massage = new byte[length];
            buffer.get(massageOffset, massage, 0, length);
            massage = cipher.doFinal(massage);


            byte [] res = new byte[massageOffset+massage.length+Short.BYTES];
            ByteBuffer buffer1 = ByteBuffer.wrap(res);
            buffer1.put(packet, 0, massageOffset)
                    .put(massage)
                    .putShort( buffer.getShort(packet.length - Short.BYTES));
            return res;

        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
}
