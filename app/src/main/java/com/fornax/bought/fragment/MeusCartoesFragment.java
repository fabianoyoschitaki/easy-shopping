package com.fornax.bought.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fornax.bought.adapter.MinhasComprasAdapter;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.mock.ComprasMock;

import java.util.List;

import bought.fornax.com.bought.R;

public class MeusCartoesFragment extends android.app.Fragment implements AdapterView.OnItemClickListener {

    private ListView listMinhasCompras;
    private MinhasComprasAdapter minhasComprasAdapter;

    public ListView getListMinhasCompras() {
        return listMinhasCompras;
    }

    public void setListMinhasCompras(ListView listMinhasCompras) {
        this.listMinhasCompras = listMinhasCompras;
    }

    public List<CompraVO> getListCompras() {
        return listCompras;
    }

    public void setListCompras(List<CompraVO> listCompras) {
        this.listCompras = listCompras;
    }

    public MinhasComprasAdapter getMinhasComprasAdapter() {
        return minhasComprasAdapter;
    }

    public void setMinhasComprasAdapter(MinhasComprasAdapter minhasComprasAdapter) {
        this.minhasComprasAdapter = minhasComprasAdapter;
    }

    private List<CompraVO> listCompras;

    public MeusCartoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listCompras = ComprasMock.getCompras();
        minhasComprasAdapter = new MinhasComprasAdapter(container.getContext(), listCompras);

        View rView = inflater.inflate(R.layout.fragment_minhas_compras, container, false);

        listMinhasCompras = (ListView) rView.findViewById(R.id.listMinhasCompras);
        listMinhasCompras.setAdapter(minhasComprasAdapter);

        // Inflate the layout for this fragment
        return rView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
