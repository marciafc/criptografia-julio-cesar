package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CriptografiaCesarianaImpl implements Criptografia {

    private final static Integer LETRA_A_MINUSCULA_ASCII = 97;
    private final static Integer LETRA_Z_MINUSCULA_ASCII = 122;

    public Map<Integer, Integer> alfabetoAsciiCifrado = new HashMap<>();
    public Map<Integer, Integer> alfabetoAsciiDecifrado = new HashMap<>();


    @Override
    public CriptografiaDTO criptografar(CriptografiaDTO criptografiaDTO) {
        if(criptografiaDTO == null)  throw new IllegalArgumentException();

        gerarMapAscii(criptografiaDTO.getNumeroCasas());
        criptografiaDTO.setCifrado(converterTexto(criptografiaDTO.getDecifrado(), true));
        return criptografiaDTO;
    }

    @Override
    public CriptografiaDTO descriptografar(CriptografiaDTO criptografiaDTO) {
        if(criptografiaDTO == null)  throw new IllegalArgumentException();

        gerarMapAscii(criptografiaDTO.getNumeroCasas());
        criptografiaDTO.setDecifrado(converterTexto(criptografiaDTO.getCifrado(), false));
        return criptografiaDTO;
    }

    private String converterTexto(String texto, boolean isCriptografar) {

        if(texto == null || texto.isEmpty()) throw new IllegalArgumentException();

        StringBuffer textoConvertido = new StringBuffer();
        texto = texto.toLowerCase();

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.substring(i, i + 1).toCharArray()[0];
            buscarCaracterCorrespondente(isCriptografar ? alfabetoAsciiCifrado : alfabetoAsciiDecifrado, textoConvertido, letra);
        }
        return textoConvertido.toString();
    }

    private void buscarCaracterCorrespondente(Map<Integer, Integer> asciiMap, StringBuffer textoConvertido, char letra) {
        int asciiCodigo = letra;
        if(asciiMap.containsKey(asciiCodigo)) {
            textoConvertido.append((char)asciiMap.get(asciiCodigo).intValue());
        } else {
            textoConvertido.append(letra);
        }
    }

    private void gerarMapAscii(int numeroPuloCasas) {
        for (int codigoAscii = LETRA_A_MINUSCULA_ASCII; codigoAscii <= LETRA_Z_MINUSCULA_ASCII; codigoAscii++) {

            if(codigoAscii - numeroPuloCasas >= LETRA_A_MINUSCULA_ASCII) {
                alfabetoAsciiDecifrado.put(codigoAscii, codigoAscii - numeroPuloCasas);
                alfabetoAsciiCifrado.put(codigoAscii - numeroPuloCasas, codigoAscii);
            } else {
                // Trata casos que precisa retornar ao final do alfabeto
                int primeirosPulos = codigoAscii - LETRA_A_MINUSCULA_ASCII;
                int pulosRestantes = LETRA_Z_MINUSCULA_ASCII - (numeroPuloCasas - primeirosPulos -1);
                alfabetoAsciiDecifrado.put(codigoAscii, pulosRestantes);
                alfabetoAsciiCifrado.put(pulosRestantes, codigoAscii);
            }

        }
    }

}
