package com.wewine.wewine.Entity;

import com.wewine.wewine.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String condicaoPagamento;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representante_id")
    private RepresentanteEntity representante;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedidoEntity> itens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
