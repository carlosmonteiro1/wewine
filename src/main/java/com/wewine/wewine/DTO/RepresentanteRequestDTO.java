package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RepresentanteRequestDTO {

    @NotBlank
    private String nome;
    @NotBlank @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String senha;
    private String telefone;
    @NotNull
    private BigDecimal percentualComissao;
    @NotNull
    private TipoUsuario tipo;
}