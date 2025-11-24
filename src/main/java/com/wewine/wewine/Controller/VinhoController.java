package com.wewine.wewine.Controller;

import com.wewine.wewine.DTO.VinhoRequestDTO;
import com.wewine.wewine.DTO.VinhoResponseDTO;
import com.wewine.wewine.Service.VinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/apli/vinhos")
public class VinhoController {

    private final VinhoService vinhoService;

    @Autowired
    public VinhoController(VinhoService vinhoService) {
        this.vinhoService = vinhoService;
    }

    @GetMapping
    public ResponseEntity<List<VinhoResponseDTO>> findAll() {
        List<VinhoResponseDTO> lista = vinhoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VinhoResponseDTO> findById(@PathVariable Long id) {
        return vinhoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VinhoResponseDTO> create(@Valid @RequestBody VinhoRequestDTO request) {
        VinhoResponseDTO saved = vinhoService.createVinho(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }
}