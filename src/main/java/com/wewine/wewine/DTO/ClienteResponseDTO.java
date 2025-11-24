package com.wewine.wewine.DTO;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nomeRazaoSocial;
    private String cpfCnpj;
    private String enderecoCompleto;
    private Double latitude;
    private Double longitude;
    private String nomeRepresentante;
}
