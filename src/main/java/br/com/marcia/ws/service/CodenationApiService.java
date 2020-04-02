package br.com.marcia.ws.service;

import br.com.marcia.ws.client.CodenationApiClient;
import br.com.marcia.ws.domain.CriptografiaEntity;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CodenationApiService {

    @Value("${app.codenation.uri}")
    private String uri;

    //@Value("${app.codenation.token}")
    //private String token;

    public CriptografiaEntity buscarDadosCriptografia(String token) {
        return getClient().buscarDadosCriptografia(token);
    }

    public Object enviarDadosCriptografia(String token, MultipartFile file){
        return getClient().enviarDadosCriptografia(token, file);
    }

    private CodenationApiClient getClient() {
        return Feign
                .builder()
                //.errorDecoder(new ErrorDecodeBusiness())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(CodenationApiClient.class, uri);
    }

}
