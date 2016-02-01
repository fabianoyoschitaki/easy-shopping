package com.fornax.bought.enums;

public enum StatusCompra {

	PAGA((short)1,"PAGA"),
	AGUARDANDO_PAGAMENTO((short)2,"AGUARDANDO PAGAMENTO"),
	CANCELADO((short)3, "CANCELADO"),
	RECUSADA((short)4, "RECUSADA"),
	FINALIZADO((short)4, "FINALIZADO");
	
	private Short codigo;
	private String descricao;
	
	public static StatusCompra get(Short codigo){
		StatusCompra retorno = null;
		for (StatusCompra statusCompra : values()) {
			if(statusCompra.getCodigo().equals(codigo)){
				retorno = statusCompra;
			}
		}
		return retorno;
	}
	
	StatusCompra(final Short codigo, final String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Short getCodigo() {
		return codigo;
	}

	public void setCodigo(Short codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
