package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.NivelCorpoEnum;
import com.wewine.wewine.enums.NivelDocuraEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VinhoDTO {
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
