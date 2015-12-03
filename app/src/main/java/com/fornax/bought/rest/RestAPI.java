package com.fornax.bought.rest;

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
}
