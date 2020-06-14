package br.com.marcia.ws.service;

import br.com.marcia.ws.domain.CriptografiaApi;
import org.springframework.web.multipart.MultipartFile;


public interface CodenationApiService {

    CriptografiaApi buscarDadosCriptografia(String token);

    String enviarDadosCriptografia(String token, MultipartFile file);

}
