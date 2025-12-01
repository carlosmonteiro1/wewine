package com.wewine.wewine.enums;

public enum CidadeEnum {
    // Adicionar as cidades necessárias manualmente
    CIDADE_1("Cidade 1"),
    CIDADE_2("Cidade 2"),
    CIDADE_3("Cidade 3");

    private final String descricao;

    CidadeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

