package com.wewine.wewine.Entity;

import com.wewine.wewine.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "representante")
public class RepresentanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING)
    private TipoUsuario perfil;
    private Double percentualComissao;
    private BigDecimal metaMensal;
}
