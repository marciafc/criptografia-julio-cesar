package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.util.ArquivoUtil;
import br.com.marcia.util.CriptografiaUtil;
import br.com.marcia.ws.domain.CriptografiaEntity;
import br.com.marcia.ws.service.CodenationApiService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class CodenationService {

    @Autowired
    private CodenationApiService codenationApiService;

    public CriptografiaDTO obterArquivo(String token) {

        CriptografiaEntity criptografia = codenationApiService.buscarDadosCriptografia(token);
        if(criptografia != null) {
            CriptografiaUtil.gerarChaveValorJulioCesar(criptografia.getNumeroCasas());

            String textoDecifrado = CriptografiaUtil.descriptografarJulioCesar(criptografia.getCifrado());
            criptografia.setDecifrado(textoDecifrado);
            criptografia.setResumoCriptografico(CriptografiaUtil.gerarHash(textoDecifrado, "SHA-1"));

            ArquivoUtil.salvar(new Gson().toJson(criptografia));

            // TODO Trocar RestTemplate para Feign
            System.out.println(enviarDadosCriptografia(token));

            return CriptografiaDTO.builder()
                    .numeroCasas(criptografia.getNumeroCasas())
                    .cifrado(criptografia.getCifrado())
                    .decifrado(criptografia.getDecifrado())
                    .resumoCriptografico(criptografia.getResumoCriptografico())
                    .token(criptografia.getToken())
                    .build();
        }
        return null;
    }

    public Object enviarArquivo(@Validated @RequestBody String token, @Validated @RequestBody MultipartFile file) {
        return codenationApiService.enviarDadosCriptografia(token, file);
    }

    // Trocar RestTemplate para Feign
    private String enviarDadosCriptografia(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("answer", new FileSystemResource(System.getProperty("user.dir").concat(File.separator).concat(ArquivoUtil.NOME_ARQUIVO)));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=".concat(token) ;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        return response.getBody();
    }
}
