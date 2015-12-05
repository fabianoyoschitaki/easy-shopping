package com.fornax.bought.common;

import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class CompraVO {

    private String codigo;
    private BigDecimal valorTotal;
    private Boolean finalizada;

    public CompraVO(BigDecimal valorTotal, Boolean finalizada){
        this.valorTotal = valorTotal;
        this.finalizada = finalizada;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
