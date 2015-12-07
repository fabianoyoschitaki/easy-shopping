package com.fornax.bought.enums;

/**
 * Created by Hallan on 05/12/2015.
 */
public enum StatusCompraEnum {
    PAGO(1,"Pago"),
    AGUARDANDO_PAGAMENTO(2,"Aguardando Pagamento"),
    CANCELADO(3,"Cancelado");

    private Integer codigo;
    private String descricao;

    StatusCompraEnum(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
