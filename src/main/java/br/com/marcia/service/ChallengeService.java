package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ChallengeService {

    CriptografiaDTO descriptografarFeign(String token);

    String enviarArquivoFeign(String token, File file);

    CriptografiaDTO descriptografarRest(String token);

    String enviarArquivoRest(String token, MultipartFile file);

}
