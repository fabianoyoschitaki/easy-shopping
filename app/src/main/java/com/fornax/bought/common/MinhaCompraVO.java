package com.fornax.bought.common;

import com.fornax.bought.enums.StatusCompraEnum;

import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhaCompraVO {

    private String codigo;
    private BigDecimal valorTotal;
    private StatusCompraEnum status;
    private String urlFoto;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public StatusCompraEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCompraEnum status) {
        this.status = status;
    }

    public MinhaCompraVO(String codigo, BigDecimal valorTotal, StatusCompraEnum status, String urlFoto){
        this.codigo = codigo;
        this.valorTotal = valorTotal;
        this.status = status;
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
