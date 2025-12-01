package com.wewine.wewine.Controller;

import com.wewine.wewine.DTO.RepresentanteRequestDTO;
import com.wewine.wewine.DTO.RepresentanteResponseDTO;
import com.wewine.wewine.Service.RepresentanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/representantes")
@Tag(name = "Representantes", description = "Endpoints para gerenciamento de representantes")
public class RepresentanteController {
    private final RepresentanteService representanteService;

    public RepresentanteController(RepresentanteService representanteService) {
        this.representanteService = representanteService;
    }

    // -----------------------------------------------------------
    // 1. ENDPOINT: CADASTRO DE NOVO REPRESENTANTE (POST /api/representantes)
    // Funcionalidade: Cadastro e gerenciamento de representantes de venda
    // -----------------------------------------------------------
    @PostMapping
    public ResponseEntity<RepresentanteResponseDTO> create(@Valid @RequestBody RepresentanteRequestDTO request) {

        RepresentanteResponseDTO saved = representanteService.createRepresentante(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        // Retorna 201 Created
        return ResponseEntity.created(location).body(saved);
    }

    // -----------------------------------------------------------
    // 2. ENDPOINT: LISTAGEM DE REPRESENTANTES (GET /api/representantes)
    // -----------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<RepresentanteResponseDTO>> findAll() {
        List<RepresentanteResponseDTO> lista = representanteService.findAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // -----------------------------------------------------------
    // 3. ENDPOINT: CONSULTA POR ID (GET /api/representantes/{id})
    // -----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<RepresentanteResponseDTO> findById(@PathVariable Long id) {
        return representanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // -----------------------------------------------------------
    // 4. ENDPOINT: ATUALIZAÇÃO DE REPRESENTANTE (PUT /api/representantes/{id})
    // -----------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<RepresentanteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RepresentanteRequestDTO request) {
        try {
            RepresentanteResponseDTO updated = representanteService.updateRepresentante(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // -----------------------------------------------------------
    // 5. ENDPOINT: EXCLUSÃO LÓGICA DE REPRESENTANTE (DELETE /api/representantes/{id})
    // Funcionalidade: Altera o status do representante para INATIVO
    // -----------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            representanteService.deleteRepresentante(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
