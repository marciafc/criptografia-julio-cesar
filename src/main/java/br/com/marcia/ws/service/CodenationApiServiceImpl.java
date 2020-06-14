package br.com.marcia.ws.service;

import br.com.marcia.ws.client.CodenationApiClient;
import br.com.marcia.ws.domain.CriptografiaApi;
import feign.Feign;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class CodenationApiServiceImpl implements CodenationApiService {

    @Value("${app.codenation.uri}")
    private String uri;

    public CriptografiaApi buscarDadosCriptografia(String token) {
        return getClient().buscarDadosCriptografia(token);
    }


    public String enviarDadosCriptografia(String token, File file){
        return getClient().enviarDadosCriptografia(token, file).body().toString();
    }


    private CodenationApiClient getClient() {
        return Feign
                .builder()
                //.encoder(new GsonEncoder())
                .encoder(new FormEncoder(new JacksonEncoder()))
                .decoder(new GsonDecoder())
                .target(CodenationApiClient.class, uri);
    }

}
