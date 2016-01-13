package com.fornax.bought.rest;

import com.fornax.bought.common.CarrinhoVO;
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
    @GET(Constants.REST_OBTER_PRODUTO_URL)
    public void obterProduto(@Path("codigoBarras") String codigoBarras, Callback<ProdutoVO> produtoResponse);

    @GET(Constants.REST_AUTENTICAR_URL)
    public void autenticar(@Path("email") String email, @Path("senha") String senha, Callback<LoginVO> loginResponse);

    @GET(Constants.REST_TODOS_MERCADOS)
    public void obterTodosMercados(Callback<List<MercadoVO>> mercadosResponse);

    @POST(Constants.REST_CARRINHO_NOVO)
    public void obterNovoCarrinho(@Path("qrCode") String qrCode, @Body UsuarioVO usuarioLogado, Callback<CarrinhoVO> carrinhoResponse);
}
