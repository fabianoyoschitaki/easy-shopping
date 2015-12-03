package com.fornax.bought.common;

import com.fornax.bought.utils.JSONUtil;

/**
 * Edited by Fabiano on 03/12/2015.
 */
public class ProdutoVO{

    private Integer id;
    private String nome;
    private Double preco;
    private String codigoBarra;
    private String categoria;
    private String marca;
    private String urlImagem;

    public ProdutoVO(){}

    public ProdutoVO(Integer id, String nome, Double preco,
                     String codigoBarra, String categoria, String marca,
                     String urlImagem) {
        super();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.codigoBarra = codigoBarra;
        this.categoria = categoria;
        this.marca = marca;
        this.urlImagem = urlImagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getMarca() {
        return marca;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }


    public String getUrlImagem() {
        return urlImagem;
    }


    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String toString(){
        return JSONUtil.toJSON(this);
    }
}
