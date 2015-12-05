package com.fornax.bought.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MercadoVO implements Serializable {

    private Integer id;
    private String nome;
    private String descricao;
    private String nomeCidade;
    private String siglaEstado;
    private String nomeLogradouro;
    private String numeroLogradouro;
    private String tipoLogradouro;
    private String numeroCep;
    private String urlFoto;

    public MercadoVO(Integer id, String nome, String descricao, String nomeCidade, String siglaEstado, String nomeLogradouro, String numeroLogradouro, String tipoLogradouro, String numeroCep, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.nomeCidade = nomeCidade;
        this.siglaEstado = siglaEstado;
        this.nomeLogradouro = nomeLogradouro;
        this.numeroLogradouro = numeroLogradouro;
        this.tipoLogradouro = tipoLogradouro;
        this.numeroCep = numeroCep;
        this.urlFoto = urlFoto;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    public void setNomeLogradouro(String nomeLogradouro) {
        this.nomeLogradouro = nomeLogradouro;
    }

    public String getNumeroLogradouro() {
        return numeroLogradouro;
    }

    public void setNumeroLogradouro(String numeroLogradouro) {
        this.numeroLogradouro = numeroLogradouro;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNumeroCep() {
        return numeroCep;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
