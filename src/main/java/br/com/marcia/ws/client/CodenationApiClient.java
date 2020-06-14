package br.com.marcia.ws.client;

import br.com.marcia.ws.domain.CriptografiaApi;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.web.multipart.MultipartFile;

public interface CodenationApiClient {

    @RequestLine("GET challenge/dev-ps/generate-data?token={token}")
    CriptografiaApi buscarDadosCriptografia(@Param("token") String token);
    
    @RequestLine("POST challenge/dev-ps/submit-solution?token={token}")
    @Headers("Content-Type: multipart/form-data")
    Response enviarDadosCriptografia(@Param("token") String token, @Param("answer") MultipartFile file);

}
