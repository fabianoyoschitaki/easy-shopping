package com.fornax.bought.common;

import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class CompraVO {

    private String codigo;
    private BigDecimal valorTotal;
    private Boolean finalizada;
    private String urlFoto;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public CompraVO(String codigo, BigDecimal valorTotal, Boolean finalizada, String urlFoto){
        this.codigo = codigo;
        this.valorTotal = valorTotal;
        this.finalizada = finalizada;
        this.urlFoto = urlFoto;
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
