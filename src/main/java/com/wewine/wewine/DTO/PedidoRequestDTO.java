package com.wewine.wewine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    private Long idCliente;
    private String condicaoPagamento;
    private List<ItemPedidoRequestDTO> itens;
}
