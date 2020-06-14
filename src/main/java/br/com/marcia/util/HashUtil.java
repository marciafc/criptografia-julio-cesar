package br.com.marcia.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String gerarHash(String texto, String algoritmo, String charset){
        try {
            MessageDigest digest = MessageDigest.getInstance(algoritmo);
            digest.reset();
            digest.update(texto.getBytes(Charset.forName(charset)));
            return String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
