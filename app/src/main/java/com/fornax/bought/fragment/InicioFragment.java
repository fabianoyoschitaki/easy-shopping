package com.fornax.bought.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.activity.TelaPrincipalActivity;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.mock.IBoughtMock;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.FragmentIntentIntegrator;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.SharedPreferencesUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class InicioFragment extends Fragment {

    private static final String TAG = InicioFragment.class.getName();

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Bind(R.id.imgBtnIniciar)
    ImageButton imgBtnIniciar;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        ButterKnife.bind(this, rootView);

        imgBtnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (IBoughtMock.isMock){
                    onCompraLoaded(IBoughtMock.getCompraMock());
                } else {
                    IntentIntegrator integrator = IntentIntegrator.forSupportFragment(InicioFragment.this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setPrompt("Escaneie o QR Code");
                    integrator.addExtra("SCAN_MODE", "QR_CODE_MODE");
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();
                }
            }

        });
        return rootView;
    }

    /**
     * Depois que voltar o QR code
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            if (resultCode == Activity.RESULT_OK) {
                String qrCodeFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String codigoEstabelecimento = intent.getStringExtra("SCAN_RESULT");
                if ("QR_CODE".equals(qrCodeFormat)){
                    pegaCarrinho(codigoEstabelecimento);
                }
            }
        }
    }

    private void pegaCarrinho(String codigoEstabelecimento) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Pegando carrinho...");
        dialog.show();

        RestClient restClient = new RestClient();

        restClient.getRestAPI().getNovaCompra(codigoEstabelecimento, SessionUtils.getUsuario(), new Callback<CompraVO>() {
            @Override
            public void success(CompraVO compraResponse, Response response) {
                dialog.dismiss();
                onCompraLoaded(compraResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método quando a compra é obtida com sucesso
     * @param compraResponse
     */
    private void onCompraLoaded(CompraVO compraResponse) {
        if (compraResponse != null
         && compraResponse.getId() != null
         && compraResponse.getEstabelecimentoVO() != null){
            SessionUtils.setCompra(compraResponse);
            Intent carrinhoCompras = new Intent(getActivity(), CarrinhoComprasActivity.class);
            startActivity(carrinhoCompras);
            getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Desculpe, não foi possível pegar o carrinho.", Toast.LENGTH_SHORT).show();
        }
    }
}
