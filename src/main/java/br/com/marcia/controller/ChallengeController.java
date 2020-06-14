package br.com.marcia.controller;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.service.ChallengeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(value = "/v1/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivoRest(@RequestParam String token,@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok().body(challengeService.enviarArquivoRest(token, file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Obtém o arquivo json com o desafio e descriptografa")
    @GetMapping(value="/v2/descriptografar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> descriptografarArquivoFeign(@RequestParam String token) {
        return ResponseEntity.ok().body(challengeService.descriptografarFeign(token));
    }

    @ApiOperation("Envia a resolução do desafio")
    @PostMapping(value = "/v2/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivoFeign(@RequestParam String token, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(challengeService.enviarArquivoFeign(token, file));
    }

}
