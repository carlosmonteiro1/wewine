package com.wewine.wewine.Service;

import com.wewine.wewine.DTO.ItemPedidoRequestDTO;
import com.wewine.wewine.DTO.ItemPedidoResponseDTO;
import com.wewine.wewine.DTO.PedidoRequestDTO;
import com.wewine.wewine.DTO.PedidoResponseDTO;
import com.wewine.wewine.Entity.*;
import com.wewine.wewine.Exception.ResourceNotFoundException;
import com.wewine.wewine.Repository.ClienteRepository;
import com.wewine.wewine.Repository.PedidoRepository;
import com.wewine.wewine.Repository.RepresentanteRepository;
import com.wewine.wewine.Repository.VinhoRepository;
import com.wewine.wewine.enums.StatusPedido;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RepresentanteRepository representanteRepository;
    private final VinhoRepository vinhoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         RepresentanteRepository representanteRepository, VinhoRepository vinhoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.representanteRepository = representanteRepository;
        this.vinhoRepository = vinhoRepository;
    }

    // -----------------------------------------------------------
    // LÓGICA DE NEGÓCIO: CRIAÇÃO DO PEDIDO (@Transactional)
    // -----------------------------------------------------------

    public List<PedidoResponseDTO> findByRepresentanteId(Long idRepresentante) {
        List<PedidoEntity> entities = pedidoRepository.findByRepresentanteId(idRepresentante);

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        PedidoEntity entity = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com ID " + id + " não encontrado"));

        return toResponseDTO(entity);
    }

    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional // Garante que se um item falhar, o pedido inteiro será desfeito (rollback).
    public PedidoResponseDTO createPedido(PedidoRequestDTO requestDTO) {

        // 1. Validação dos Vínculos Essenciais
        ClienteEntity cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        RepresentanteEntity representante = representanteRepository.findById(requestDTO.getIdRepresentante())
                .orElseThrow(() -> new ResourceNotFoundException("Representante não encontrado."));

        // 2. Mapeamento e Processamento dos Itens
        List<ItemPedidoEntity> itensEntity = new ArrayList<>();
        BigDecimal valorTotalItens = BigDecimal.ZERO;
        BigDecimal valorTotalDesconto = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDto : requestDTO.getItens()) {
            VinhoEntity vinho = vinhoRepository.findById(itemDto.getIdVinho())
                    .orElseThrow(() -> new ResourceNotFoundException("Vinho com ID " + itemDto.getIdVinho() + " não encontrado."));

            // Cria o ItemPedidoEntity
            ItemPedidoEntity itemEntity = processItem(itemDto, vinho);

            itensEntity.add(itemEntity);
            valorTotalItens = valorTotalItens.add(itemEntity.getSubTotalItem());
            valorTotalDesconto = valorTotalDesconto.add(itemEntity.getValorDescontoAplicado());
        }

        // 3. Criação do Pedido Principal (Cabeçalho)
        PedidoEntity pedido = new PedidoEntity();

        // Configurações do pedido
        pedido.setCliente(cliente);
        pedido.setRepresentante(representante);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setFormaPagamento(requestDTO.getFormaPagamento());
        pedido.setStatus(StatusPedido.EMITIDO); // Status inicial

        // Configura os valores calculados
        pedido.setValorTotalItens(valorTotalItens); // Total antes de descontos
        pedido.setValorDesconto(valorTotalDesconto); // Total de descontos
        pedido.setValorTotalFinal(valorTotalItens.subtract(valorTotalDesconto)); // Total Final

        // Vínculo dos itens com o pedido principal
        pedido.setItens(itensEntity);
        // ATENÇÃO: É necessário configurar Cascade.ALL no PedidoEntity para salvar os itens automaticamente.
        itensEntity.forEach(item -> item.setPedido(pedido));

        // 4. Persistência
        PedidoEntity savedPedido = pedidoRepository.save(pedido);

        // 5. Retorno
        return toResponseDTO(savedPedido);
    }

    // -----------------------------------------------------------
    // MÉTODOS AUXILIARES E PRIVADOS
    // -----------------------------------------------------------

    private ItemPedidoEntity processItem(ItemPedidoRequestDTO itemDto, VinhoEntity vinho) {
        ItemPedidoEntity itemEntity = new ItemPedidoEntity();

        // **Preço de Venda** (É o preço do vinho no momento da venda)
        BigDecimal precoUnitario = vinho.getPreco(); // Assume que o preço está na VinhoEntity

        // **Cálculos**
        BigDecimal quantidade = new BigDecimal(itemDto.getQuantidade());
        BigDecimal subtotalBruto = precoUnitario.multiply(quantidade);

        // Desconto (se não for nulo no DTO)
        BigDecimal descontoItem = itemDto.getDescontoItem() != null ? itemDto.getDescontoItem() : BigDecimal.ZERO;

        // Subtotal final do item
        BigDecimal subtotalFinal = subtotalBruto.subtract(descontoItem)
                .setScale(2, RoundingMode.HALF_UP); // Garantir precisão de 2 casas decimais

        // Mapeamento para a Entity
        itemEntity.setVinho(vinho);
        itemEntity.setQuantidade(itemDto.getQuantidade());
        itemEntity.setPrecoUnitarioVenda(precoUnitario);
        itemEntity.setValorDescontoAplicado(descontoItem);
        itemEntity.setSubtotalItem(subtotalFinal);

        return itemEntity;
    }

    // Mapeamento Entity -> DTO (Complexo por causa dos itens)
    private PedidoResponseDTO toResponseDTO(PedidoEntity entity) {
        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(entity.getId());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setFormaPagamento(entity.getFormaPagamento().name());
        dto.setStatus(entity.getStatus());

        // Mapeamento de valores
        dto.setValorTotalItens(entity.getValorTotalItens());
        dto.setValorDesconto(entity.getValorDesconto());
        dto.setValorTotalFinal(entity.getValorTotalFinal());

        // Dados de referência (para relatórios)
        dto.setNomeCliente(entity.getCliente().getNomeRazaoSocial());
        dto.setNomeRepresentante(entity.getRepresentante().getNome());

        // Mapeamento dos Itens
        List<ItemPedidoResponseDTO> itensDTO = entity.getItens().stream()
                .map(this::toItemResponseDTO)
                .collect(Collectors.toList());
        dto.setItens(itensDTO);

        return dto;
    }

    // Mapeamento de ItemPedidoEntity -> ItemPedidoResponseDTO
    private ItemPedidoResponseDTO toItemResponseDTO(ItemPedidoEntity itemEntity) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setId(itemEntity.getId());

        // Dados do Vinho
        dto.setIdVinho(itemEntity.getVinho().getId());
        dto.setNomeVinho(itemEntity.getVinho().getNome());
        dto.setUrlImagemVinho(itemEntity.getVinho().getUrlImagem());

        // Dados da Venda
        dto.setQuantidade(itemEntity.getQuantidade());
        dto.setPrecoUnitarioVenda(itemEntity.getPrecoUnitarioVenda());
        dto.setValorDescontoAplicado(itemEntity.getValorDescontoAplicado());
        dto.setSubtotalItem(itemEntity.getSubtotalItem());

        return dto;
    }
}
