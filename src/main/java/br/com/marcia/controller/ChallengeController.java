package br.com.marcia.controller;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.service.ChallengeService;
import br.com.marcia.domain.ResultadoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@Api
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @ApiOperation("Obtém o arquivo json com o desafio e descriptografa")
    @GetMapping(value="/v1/descriptografar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> descriptografarRest(@RequestParam String token) {
        return ResponseEntity.ok().body(challengeService.descriptografarRest(token));
    }

    @ApiOperation("Envia a resolução do desafio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desafio submetido com sucesso."),
            @ApiResponse(code = 404, message = "O token é inválido."),
            @ApiResponse(code = 403, message = "Não é mais possível enviar o desafio. O usuário não é candidato a nenhuma aceleração válida.")})
    @PostMapping(value = "/v1/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivoRest(@RequestParam String token,@RequestParam("file") MultipartFile file) {
        try {

            ResultadoModel retorno = challengeService.enviarArquivoRest(token, file);
            return ResponseEntity.status(retorno.getHttpStatusCode()).body(retorno.getScore());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode().value()).body(e.getResponseBodyAsString());
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Obtém o arquivo json com o desafio e descriptografa")
    @GetMapping(value="/v2/descriptografar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> descriptografarArquivoFeign(@RequestParam String token) {
        return ResponseEntity.ok().body(challengeService.descriptografarFeign(token));
    }

    @ApiOperation("Envia a resolução do desafio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desafio submetido com sucesso."),
            @ApiResponse(code = 404, message = "O token é inválido."),
            @ApiResponse(code = 403, message = "Não é mais possível enviar o desafio. O usuário não é candidato a nenhuma aceleração válida.")})
    @PostMapping(value = "/v2/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivoFeign(@RequestParam String token, @RequestParam("file") MultipartFile file) {
        ResultadoModel retorno = challengeService.enviarArquivoFeign(token, file);
        return ResponseEntity.status(retorno.getHttpStatusCode()).body(retorno.getScore());
    }

}
