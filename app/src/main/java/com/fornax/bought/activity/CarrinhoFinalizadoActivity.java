package com.fornax.bought.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.utils.Utils;

import java.math.BigDecimal;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CarrinhoFinalizadoActivity extends AppCompatActivity {

    @Bind(R.id.btn_efetuar_pagamento) Button btnEfetuarPagamento;
    @Bind(R.id.btn_voltar) Button btnVoltar;
    @Bind(R.id.txt_valor_total_compra) TextView txtValorTotalCompra;
    BigDecimal valorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_finalizado);
        ButterKnife.bind(this);

        valorTotal = (BigDecimal) getIntent().getExtras().getSerializable("valorTotal");

        txtValorTotalCompra.setText(Utils.getValorFormatado(valorTotal));

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
                Intent intent = new Intent(CarrinhoFinalizadoActivity.this, PagamentoEfetuadoActivity.class);
                intent.putExtra("valorTotal", valorTotal);
                startActivity(intent);
            }
        });
    }
}
