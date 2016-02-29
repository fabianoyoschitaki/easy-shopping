package com.fornax.bought.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.fornax.bought.paypal.SampleActivity;

import com.fornax.bought.R;

/**
 * Created by Hallan on 03/12/15.
 */
public class PayPalFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = InicioFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.paypal_fragment, container, false);
        Button btn_iniciar = (Button) rootView.findViewById(R.id.btn_iniciar);
        btn_iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(container.getContext(), SampleActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
