package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

public interface ChallengeService {

    CriptografiaDTO descriptografarFeign(String token);

    String enviarArquivoFeign(String token, MultipartFile file);

    CriptografiaDTO descriptografarRest(String token);

    String enviarArquivoRest(String token, MultipartFile file) throws IOException;

}
