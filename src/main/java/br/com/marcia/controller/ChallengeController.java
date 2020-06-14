package br.com.marcia.controller;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.service.ChallengeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@Api
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping(value="/v1/descriptografar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> descriptografarRest(@RequestParam String token) {
        return ResponseEntity.ok().body(challengeService.descriptografarRest(token));
    }

    @PostMapping(value = "/v1/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivo(@RequestParam String token,@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(challengeService.enviarArquivoRest(token, file));
    }

    @GetMapping(value="/v2/descriptografar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> descriptografarArquivo(@RequestParam String token) {
        return ResponseEntity.ok().body(challengeService.descriptografarFeign(token));
    }

    @PostMapping(value = "/v2/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarArquivoFeign(@RequestParam String token, @RequestPart("answer")  MultipartFile file) {

        // TODO Enconder MultipartFile
        File fileCopy = new File("answer.json");
        try {
            file.transferTo(fileCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(challengeService.enviarArquivoFeign(token, fileCopy));
    }

}
