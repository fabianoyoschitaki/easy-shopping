package com.fornax.bought.utils;

/**
 * Created by root on 03/12/15.
 */
public class Constants {
    public static final String REST_BASE_URL = "http://www.networkmi.com.br/networkmi";
    public static final String REST_BASE_BOUGHT_URL = "http://54.200.195.122/bought";

    public static final String REST_TODOS_PRODUTOS_URL = "/service/produtos/todos";
    public static final String REST_OBTER_PRODUTO_URL = "/service/produtos/obterproduto/{codigoBarras}"; //TODO mudar
    public static final String REST_AUTENTICAR_URL = "/rest/login/autenticar/{email}/{senha}";
}
