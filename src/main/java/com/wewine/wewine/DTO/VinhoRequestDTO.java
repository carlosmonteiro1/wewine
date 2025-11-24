package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.NivelCorpoEnum;
import com.wewine.wewine.enums.NivelDocuraEnum;
import com.wewine.wewine.enums.TipoVinhoEnum;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
@Data
public class VinhoRequestDTO {
    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private NivelCorpoEnum nivelCorpo;
    @NotNull
    private NivelDocuraEnum nivelDocura;
    @NotNull
    private String uva;
    private String vinicola;
    @NotNull
    private int anoSafra;
    private String pais;
    private String regiao;
    private String urlImagem;
    private int volume;
    @NotNull
    private BigDecimal preco;
    private Double teorAlcoolico;
    private TipoVinhoEnum tipo;
}
