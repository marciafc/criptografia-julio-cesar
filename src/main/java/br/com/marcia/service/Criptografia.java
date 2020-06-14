package br.com.marcia.service;

import br.com.marcia.dto.CriptografiaDTO;

public interface Criptografia {

    CriptografiaDTO criptografar(CriptografiaDTO criptografiaDTO);

    CriptografiaDTO descriptografar(CriptografiaDTO criptografiaDTO);
}
