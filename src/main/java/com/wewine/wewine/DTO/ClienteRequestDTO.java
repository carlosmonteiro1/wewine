package com.wewine.wewine.DTO;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class ClienteRequestDTO {
    @NotNull
    private String nomeRazaoSocial;
    @NotNull
    private String cpfCnpj;
    private String nomeResponsavel;
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
