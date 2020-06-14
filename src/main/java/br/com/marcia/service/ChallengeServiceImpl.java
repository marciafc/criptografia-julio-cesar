package br.com.marcia.service;

import br.com.marcia.domain.CriptografiaModel;
import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.mapper.CriptografiaMapper;
import br.com.marcia.util.ArquivoUtil;
import br.com.marcia.util.FileUtil;
import br.com.marcia.util.HashUtil;
import br.com.marcia.ws.service.CodenationApiService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    public String enviarArquivoRest(String token, MultipartFile file) throws IOException {
        String url = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=".concat(token);
        ResponseEntity<String> response = null;
        try {
            response = new RestTemplate().exchange(url,
                    HttpMethod.POST,
                    FileUtil.converterMultipartFile(file, "answer"),
                    String.class);
        } catch (Exception e) {
            throw e;
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
