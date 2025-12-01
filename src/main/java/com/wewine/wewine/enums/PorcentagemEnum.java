package com.wewine.wewine.enums;

public enum PorcentagemEnum {
    // Adicionar as porcentagens necessárias manualmente
    PORCENTAGEM_5("5%"),
    PORCENTAGEM_10("10%"),
    PORCENTAGEM_15("15%");

    private final String descricao;

    PorcentagemEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

