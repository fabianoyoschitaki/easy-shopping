package com.fornax.bought.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Hallan on 06/12/2015.
 */
public class CompraVO {

    private List<ItemCompraVO> itens;
    private BigDecimal valorTotalCompra;
    private Calendar dataCompra;

    public List<ItemCompraVO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompraVO> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotalCompra() {
        return valorTotalCompra;
    }

    public void setValorTotalCompra(BigDecimal valorTotalCompra) {
        this.valorTotalCompra = valorTotalCompra;
    }

    public Calendar getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Calendar dataCompra) {
        this.dataCompra = dataCompra;
    }
}
