package br.com.marcia.ws.service;

import br.com.marcia.ws.client.CodenationApiClient;
import br.com.marcia.ws.decoder.CustomErrorDecoder;
import br.com.marcia.ws.domain.CriptografiaApi;
import br.com.marcia.domain.ResultadoModel;
import feign.Feign;
import feign.Response;
import feign.form.spring.SpringFormEncoder;
import feign.gson.GsonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CodenationApiServiceImpl implements CodenationApiService {

    @Value("${app.codenation.uri}")
    private String uri;

    public CriptografiaApi buscarDadosCriptografia(String token) {
        return getClient().buscarDadosCriptografia(token);
    }

    public ResultadoModel enviarDadosCriptografia(String token, MultipartFile file){
        Response response = getClient().enviarDadosCriptografia(token, file);
        return ResultadoModel.builder().score(response.body().toString()).httpStatusCode(response.status()).build();
    }

    private CodenationApiClient getClient() {
        return Feign
                .builder()
                .errorDecoder(new CustomErrorDecoder())
                .encoder(new SpringFormEncoder())
                .decoder(new GsonDecoder())
                .target(CodenationApiClient.class, uri);
    }

}
