package com.fornax.bought.common;


import com.fornax.bought.enums.StatusCompraENUM;

import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhaCompraVO {

    private String codigo;
    private BigDecimal valorTotal;
    private String urlFoto;
    private String statusCompra;

    public MinhaCompraVO(String codigo, BigDecimal valorTotal, StatusCompraENUM statusCompraENUM,String urlFoto){
        this.codigo = codigo;
        this.statusCompra = statusCompraENUM.getDescricao();
        this.valorTotal = valorTotal;
        this.urlFoto = urlFoto;
    }

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
