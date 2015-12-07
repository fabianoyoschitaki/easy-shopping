package com.fornax.bought.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.adapter.ConfiguracoesAdapter;
import com.fornax.bought.mock.ComprasMock;

import bought.fornax.com.bought.R;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class ConfiguracoesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = ConfiguracoesFragment.class.getName();

    private ListView listConfiguracoes;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        listConfiguracoes = (ListView) rootView.findViewById(R.id.listConfiguracoes);
        listConfiguracoes.setOnItemClickListener(this);

        popularItensConfiguracoes();

        return rootView;
    }

    public void popularItensConfiguracoes(){
        ConfiguracoesAdapter adapter = new ConfiguracoesAdapter(getActivity());
        listConfiguracoes.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
