package br.com.marcia.mapper;

import br.com.marcia.domain.CriptografiaModel;
import br.com.marcia.dto.CriptografiaDTO;
import br.com.marcia.ws.domain.CriptografiaApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CriptografiaMapper {

    @Mappings({
            @Mapping(source = "numeroCasas", target = "numeroCasas"),
            @Mapping(source = "token", target = "token"),
            @Mapping(source = "cifrado", target = "cifrado"),
            @Mapping(source = "decifrado", target = "decifrado"),
            @Mapping(source = "resumoCriptografico", target = "resumoCriptografico"),
    })

    CriptografiaDTO map(CriptografiaModel criptografiaModel);

    CriptografiaDTO map(CriptografiaApi criptografiaApi);

}