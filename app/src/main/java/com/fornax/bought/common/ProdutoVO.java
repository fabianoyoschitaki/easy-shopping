package com.fornax.bought.common;

/**
 * Created by Hallan on 29/11/2015.
 */
public class ProdutoVO{

    private String codigobarras;
    private Double preco;


    public ProdutoVO(){

    }

    public ProdutoVO(String codigobarras, Double preco) {
        this.codigobarras = codigobarras;
        this.preco = preco;
    }

    public String getCodigobarras() {
        return codigobarras;
    }

    public void setCodigobarras(String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
