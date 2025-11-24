// java
package com.wewine.wewine.Service;

import com.wewine.wewine.DTO.ClienteRequestDTO;
import com.wewine.wewine.DTO.ClienteResponseDTO;
import com.wewine.wewine.Entity.ClienteEntity;
import com.wewine.wewine.Entity.RepresentanteEntity;
import com.wewine.wewine.Exception.ResourceNotFoundException;
import com.wewine.wewine.Repository.ClienteRepository;
import com.wewine.wewine.Repository.RepresentanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final RepresentanteRepository representanteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, RepresentanteRepository representanteRepository) {
        this.clienteRepository = clienteRepository;
        this.representanteRepository = representanteRepository;
    }

    /* Mapeamento DTO -> Entity */
    private ClienteEntity toEntity(ClienteRequestDTO dto, RepresentanteEntity representante) {
        ClienteEntity entity = new ClienteEntity();
        entity.setNomeRazaoSocial(dto.getNomeRazaoSocial());
        entity.setCpfCnpj(dto.getCpfCnpj());
        entity.setNomeResponsavel(dto.getNomeResponsavel());
        entity.setTelefone(dto.getTelefone());
        entity.setLogradouro(dto.getLogradouro());
        entity.setNumero(dto.getNumero());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setCep(dto.getCep());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setRepresentante(representante);
        return entity;
    }

    /* Mapeamento Entity -> DTO */
    private ClienteResponseDTO toResponseDTO(ClienteEntity entity) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNomeRazaoSocial(entity.getNomeRazaoSocial());
        dto.setCpfCnpj(entity.getCpfCnpj());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());

        String enderecoCompleto = entity.getLogradouro() + ", " + entity.getNumero() + " - " +
                entity.getBairro() + ", " + entity.getCidade() + "/" + entity.getEstado();
        dto.setEnderecoCompleto(enderecoCompleto);

        if (entity.getRepresentante() != null) {
            dto.setNomeRepresentante(entity.getRepresentante().getNome());
        }
        return dto;
    }

    /* CRUD */
    public ClienteResponseDTO createCliente(ClienteRequestDTO requestDTO, Long idRepresentante) {
        RepresentanteEntity representante = representanteRepository.findById(idRepresentante)
                .orElseThrow(() -> new ResourceNotFoundException("Representante com ID " + idRepresentante + " não encontrado."));
        ClienteEntity savedCliente = clienteRepository.save(toEntity(requestDTO, representante));
        return toResponseDTO(savedCliente);
    }

    public List<ClienteResponseDTO> findAllByRepresentanteId(Long idRepresentante) {
        List<ClienteEntity> entities = clienteRepository.findByRepresentanteId(idRepresentante);
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + id + " não encontrado."));
        return toResponseDTO(entity);
    }
}