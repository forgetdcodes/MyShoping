package com.example.myshop.Utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by 刘博良 on 2017/5/18.
 */

public class DESUtil {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static String encode(String key,String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec spec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec parameterSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2String(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            return data;

        }
    }

    private static String byte2String(byte[] bytes) {
        StringBuilder sb =new StringBuilder();
        String s;
        for (int i =0;sb!=null&&i<bytes.length;i++){
            s =Integer.toHexString(bytes[i]&0XFF);
            if (s.length() ==1)
                sb.append("0");
            sb.append(s);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }
}
