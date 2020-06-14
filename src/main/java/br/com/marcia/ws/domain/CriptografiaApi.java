package br.com.marcia.ws.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriptografiaApi implements Serializable {

    @JsonProperty("numero_casas")
    @SerializedName("numero_casas")
    private Integer numeroCasas;

    private String token;

    private String cifrado;

    private String decifrado;

    @JsonProperty("resumo_criptografico")
    @SerializedName("resumo_criptografico")
    private String resumoCriptografico;
}
