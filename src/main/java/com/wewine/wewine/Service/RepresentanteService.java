package com.wewine.wewine.Service;

import com.wewine.wewine.DTO.RepresentanteLoginDTO;
import com.wewine.wewine.DTO.RepresentanteRequestDTO;
import com.wewine.wewine.DTO.RepresentanteResponseDTO;
import com.wewine.wewine.Entity.RepresentanteEntity;
import com.wewine.wewine.Exception.ResourceNotFoundException;
import com.wewine.wewine.Repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepresentanteService {

    private final RepresentanteRepository representanteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RepresentanteService(RepresentanteRepository representanteRepository, PasswordEncoder passwordEncoder) {
        this.representanteRepository = representanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Mapeamento DTO -> Entity
    private RepresentanteEntity toEntity(RepresentanteRequestDTO dto) {
        RepresentanteEntity entity = new RepresentanteEntity();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setPercentualComissao(dto.getPercentualComissao());
        entity.setTipo(dto.getTipo());
        return entity;
    }

    // Mapeamento Entity -> DTO
    private RepresentanteResponseDTO toResponseDTO(RepresentanteEntity entity) {
        RepresentanteResponseDTO dto = new RepresentanteResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setPercentualComissao(entity.getPercentualComissao());
        dto.setTipo(entity.getTipo());
        return dto;
    }

    public RepresentanteResponseDTO createRepresentante(RepresentanteRequestDTO requestDTO) {
        RepresentanteEntity entity = toEntity(requestDTO);
        String encodedPassword = passwordEncoder.encode(requestDTO.getSenha());
        entity.setSenha(encodedPassword);
        RepresentanteEntity savedEntity = representanteRepository.save(entity);
        return toResponseDTO(savedEntity);
    }

    public RepresentanteResponseDTO authenticate(RepresentanteLoginDTO loginDTO) {
        RepresentanteEntity representante = representanteRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Credenciais inválidas."));
        boolean isMatch = passwordEncoder.matches(loginDTO.getSenha(), representante.getSenha());
        if (isMatch) {
            return toResponseDTO(representante);
        } else {
            throw new ResourceNotFoundException("Credenciais inválidas.");
        }
    }

    // Retorna todos os representantes
    public List<RepresentanteResponseDTO> findAll() {
        return representanteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca por ID (retorna Optional para controle no controller)
    public Optional<RepresentanteResponseDTO> findById(Long id) {
        return representanteRepository.findById(id)
                .map(this::toResponseDTO);
    }
}