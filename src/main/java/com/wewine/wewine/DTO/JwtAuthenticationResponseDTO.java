package com.wewine.wewine.DTO;

import com.wewine.wewine.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";

    private Long id;
    private String nome;
    private TipoUsuario tipo;

    public JwtAuthenticationResponseDTO(String jwt, Long id, String nome, TipoUsuario tipo) {
    }
}
