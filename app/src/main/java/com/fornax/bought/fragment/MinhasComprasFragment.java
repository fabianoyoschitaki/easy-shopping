package com.fornax.bought.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fornax.bought.adapter.MinhasComprasAdapter;
import com.fornax.bought.common.MinhaCompraVO;
import com.fornax.bought.mock.ComprasMock;

import java.util.List;

import com.fornax.bought.R;

public class MinhasComprasFragment extends Fragment {

    private ListView listMinhasCompras;
    private List<MinhaCompraVO> listCompras;
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

        listMinhasCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.dialog_minhas_compras).show();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void populateComprasListView() {
        MinhasComprasAdapter minhasComprasAdapter = new MinhasComprasAdapter(getActivity(), listCompras);
        listMinhasCompras.setAdapter(minhasComprasAdapter);
    }

}
