package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.TipoUsuario;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepresentanteResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private BigDecimal percentualComissao;
    private TipoUsuario tipo;
}
