package com.fornax.bought.common;

import java.math.BigDecimal;

/**
 * Created by Hallan on 06/12/2015.
 */
public class ItemCompraVO {

    private ProdutoVO produto;
    private Integer quantidade;
    private BigDecimal valorTotalItem;

    public ItemCompraVO(ProdutoVO produto, Integer quantidade){

    }

    public ProdutoVO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVO produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotalItem() {
        if(produto != null && produto.getPreco() != null && quantidade != null){
            valorTotalItem = new BigDecimal(produto.getPreco() * quantidade);
        }
        return valorTotalItem;
    }
}
