package com.fornax.bought.rest;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.LoginVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.common.UsuarioVO;
import com.fornax.bought.utils.Constants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by root on 03/12/15.
 */
public interface RestAPI {
    @GET(Constants.REST_OBTER_ITEM_COMPRA_URL)
    public void obterItemCompraPorCodigoBarra(
        @Path("codigoBarras") String codigoBarras,
        @Path("codigoEstabelecimento") String codigoEstabelecimento,
        Callback<ItemCompraVO> itemCompraResponse);

    @POST(Constants.REST_FINALIZAR_COMPRA)
    public void finalizarCompra(
        @Body CompraVO compraVO,
        Callback<CompraVO> compraResponse);

    @GET(Constants.REST_AUTENTICAR_URL)
    public LoginVO autenticar(
        @Path("email") String email,
        @Path("senha") String senha);

    @GET(Constants.REST_CADASTRAR_NOVO_USUARIO)
    public LoginVO cadastrarNovoUsuario(
        @Path("nome") String nome,
        @Path("email") String email,
        @Path("senha") String senha,
        @Path("cpf") String cpf,
        @Path("dataNascimento") String dataNascimento);

    @GET(Constants.REST_TODOS_MERCADOS)
    public void obterTodosMercados(Callback<List<MercadoVO>> mercadosResponse);

    @POST(Constants.REST_NOVA_COMPRA)
    public void getNovaCompra(@Path("codigoEstabelecimento") String codigoEstabelecimento, @Body UsuarioVO usuarioLogado, Callback<CompraVO> compraResponse);
}
