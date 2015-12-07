package com.fornax.bought.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fornax.bought.adapter.MercadoListAdapter;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.mock.ComprasMock;

import java.util.List;

import bought.fornax.com.bought.R;

public class EscolherMercadoActivity extends AppCompatActivity{

    private List<MercadoVO> mercados;
    private ListView mercadosListView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_mercado);

        mercadosListView = (ListView) findViewById(R.id.mercadosList);
        if (mercados == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Localizando os mercados mais próximos..");
            dialog.show();
            mercados = ComprasMock.getMercados();
            dialog.dismiss();
        }

        MercadoListAdapter adapter = new MercadoListAdapter(this, mercados);
        mercadosListView.setAdapter(adapter);
        mercadosListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                MercadoVO mercadoEscolhido = mercados.get(position);
                Intent intent = new Intent(getApplicationContext(), CarrinhoComprasActivity.class);
                intent.putExtra("mercadoEscolhido", mercadoEscolhido);
                startActivity(intent);
            }
        });
    }

}
