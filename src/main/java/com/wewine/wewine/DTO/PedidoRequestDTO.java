package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.FormaPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
public class PedidoRequestDTO {
    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long idCliente;
    @NotNull(message = "O ID do representante é obrigatório.")
    private Long idRepresentante;
    @NotBlank(message = "A forma de pagamento é obrigatória.")
    private FormaPagamentoEnum formaPagamento;
    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    private List<ItemPedidoRequestDTO> itens;
}
