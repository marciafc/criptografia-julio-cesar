package br.com.marcia.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoModel {

    String score;

    int httpStatusCode;
}
