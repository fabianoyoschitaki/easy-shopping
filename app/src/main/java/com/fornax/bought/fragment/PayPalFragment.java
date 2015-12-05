package com.fornax.bought.fragment;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.paypal.SampleActivity;
import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import bought.fornax.com.bought.R;

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
