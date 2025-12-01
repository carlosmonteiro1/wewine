package com.wewine.wewine.Service;

import com.wewine.wewine.DTO.RepresentanteRequestDTO;
import com.wewine.wewine.DTO.RepresentanteResponseDTO;
import com.wewine.wewine.Entity.RepresentanteEntity;
import com.wewine.wewine.Repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepresentanteService {

    private final RepresentanteRepository representanteRepository;

    @Autowired
    public RepresentanteService(RepresentanteRepository representanteRepository) {
        this.representanteRepository = representanteRepository;
    }

    // Mapeamento DTO -> Entity
    private RepresentanteEntity toEntity(RepresentanteRequestDTO dto) {
        RepresentanteEntity entity = new RepresentanteEntity();
        entity.setNome(dto.getNome());
        entity.setCpfCnpj(dto.getCpfCnpj());
        entity.setRgIe(dto.getRgIe());
        entity.setNascimento(dto.getNascimento());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setSituacaoLegal(dto.getSituacaoLegal());
        entity.setStatus(dto.getStatus());
        entity.setEmail(dto.getEmail());
        entity.setCelularWhatsapp(dto.getCelularWhatsapp());
        entity.setCep(dto.getCep());
        entity.setEndereco(dto.getEndereco());
        entity.setNumero(dto.getNumero());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setRegiaoAtuacao(dto.getRegiaoAtuacao());
        entity.setRegraComissao(dto.getRegraComissao());
        entity.setObservacoes(dto.getObservacoes());
        entity.setBanco(dto.getBanco());
        entity.setAgencia(dto.getAgencia());
        entity.setConta(dto.getConta());
        entity.setTipoConta(dto.getTipoConta());
        entity.setConcederAcessoApp(dto.getConcederAcessoApp());
        entity.setLoginAplicativo(dto.getLoginAplicativo());
        entity.setSenhaAcesso(dto.getSenhaAcesso());
        return entity;
    }

    // Mapeamento Entity -> DTO
    private RepresentanteResponseDTO toResponseDTO(RepresentanteEntity entity) {
        RepresentanteResponseDTO dto = new RepresentanteResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setRgIe(entity.getRgIe());
        dto.setNascimento(entity.getNascimento());
        dto.setNomeFantasia(entity.getNomeFantasia());
        dto.setSituacaoLegal(entity.getSituacaoLegal());
        dto.setStatus(entity.getStatus());
        dto.setEmail(entity.getEmail());
        dto.setCelularWhatsapp(entity.getCelularWhatsapp());
        dto.setCep(entity.getCep());
        dto.setEndereco(entity.getEndereco());
        dto.setNumero(entity.getNumero());
        dto.setComplemento(entity.getComplemento());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setRegiaoAtuacao(entity.getRegiaoAtuacao());
        dto.setRegraComissao(entity.getRegraComissao());
        dto.setObservacoes(entity.getObservacoes());
        dto.setBanco(entity.getBanco());
        dto.setAgencia(entity.getAgencia());
        dto.setConta(entity.getConta());
        dto.setTipoConta(entity.getTipoConta());
        dto.setConcederAcessoApp(entity.getConcederAcessoApp());
        dto.setLoginAplicativo(entity.getLoginAplicativo());
        return dto;
    }

    public RepresentanteResponseDTO createRepresentante(RepresentanteRequestDTO requestDTO) {
        RepresentanteEntity entity = toEntity(requestDTO);
        RepresentanteEntity savedEntity = representanteRepository.save(entity);
        return toResponseDTO(savedEntity);
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