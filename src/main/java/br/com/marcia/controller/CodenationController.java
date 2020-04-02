package br.com.marcia.controller;

import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.service.CodenationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@Api
public class CodenationController {

    @Autowired
    private CodenationService codenationService;

    @GetMapping(value="/obter-arquivo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriptografiaDTO> obterArquivo(@Validated @RequestParam String token) {
        return ResponseEntity.ok().body(codenationService.obterArquivo(token));
    }

    @PostMapping(value = "/enviar-arquivo", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> enviarArquivo(@Validated @RequestParam String token, @Validated @RequestBody MultipartFile file) {
        // TODO Nao está funcionando com o Feign codenationService.enviarArquivo(token, file);
        return ResponseEntity.ok().body("");

    }
}
