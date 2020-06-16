package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.domain.ResultadoModel;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

public interface ChallengeService {

    CriptografiaDTO descriptografarFeign(String token);

    ResultadoModel enviarArquivoFeign(String token, MultipartFile file);

    CriptografiaDTO descriptografarRest(String token);

    ResultadoModel enviarArquivoRest(String token, MultipartFile file) throws IOException;

}
