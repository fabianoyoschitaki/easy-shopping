package com.fornax.bought.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fornax.bought.adapter.MinhasComprasAdapter;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.mock.ComprasMock;

import org.w3c.dom.Text;

import java.util.List;

import bought.fornax.com.bought.R;

public class MinhasComprasFragment extends android.app.Fragment implements AdapterView.OnItemClickListener {

    private ListView listMinhasCompras;
    private List<CompraVO> listCompras;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_minhas_compras, container, false);
        listMinhasCompras = (ListView) rootView.findViewById(R.id.compras_list_view);
        if (listCompras == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Carregando compras");
            dialog.show();
            listCompras = ComprasMock.getCompras();
            dialog.dismiss();
        }

        populateComprasListView();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void populateComprasListView() {
        MinhasComprasAdapter minhasComprasAdapter = new MinhasComprasAdapter(getActivity(), listCompras);
        listMinhasCompras.setAdapter(minhasComprasAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
