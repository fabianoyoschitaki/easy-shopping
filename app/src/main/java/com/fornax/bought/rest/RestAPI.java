package com.fornax.bought.rest;

import com.fornax.bought.common.LoginVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.utils.Constants;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by root on 03/12/15.
 */
public interface RestAPI {
    @GET(Constants.REST_OBTER_PRODUTO_URL)
    public void obterProduto(@Path("codigoBarras") String codigoBarras, Callback<ProdutoVO> produtoResponse);

    @GET(Constants.REST_AUTENTICAR_URL)
    public void autenticar(@Path("email") String email, @Path("senha") String senha, Callback<LoginVO> loginResponse);
}
