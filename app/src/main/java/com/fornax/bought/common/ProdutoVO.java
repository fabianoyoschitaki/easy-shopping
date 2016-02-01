package com.fornax.bought.common;


import java.io.Serializable;

public class ProdutoVO implements Serializable {

	private Long id;
	private String nome;
	private String codigoBarra;
	private String urlImagem;

	
	// private Categoria categoria;

	// private Marca marca;

	// public Categoria getCategoria() {
	// return categoria;
	// }
	//
	// public void setCategoria(Categoria categoria) {
	// this.categoria = categoria;
	// }
	//
	// public Marca getMarca() {
	// return marca;
	// }
	//
	// public void setMarca(Marca marca) {
	// this.marca = marca;
	// }

	
	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
}