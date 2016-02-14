package com.fornax.bought.utils;

/**
 * Created by root on 03/12/15.
 */
public class Constants {
    public static final String REST_BASE_BOUGHT_URL = "http://ec2-54-200-195-122.us-west-2.compute.amazonaws.com/bought-web";

    public static final String REST_COMPRA_OBTER_ITEM_COMPRA_CODIGO_BARRA = "/compra/obterItemCompraPorCodigoBarra/{codigoBarras}/{codigoEstabelecimento}";
    public static final String REST_COMPRA_NOVA_COMPRA = "/compra/novaCompra/{codigoEstabelecimento}";
    public static final String REST_COMPRA_FINALIZAR_COMPRA = "/compra/finalizarCompra";

    //TODO ver com o lixo, remover login e colocar dentro de usuarios
    public static final String REST_USUARIO_CADASTRAR_USUARIO = "/usuarios/cadastrarUsuario";
    public static final String REST_AUTENTICAR_URL = "/login/autenticar/{email}/{senha}";

    public static final String REST_TODOS_MERCADOS = "/mercados/todos";

    public static final Integer LOGIN_CODIGO_SUCESSO = 0;
    public static final Integer LOGIN_CODIGO_ERRO = -1;
}
