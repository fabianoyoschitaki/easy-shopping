package com.fornax.bought.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.adapter.MercadoListAdapter;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.mock.ComprasMock;

import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class OndeComprarFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = OndeComprarFragment.class.getName();

    private List<MercadoVO> mercados;
    private ListView mercadosListView;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onde_comprar, container, false);
        mercadosListView = (ListView) rootView.findViewById(R.id.mercado_list_view);
        mercadosListView.setOnItemClickListener(this);
        if (mercados == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Carregando Mercados");
            dialog.show();
            mercados = ComprasMock.getMercados();
            dialog.dismiss();
        }

        populateMercadosListView();

        // Inflate the layout for this fragment
        return rootView;
    }

    protected void populateMercadosListView() {
        MercadoListAdapter adapter = new MercadoListAdapter(getActivity(), mercados);
        mercadosListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MercadoVO mercadoEscolhido = mercados.get(position);
        Intent intent = new Intent(getActivity(), CarrinhoComprasActivity.class);
        intent.putExtra("mercadoEscolhido", mercadoEscolhido);
        startActivity(intent);
    }
}
