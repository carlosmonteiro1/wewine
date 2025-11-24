package com.wewine.wewine.Entity;


import com.wewine.wewine.enums.NivelCorpoEnum;
import com.wewine.wewine.enums.NivelDocuraEnum;
import com.wewine.wewine.enums.TipoVinhoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
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
    @Enumerated(EnumType.STRING)
    private TipoVinhoEnum tipo;
}
