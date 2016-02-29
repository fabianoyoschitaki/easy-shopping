package com.fornax.bought.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.enums.StatusCompra;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.Utils;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarrinhoFinalizadoActivity extends AppCompatActivity {

    @Bind(R.id.btn_efetuar_pagamento) Button btnEfetuarPagamento;
    @Bind(R.id.btn_voltar) Button btnVoltar;
    @Bind(R.id.txt_valor_total_compra) TextView txtValorTotalCompra;
    BigDecimal valorTotal = BigDecimal.ZERO;
    private CompraVO compraVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_finalizado);
        ButterKnife.bind(this);

        if (getIntent().getExtras().getSerializable("compra") != null) {
            compraVO = (CompraVO) getIntent().getExtras().getSerializable("compra");
            txtValorTotalCompra.setText(Utils.getValorFormatado(compraVO.getValorTotal()));
        }
        // volta pra activity anterior
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEfetuarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarCompraWS();
            }
        });
    }

    public void finalizarCompraWS(){
        RestClient restClient = new RestClient();
        restClient.getRestAPI().finalizarCompra(compraVO, new Callback<CompraVO>() {
            @Override
            public void success(CompraVO compraResponse, Response response) {
                if(compraResponse != null && compraResponse.getStatusCompra() != null &&
                        compraResponse.getStatusCompra().equals(StatusCompra.FINALIZADO)){
                    Intent intent = new Intent(CarrinhoFinalizadoActivity.this, EscolherFormaPagamentoActivity.class);
                    intent.putExtra("compra", compraResponse);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "A compra não foi finalizada.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}