package com.fornax.bought.common;


import com.fornax.bought.enums.StatusCompraENUM;

import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhaCompraVO {

    private String dataHora;
    private BigDecimal valorTotal;
    private String urlFoto;
    private StatusCompraENUM statusCompraENUM;

    public MinhaCompraVO(String dataHora, BigDecimal valorTotal, StatusCompraENUM statusCompraENUM,String urlFoto){
        this.dataHora = dataHora;
        this.statusCompraENUM = statusCompraENUM;
        this.valorTotal = valorTotal;
        this.urlFoto = urlFoto;
    }

    public StatusCompraENUM getStatusCompraENUM() {
        return statusCompraENUM;
    }

    public void setStatusCompraENUM(StatusCompraENUM statusCompraENUM) {
        this.statusCompraENUM = statusCompraENUM;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
