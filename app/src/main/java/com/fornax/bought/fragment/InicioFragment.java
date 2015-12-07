package com.fornax.bought.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.activity.EscolherMercadoActivity;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.mock.ComprasMock;

import bought.fornax.com.bought.R;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class InicioFragment extends android.app.Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = InicioFragment.class.getName();

    private ListView categoriasListView;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);

        ImageButton img = (ImageButton) rootView.findViewById(R.id.btn_iniciar);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getActivity(), EscolherMercadoActivity.class);
                startActivity(intent);
            }

        });

        return rootView;
    }

    protected synchronized void buildGoogleApiClient(Context context) {

        //mGoogleApiClient = new GoogleApiClient.Builder(this)
         //       .addConnectionCallbacks(this)
         //       .addOnConnectionFailedListener(this)
          //      .addApi(LocationServices.API)
            //    .build();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
