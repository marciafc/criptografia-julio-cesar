package br.com.marcia.ws.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CriptografiaEntity {

    @SerializedName(value = "numero_casas")
    private Integer numeroCasas;

    private String token;

    private String cifrado;

    private String decifrado;

    @SerializedName("resumo_criptografico")
    private String resumoCriptografico;
}
