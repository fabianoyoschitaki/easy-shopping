package com.fornax.bought.enums;

public enum StatusCompraENUM {

	AGUARDANDO_PAGAMENTO((short)1,"AGUARDANDO PAGAMENTO"),
	PAGA((short)2,"PAGA"),
	CANCELADO((short)3, "CANCELADA"),
	ENCERRADA((short)4, "ENCERRADA");
	
	private Short codigo;
	private String descricao;
	
	public static StatusCompraENUM get(Short codigo){
		StatusCompraENUM retorno = null;
		for (StatusCompraENUM statusCompraEnum : values()) {
			if(statusCompraEnum.getCodigo().equals(codigo)){
				retorno = statusCompraEnum;
			}
		}
		return retorno;
	}
	
	StatusCompraENUM(final Short codigo, final String descricao){
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
