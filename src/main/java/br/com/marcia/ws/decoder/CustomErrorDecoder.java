package br.com.marcia.ws.decoder;

import br.com.marcia.exception.BadRequestException;
import br.com.marcia.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String metodo, Response response) {

        switch (response.status()){
            case 400:
                return new BadRequestException("Bad Request: " + metodo + " - " + response.reason());
            case 404:
                return new NotFoundException( "Not Found: " + metodo + " - " + response.reason());
            default:
                return new Exception("Generic error: " + metodo + " - " + response.reason());
        }
    }
}
