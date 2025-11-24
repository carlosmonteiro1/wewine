package com.wewine.wewine.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeRazaoSocial;
    private String nomeResponsavel;
    @Column(unique = true, nullable = false)
    private String cpfCnpj;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representante_id")
    private RepresentanteEntity representante;
}
