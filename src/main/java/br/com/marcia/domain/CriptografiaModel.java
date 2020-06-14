package br.com.marcia.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriptografiaModel implements Serializable {

    @JsonProperty("numero_casas")
    private Integer numeroCasas;

    private String token;

    private String cifrado;

    private String decifrado;

    @JsonProperty("resumo_criptografico")
    private String resumoCriptografico;

}
