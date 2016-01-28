package com.fornax.bought.fragment;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fornax.bought.activity.CarrinhoComprasActivity;
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

/**
 * Created by Rodrigo on 21/11/15.
 */
public class OndeComprarFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = OndeComprarFragment.class.getName();

    private List<MercadoVO> mercados;

    @Bind(R.id.mercado_recycler_view) RecyclerView mercadosRecyclerView;

    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onde_comprar, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mercadosRecyclerView.setLayoutManager(mLayoutManager);
        mercadosRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mercadosRecyclerView.setHasFixedSize(true);

        if (mercados == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Carregando Mercados");
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
                    Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    private void onMercadosLoaded(List<MercadoVO> mercadosResponse) {
        mercados = mercadosResponse;
        MercadoListAdapter adapter = new MercadoListAdapter(getContext(), mercados, onClickMercado());
        mercadosRecyclerView.setAdapter(adapter);
    }

    private MercadoListAdapter.MercadoOnClickListener onClickMercado() {
        return new MercadoListAdapter.MercadoOnClickListener(){
            @Override
            public void onClickMercado(View view, int idx) {
                MercadoVO mercadoEscolhido = mercados.get(idx);
                Intent intent = new Intent(getActivity(), CarrinhoComprasActivity.class);
                intent.putExtra("mercadoEscolhido", mercadoEscolhido);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MercadoVO mercadoEscolhido = mercados.get(position);
        Intent intent = new Intent(getActivity(), CarrinhoComprasActivity.class);
        intent.putExtra("mercadoEscolhido", mercadoEscolhido);
        startActivity(intent);
    }
}
