package com.wewine.wewine.Controller;

import com.wewine.wewine.DTO.JwtAuthenticationResponseDTO;
import com.wewine.wewine.DTO.RepresentanteLoginDTO;
import com.wewine.wewine.DTO.RepresentanteRequestDTO;
import com.wewine.wewine.DTO.RepresentanteResponseDTO;
import com.wewine.wewine.Entity.RepresentanteEntity;
import com.wewine.wewine.Security.JwtTokenProvider;
import com.wewine.wewine.Service.RepresentanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/representantes")
public class RepresentanteController {
    private final RepresentanteService representanteService;
    private final JwtTokenProvider tokenProvider; // Injeção do provedor de JWT

    public RepresentanteController(RepresentanteService representanteService, JwtTokenProvider tokenProvider) {
        this.representanteService = representanteService;
        this.tokenProvider = tokenProvider;
    }

    // -----------------------------------------------------------
    // 1. ENDPOINT: LOGIN (POST /api/representantes/login)
    // Funcionalidade: Login com autenticação segura
    // -----------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDTO> login(@Valid @RequestBody RepresentanteLoginDTO loginDTO) {

        // 1. Autenticação e Validação de Credenciais no Service
        RepresentanteResponseDTO responseDTO = representanteService.authenticate(loginDTO);

        // 2. Busca a Entity para obter todos os dados necessários para o token
        // (Assume que findByEmailEntity retorna a Entity completa e lança exceção se não for achado)
        RepresentanteEntity representanteEntity = representanteService.findByEmailEntity(loginDTO.getEmail());

        // 3. Geração do Token JWT
        String jwt = tokenProvider.generateToken(representanteEntity);

        // 4. Monta a resposta com o Token e os dados do usuário (ID, Perfil)
        JwtAuthenticationResponseDTO response = new JwtAuthenticationResponseDTO(
                jwt,
                responseDTO.getId(),
                responseDTO.getNome(),
                responseDTO.getTipo()
        );

        // Retorna 200 OK com o token de acesso
        return ResponseEntity.ok(response);
    }

    // -----------------------------------------------------------
    // 2. ENDPOINT: CADASTRO DE NOVO REPRESENTANTE (POST /api/representantes)
    // Funcionalidade: Cadastro e gerenciamento de representantes de venda (Admin)
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
    // 3. ENDPOINT: LISTAGEM DE REPRESENTANTES (GET /api/representantes)
    // Funcionalidade: Gerenciamento administrativo (Admin)
    // -----------------------------------------------------------
    // OBS: Em um sistema real, este endpoint seria restrito ao perfil ADMIN via Spring Security.
    @GetMapping
    public ResponseEntity<List<RepresentanteResponseDTO>> findAll() {
        List<RepresentanteResponseDTO> lista = representanteService.findAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // -----------------------------------------------------------
    // 4. ENDPOINT: CONSULTA POR ID (GET /api/representantes/{id})
    // Funcionalidade: Edição e visualização de detalhes (Admin)
    // -----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<RepresentanteResponseDTO> findById(@PathVariable Long id) {
        return representanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
