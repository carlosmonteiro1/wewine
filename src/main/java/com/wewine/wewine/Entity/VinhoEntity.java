package com.wewine.wewine.Entity;


import com.wewine.wewine.enums.NivelCorpoEnum;
import com.wewine.wewine.enums.NivelDocuraEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class VinhoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private NivelCorpoEnum nivelCorpo;
    private NivelDocuraEnum nivelDocura;
    private String uva;
    private String vinicola;
    private int anoSafra;
    private String pais;
    private String regiao;
    private String urlImagem;
    private int volume;
    private BigDecimal preco;
    private Double teorAlcoolico;
}
