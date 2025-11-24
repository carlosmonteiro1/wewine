package com.wewine.wewine.Service;

import com.wewine.wewine.DTO.VinhoRequestDTO;
import com.wewine.wewine.DTO.VinhoResponseDTO;
import com.wewine.wewine.Entity.VinhoEntity;
import com.wewine.wewine.Repository.VinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VinhoService {

    private final VinhoRepository vinhoRepository;

    @Autowired
    public VinhoService(VinhoRepository vinhoRepository) {
        this.vinhoRepository = vinhoRepository;
    }

    private VinhoEntity toEntity(VinhoRequestDTO dto) {
        VinhoEntity entity = new VinhoEntity();
        if (dto == null) {
            return entity;
        }

        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setNivelCorpo(dto.getNivelCorpo());
        entity.setNivelDocura(dto.getNivelDocura());
        entity.setUva(dto.getUva());
        entity.setVinicola(dto.getVinicola());
        entity.setAnoSafra(dto.getAnoSafra());
        entity.setPais(dto.getPais());
        entity.setRegiao(dto.getRegiao());
        entity.setUrlImagem(dto.getUrlImagem());
        entity.setVolume(dto.getVolume());
        entity.setPreco(dto.getPreco());
        entity.setTeorAlcoolico(dto.getTeorAlcoolico());
        entity.setTipo(dto.getTipo());

        return entity;
    }

    private VinhoResponseDTO toResponseDTO(VinhoEntity entity) {
        VinhoResponseDTO dto = new VinhoResponseDTO();
        if (entity == null) {
            return dto;
        }

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setPreco(entity.getPreco());
        dto.setDescricao(entity.getDescricao());
        dto.setUva(entity.getUva());
        dto.setPais(entity.getPais());
        dto.setRegiao(entity.getRegiao());
        dto.setUrlImagem(entity.getUrlImagem());
        dto.setSafra(entity.getAnoSafra() > 0 ? Integer.toString(entity.getAnoSafra()) : null);

        if (entity.getDescricao() != null) {
            String desc = entity.getDescricao().trim();
            dto.setDescricaoCurta(desc.length() <= 120 ? desc : desc.substring(0, 120).trim() + "...");
        } else {
            dto.setDescricaoCurta(null);
        }

        dto.setTipo(entity.getTipo());

        return dto;
    }

    public VinhoResponseDTO createVinho(VinhoRequestDTO requestDTO) {
        VinhoEntity vinhoToSave = toEntity(requestDTO);
        VinhoEntity savedVinho = vinhoRepository.save(vinhoToSave);
        return toResponseDTO(savedVinho);
    }

    public List<VinhoResponseDTO> findAll() {
        List<VinhoEntity> entities = vinhoRepository.findAll();
        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<VinhoResponseDTO> findById(Long id) {
        return vinhoRepository.findById(id)
                .map(this::toResponseDTO);
    }
}