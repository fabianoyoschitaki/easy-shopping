package com.fornax.bought.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.activity.TelaPrincipalActivity;
import com.fornax.bought.adapter.MinhasComprasAdapter;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.LoginVO;
import com.fornax.bought.common.MinhaCompraVO;

import java.util.List;

import com.fornax.bought.R;
import com.fornax.bought.common.UsuarioVO;
import com.fornax.bought.mock.IBoughtMock;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.Constants;
import com.fornax.bought.utils.SessionUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MinhasComprasFragment extends Fragment {

    @Bind(R.id.itemListView)RecyclerView itemListView;
    private ProgressDialog dialog;
    @Bind(R.id.progressBarHolder)
    FrameLayout progressBarHolder;
    private MinhasComprasAdapter minhasComprasAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_minhas_compras, container, false);
        ButterKnife.bind(this, rootView);

        new ObterComprasTask().execute(SessionUtils.getUsuario());
        return rootView;
    }


    private class ObterComprasTask extends AsyncTask<Object, Void, List<CompraVO>> {

        AlphaAnimation inAnimation;
        AlphaAnimation outAnimation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<CompraVO> compras) {
            super.onPostExecute(compras);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            obterComprasResult(compras);
        }

        @Override
        protected List<CompraVO> doInBackground(Object... params) {
            List<CompraVO> retorno = null;
            try {
                WSRestService restClient = new WSRestService();
                UsuarioVO usuarioVO = (UsuarioVO) params[0];
                retorno = restClient.getRestAPI().obterCompras(usuarioVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retorno;
        }
    }

    /**
     * Após o retorno das compras pela Task
     *
     */
    private void obterComprasResult(List<CompraVO> compras) {
        if (compras != null) {
            minhasComprasAdapter = new MinhasComprasAdapter(compras, this);
            itemListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemListView.setAdapter(minhasComprasAdapter);
        }
    }
}
