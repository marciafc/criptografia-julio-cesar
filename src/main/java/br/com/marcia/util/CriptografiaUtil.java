package br.com.marcia.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CriptografiaUtil {

    public static Map<Integer, Integer> alfabetoAscii = new HashMap<>();

    private final static Integer LETRA_A_MINUSCULA_ASCII = 97;
    private final static Integer LETRA_Z_MINUSCULA_ASCII = 122;

    public static void gerarChaveValorJulioCesar(Integer numeroCasas) {

        for (int codigoAscii = LETRA_A_MINUSCULA_ASCII; codigoAscii <= LETRA_Z_MINUSCULA_ASCII; codigoAscii++) {

            if(codigoAscii - numeroCasas >= LETRA_A_MINUSCULA_ASCII) {
                alfabetoAscii.put(codigoAscii, codigoAscii - numeroCasas);

            } else {
                // Trata casos que precisa retornar ao final do alfabeto
                int primeirosPulos = codigoAscii - LETRA_A_MINUSCULA_ASCII;
                int pulosRestantes = LETRA_Z_MINUSCULA_ASCII - (numeroCasas - primeirosPulos -1);
                alfabetoAscii.put(codigoAscii, pulosRestantes);
            }
        }
    }

    public static String descriptografarJulioCesar(String textoCifrado) {

        StringBuffer textoDecifrado = new StringBuffer();

        for (int i = 0; i < textoCifrado.length(); i++) {

            char[] str = textoCifrado.substring(i, i + 1).toCharArray();
            int a = str[0];
            if(alfabetoAscii.containsKey(a)) {
                char b = (char)(alfabetoAscii.get(a).intValue());
                textoDecifrado.append(b);
            } else {
                textoDecifrado.append(str);
            }
        }

        return textoDecifrado.toString();
    }

    public static String gerarHash(String texto, String algoritmo) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algoritmo);
            digest.reset();
            digest.update(texto.getBytes(Charset.forName("UTF-8")));
            return String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
