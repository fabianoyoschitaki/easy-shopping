package com.fornax.bought.fragment;

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

import bought.fornax.com.bought.R;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class InicioFragment extends android.app.Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = InicioFragment.class.getName();

    private ListView categoriasListView;
    private ProgressDialog dialog;

    private Button btn_iniciar;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        btn_iniciar = (Button) rootView.findViewById(R.id.btn_iniciar);
        btn_iniciar.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View v)
                {
                 Intent intent = new Intent(container.getContext(), CarrinhoComprasActivity.class);
                 startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
