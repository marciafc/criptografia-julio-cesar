package br.com.marcia.service;

import br.com.marcia.domain.CriptografiaModel;
import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.mapper.CriptografiaMapper;
import br.com.marcia.util.ArquivoUtil;
import br.com.marcia.util.HashUtil;
import br.com.marcia.ws.service.CodenationApiService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private CodenationApiService codenationApiService;

    @Autowired
    private Criptografia criptografiaService;

    @Autowired
    private CriptografiaMapper criptografiaMapper;

    public CriptografiaDTO descriptografarRest(String token) {
        String url = "https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=".concat(token);
        ResponseEntity<CriptografiaModel> criptografiaModel = new RestTemplate().getForEntity(url, CriptografiaModel.class);

        CriptografiaDTO criptografiaDTO = criptografiaService.descriptografar(criptografiaMapper.map(criptografiaModel.getBody()));
        criptografiaDTO.setResumoCriptografico(HashUtil.gerarHash(criptografiaDTO.getDecifrado(), "SHA-1", "UTF-8"));

        ArquivoUtil.salvar(new Gson().toJson(criptografiaDTO));

        return criptografiaDTO;
    }

    public String enviarArquivoRest(String token, MultipartFile file) {
        String url = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=".concat(token);
        ResponseEntity<String> response = null;

        try {
            // header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // body
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("answer")
                    .filename(file.getName())
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            response = new RestTemplate().exchange(url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response != null ? response.getBody() : null;
    }

    public CriptografiaDTO descriptografarFeign(String token) {

        CriptografiaDTO criptografiaDTO = criptografiaService.descriptografar(criptografiaMapper.map(codenationApiService.buscarDadosCriptografia(token)));
        criptografiaDTO.setResumoCriptografico(HashUtil.gerarHash(criptografiaDTO.getDecifrado(), "SHA-1", "UTF-8"));

        ArquivoUtil.salvar(new Gson().toJson(criptografiaDTO));

        return criptografiaDTO;

        /*return CriptografiaDTO.builder()
                .numeroCasas(criptografia.getNumeroCasas())
                .cifrado(criptografia.getCifrado())
                .decifrado(criptografia.getDecifrado())
                .resumoCriptografico(criptografia.getResumoCriptografico())
                .token(criptografia.getToken())
                .build();*/
    }

    public String enviarArquivoFeign(String token, File file) {
        return codenationApiService.enviarDadosCriptografia(token, file);
    }

}
