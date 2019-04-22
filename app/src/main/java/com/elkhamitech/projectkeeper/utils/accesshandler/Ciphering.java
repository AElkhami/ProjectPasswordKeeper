package com.elkhamitech.projectkeeper.utils.accesshandler;

import com.elkhamitech.projectkeeper.Constants;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Ciphering {
    public static String encrypt(String value, String encryptionKey) {
        byte[] keyData = new byte[0];
        try {
            encryptionKey = encryptionKey.substring(encryptionKey.length() - 5)
                    + Constants.ENCRYPT_KEY;
            keyData = (encryptionKey).getBytes(Constants.ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, Constants.ALGORITHM);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(Constants.TRANSFORMATION);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        byte[] iv = new byte[16];
//                {0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        try {
            assert cipher != null;
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        byte[] hasil = new byte[0];
        try {
            hasil = cipher.doFinal(value.getBytes(Constants.ENCODING));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bytesToHex(hasil);
        return bytesToHex(hasil)/*new String(hasil)*/;
    }

    private static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            for (byte aData : data) {
                if ((aData & 0xFF) < 16)
                    str.append("0").append(Integer.toHexString(aData & 0xFF));
                else
                    str.append(Integer.toHexString(aData & 0xFF));
            }
            return str.toString().toUpperCase();
        }
    }

    public static String decrypt(String value, String encryptionKey) {
        if (!value.equals("")) {
            byte[] keyData = new byte[0];
            try {
                encryptionKey = encryptionKey.substring(encryptionKey.length() - 5)
                        + Constants.ENCRYPT_KEY;
                keyData = (encryptionKey).getBytes(Constants.ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, Constants.ALGORITHM);
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance(Constants.TRANSFORMATION);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }

            byte[] iv = new byte[16];
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            try {
                assert cipher != null;
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            byte[] hasil = new byte[0];
            try {
                hasil = cipher.doFinal(hexToBytes(value));
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            return new String(hasil);
        } else {
            return "";
        }
    }

    private static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }

    }
}
