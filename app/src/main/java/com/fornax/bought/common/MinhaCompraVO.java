package com.fornax.bought.common;


import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhaCompraVO {

    private String codigo;
    private BigDecimal valorTotal;
    private String urlFoto;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
