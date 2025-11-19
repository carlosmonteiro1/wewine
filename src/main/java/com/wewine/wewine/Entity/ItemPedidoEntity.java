package com.wewine.wewine.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "itemPedido")
public class ItemPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoEntity pedido;
    @ManyToOne
    @JoinColumn(name = "vinho_id",  nullable = false)
    private VinhoEntity vinho;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}
