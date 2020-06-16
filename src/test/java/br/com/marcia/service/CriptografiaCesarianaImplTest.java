package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CriptografiaCesarianaImplTest {

    private static final String TEXTO_CRIPTOGRAFADO = "d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr";
    private static final String TEXTO_DESCRIPTOGRAFADO = "a ligeira raposa marrom saltou sobre o cachorro cansado";

    @InjectMocks
    private CriptografiaCesarianaImpl criptografiaCesariana;

    @Test(expected = IllegalArgumentException.class)
    public void deveRetornarErroQuandoCriptografaEhNulo() {
        criptografiaCesariana.criptografar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRetornarErroQuandoDescriptografaEhNulo() {
        criptografiaCesariana.descriptografar(null);
    }

    @Test
    public void deveCriptografarTexto() {
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().decifrado(TEXTO_DESCRIPTOGRAFADO).numeroCasas(3).build();
        assertEquals(TEXTO_CRIPTOGRAFADO, criptografiaCesariana.criptografar(criptografiaDTO).getCifrado());
    }

    @Test
    public void deveDescriptografarTexto() {
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().cifrado(TEXTO_CRIPTOGRAFADO).numeroCasas(3).build();
        assertEquals(TEXTO_DESCRIPTOGRAFADO, criptografiaCesariana.descriptografar(criptografiaDTO).getDecifrado());
    }

    @Test
    public void deveManterOsNumerosNaCriptografia() {
        String texto = "sejam bem vindos ao Acelera Brasil 2019";
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().decifrado(texto).numeroCasas(3).build();

        assertEquals("vhmdp ehp ylqgrv dr dfhohud eudvlo 2019", criptografiaCesariana.criptografar(criptografiaDTO).getCifrado());
    }

    @Test
    public void deveManterOsNumerosNaDescriptografia() {
        String textoCifrado = "vhmdp ehp ylqgrv dr dfhohud eudvlo 2019";
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().cifrado(textoCifrado).numeroCasas(3).build();
        assertEquals("sejam bem vindos ao acelera brasil 2019", criptografiaCesariana.descriptografar(criptografiaDTO).getDecifrado());
    }

    @Test
    public void deveConverterCaracteresParaMinusculoNaCriptografia() {
        String texto = "Aprender Java gera felicidade";
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().decifrado(texto).numeroCasas(3).build();
        assertEquals("dsuhqghu mdyd jhud iholflgdgh", criptografiaCesariana.criptografar(criptografiaDTO).getCifrado());
    }

    @Test
    public void deveConverterCaracteresParaMinusculoNaDescriptografia() {
        String textoCifrado = "Dsuhqghu Mdyd jhud iholflgdgh";
        CriptografiaDTO criptografiaDTO = CriptografiaDTO.builder().cifrado(textoCifrado).numeroCasas(3).build();
        assertEquals("aprender java gera felicidade", criptografiaCesariana.descriptografar(criptografiaDTO).getDecifrado());
    }
}
