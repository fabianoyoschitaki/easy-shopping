package com.fornax.bought.common;

import com.fornax.bought.enums.StatusCompra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CompraVO implements Serializable{

	private Long id;
	private String numeroSessao;
	private BigDecimal valorTotal;
	private UsuarioVO usuarioVO;
	private EstabelecimentoVO estabelecimentoVO;
	private List<ItemCompraVO> itensCompraVO;
	private StatusCompra statusCompra;

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}
	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}
	public EstabelecimentoVO getEstabelecimentoVO() {
		return estabelecimentoVO;
	}
	public void setEstabelecimentoVO(EstabelecimentoVO estabelecimentoVO) {
		this.estabelecimentoVO = estabelecimentoVO;
	}
	public List<ItemCompraVO> getItensCompraVO() {
		return itensCompraVO;
	}
	public void setItensCompraVO(List<ItemCompraVO> itensCompraVO) {
		this.itensCompraVO = itensCompraVO;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumeroSessao() {
		return numeroSessao;
	}
	public void setNumeroSessao(String numeroSessao) {
		this.numeroSessao = numeroSessao;
	}

	public StatusCompra getStatusCompra() {
		return statusCompra;
	}

	public void setStatusCompra(StatusCompra statusCompra) {
		this.statusCompra = statusCompra;
	}
}