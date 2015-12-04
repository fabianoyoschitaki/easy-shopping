package com.fornax.bought.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import bought.fornax.com.bought.R;

public class SucessoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        if(LoginActivity.codigosEscaneados != null) {
            LoginActivity.codigosEscaneados.clear();
        }

        final Random numRandomico = new Random();

        String codigoSucesso = String.valueOf(numRandomico.nextInt(10310 * 33165));
        TextView txt = (TextView) findViewById(R.id.txtCodigoSucesso);
        txt.setText(codigoSucesso);

        Button btn = (Button) findViewById(R.id.btn_voltar);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
