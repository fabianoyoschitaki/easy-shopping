package com.fornax.bought.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fornax.bought.utils.Utils;

import bought.fornax.com.bought.R;
public class ConfirmacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        Bundle extras = getIntent().getExtras();
        Double value = 0.0;
        if (extras != null) {
            value = extras.getDouble("valorTotal");
        }
        TextView txtMsgConfirmacao = (TextView) findViewById(R.id.txtMsgConfirmacao);
        txtMsgConfirmacao.setText("Deseja confirmar a compra de R$ " + Utils.getValorFormatado(value));

        Button btn = (Button) findViewById(R.id.btn_confirmar);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SucessoActivity.class);
                startActivity(intent);
            }
        });

    }
}