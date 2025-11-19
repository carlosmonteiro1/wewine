package com.wewine.wewine.Entity;


import com.wewine.wewine.enums.NivelCorpoEnum;
import com.wewine.wewine.enums.NivelDocuraEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "vinhos")
public class VinhoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private String uva;
    private String vinicola;
    private String pais;
    private String regiao;
    private String urlImagem;
    private int volume;
    private int anoSafra;
    private BigDecimal preco;
    private Double teorAlcoolico;
    @Enumerated(EnumType.STRING)
    private NivelCorpoEnum nivelCorpo;
    @Enumerated(EnumType.STRING)
    private NivelDocuraEnum nivelDocura;
}
