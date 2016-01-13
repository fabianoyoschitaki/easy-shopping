package com.fornax.bought.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fornax.bought.adapter.MercadoListAdapter;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.rest.RestClient;

import java.util.List;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EscolherMercadoActivity extends AppCompatActivity{

    private static List<MercadoVO> mercados;

    @Bind(R.id.mercadosList) ListView mercadosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_mercado);
        ButterKnife.bind(this);
        if (mercados == null) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(this);
            dialog.setMessage("Localizando os mercados mais próximos..");
            dialog.show();
            RestClient restClient = new RestClient();
            restClient.getRestAPI().obterTodosMercados(new Callback<List<MercadoVO>>() {
                @Override
                public void success(List<MercadoVO> mercadosResponse, Response response) {
                    dialog.dismiss();
                    onMercadosLoaded(mercadosResponse);
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * Método que carrega os mercados quando voltam da chamada REST
     * @param mercadosResponse
     */
    private void onMercadosLoaded(List<MercadoVO> mercadosResponse) {
        mercados = mercadosResponse;
        MercadoListAdapter adapter = new MercadoListAdapter(this, mercados);
        mercadosListView.setAdapter(adapter);
        mercadosListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                MercadoVO mercadoEscolhido = mercados.get(position);
                Intent intent = new Intent(getApplicationContext(), CarrinhoComprasActivity.class);
                intent.putExtra("mercadoEscolhido", mercadoEscolhido);
                startActivity(intent);
            }
        });
    }

}
