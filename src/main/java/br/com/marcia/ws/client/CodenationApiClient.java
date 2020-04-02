package br.com.marcia.ws.client;

import br.com.marcia.ws.domain.CriptografiaEntity;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public interface CodenationApiClient {

    @RequestLine("GET challenge/dev-ps/generate-data?token={token}")
    CriptografiaEntity buscarDadosCriptografia(@Param("token") String token);

    @RequestLine("POST challenge/dev-ps/submit-solution?token={token}")
    @Headers("Content-Type: multipart/form-data")
    Object enviarDadosCriptografia(@Param("token") String token, @RequestBody MultipartFile file);

}
