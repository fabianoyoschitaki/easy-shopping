package com.fornax.bought.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.activity.ConfirmacaoCompraActivity;
import com.fornax.bought.activity.PagamentoEfetuadoActivity;
import com.fornax.bought.adapter.ConfiguracoesAdapter;
import com.fornax.bought.adapter.ItemCompraFinalizadaAdapter;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.enums.StatusCompraENUM;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.PayPalUtil;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ConfiguracoesFragment extends Fragment {

    private static final String TAG = "CONFIGURACOES";
    @Bind(R.id.coordinatorLayout)CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracoes, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }



}
