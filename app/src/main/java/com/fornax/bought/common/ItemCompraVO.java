package com.fornax.bought.common;

import java.math.BigDecimal;


/**
 * Created by Hallan on 06/12/2015.
 */
public class ItemCompraVO {

	private Long id;
    private ProdutoVO produtoVO;
    private Integer quantidade;
    private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ProdutoVO getProdutoVO() {
		return produtoVO;
	}

	public void setProdutoVO(ProdutoVO produtoVO) {
		this.produtoVO = produtoVO;
	}
}
