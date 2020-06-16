package br.com.marcia.ws.service;

import br.com.marcia.ws.domain.CriptografiaApi;
import br.com.marcia.domain.ResultadoModel;
import org.springframework.web.multipart.MultipartFile;


public interface CodenationApiService {

    CriptografiaApi buscarDadosCriptografia(String token);

    ResultadoModel enviarDadosCriptografia(String token, MultipartFile file);

}
