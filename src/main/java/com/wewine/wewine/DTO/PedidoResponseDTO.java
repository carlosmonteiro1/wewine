package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.StatusPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataCriacao;
    private String nomeCliente;
    private String nomeRepresentante;
    private String formaPagamento;
    private StatusPedido status;
    private BigDecimal valorTotalItens;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotalFinal;
    private List<ItemPedidoResponseDTO> itens;
}
