package br.com.marcia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriptografiaDTO {

    @JsonProperty("numero_casas")
    private Integer numeroCasas;

    private String token;

    private String cifrado;

    private String decifrado;

    @JsonProperty("resumo_criptografico")
    private String resumoCriptografico;

}
