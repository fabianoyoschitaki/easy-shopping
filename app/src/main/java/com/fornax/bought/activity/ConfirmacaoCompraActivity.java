package com.fornax.bought.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fornax.bought.R;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.utils.Utils;

import java.util.ArrayList;

public class ConfirmacaoCompraActivity extends AppCompatActivity {

    private MercadoVO mercadoEscolhido;
    private ArrayList<ItemCompraVO> itens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_compra);

        Bundle extras = getIntent().getExtras();
        Double value = 0.0;
        if (extras != null) {
            value = extras.getDouble("valorTotal");
        }
        TextView txtMsgConfirmacao = (TextView) findViewById(R.id.txtMsgConfirmacao);
        txtMsgConfirmacao.setText("Deseja confirmar a compra de R$ " + Utils.getValorFormatado(value));

        ImageButton btn_sim = (ImageButton) findViewById(R.id.btn_sim);
        btn_sim.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SucessoActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_nao = (ImageButton) findViewById(R.id.btn_nao);
        btn_nao.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (getIntent().getExtras().getSerializable("mercadoEscolhido") != null){
                    mercadoEscolhido = (MercadoVO) getIntent().getExtras().getSerializable("mercadoEscolhido");
                }

                Intent intent = new Intent(getApplicationContext(), CarrinhoComprasActivity.class);
                intent.putExtra("mercadoEscolhido", mercadoEscolhido);
                intent.putExtra("itens", itens);
                startActivity(intent);
            }
        });

    }
}