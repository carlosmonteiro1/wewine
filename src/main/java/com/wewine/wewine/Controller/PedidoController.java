package com.wewine.wewine.Controller;

import com.wewine.wewine.DTO.PedidoRequestDTO;
import com.wewine.wewine.DTO.PedidoResponseDTO;
import com.wewine.wewine.Service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // SIMULAÇÃO DE SEGURANÇA:
    // Em um sistema real, o ID do Representante viria do token JWT/sessão.
    private Long getRepresentanteIdSimulado() {
        return 1L;
    }

    // -----------------------------------------------------------
    // 1. ENDPOINT: CRIAÇÃO DE PEDIDO (POST)
    // Funcionalidade: Emissão de pedidos com seleção de produtos
    // -----------------------------------------------------------
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> createPedido(@Valid @RequestBody PedidoRequestDTO request) {

        // Chamada ao Service, que fará todas as validações, cálculos e persistência transacional.
        PedidoResponseDTO saved = pedidoService.createPedido(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        // Retorna 201 Created
        return ResponseEntity.created(location).body(saved);
    }

    // -----------------------------------------------------------
    // 2. ENDPOINT: LISTAGEM DE PEDIDOS POR REPRESENTANTE (GET)
    // Funcionalidade: Acompanhamento de pedidos (App Mobile)
    // -----------------------------------------------------------
    @GetMapping("/meus")
    public ResponseEntity<List<PedidoResponseDTO>> findByRepresentante() {
        // Pega o ID do representante logado (simulação)
        Long representanteId = getRepresentanteIdSimulado();

        List<PedidoResponseDTO> lista = pedidoService.findByRepresentanteId(representanteId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Retorna 200 OK
        return ResponseEntity.ok(lista);
    }

    // -----------------------------------------------------------
    // 3. ENDPOINT: LISTAGEM GERAL DE PEDIDOS (GET)
    // Funcionalidade: Acompanhamento de pedidos (Painel Admin)
    // -----------------------------------------------------------
    // OBS: Este endpoint deve ser restrito ao perfil de Administrador (TipoUsuario.ADMIN).
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll() {
        List<PedidoResponseDTO> lista = pedidoService.findAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // -----------------------------------------------------------
    // 4. ENDPOINT: CONSULTA POR ID (GET)
    // -----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        // Se o Service lança 404, o Controller só se preocupa com o 200 OK.
        PedidoResponseDTO dto = pedidoService.findById(id);
        return ResponseEntity.ok(dto);
    }
}
