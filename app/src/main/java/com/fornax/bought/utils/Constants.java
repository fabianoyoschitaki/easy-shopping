package com.fornax.bought.utils;

/**
 * Created by root on 03/12/15.
 */
public class Constants {
    public static final String REST_BASE_BOUGHT_URL = "http://54.200.195.122/bought-web";

    public static final String REST_TODOS_PRODUTOS_URL = "/produtos/todos";
    public static final String REST_OBTER_PRODUTO_URL = "/produtos/obterproduto/{codigoBarras}";
    public static final String REST_AUTENTICAR_URL = "/login/autenticar/{email}/{senha}";

    public static final String REST_TODOS_MERCADOS = "/mercados/todos";

    public static final String REST_CARRINHO_NOVO = "/carrinho/obter/novo/{qrCode}";

    public static final Integer LOGIN_CODIGO_SUCESSO = 0;
}
