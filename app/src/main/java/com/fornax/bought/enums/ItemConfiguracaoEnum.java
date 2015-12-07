package com.fornax.bought.enums;

/**
 * Created by Hallan on 05/12/2015.
 */
public enum ItemConfiguracaoEnum {

    AJUDA(1, "Ajuda"),
    PERFIL(2, "Perfil"),
    ALERTAS(3, "Alertas");

    ItemConfiguracaoEnum(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    private int codigo;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
