package com.fornax.bought.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.activity.LoginActivity;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.EstabelecimentoVO;
import com.fornax.bought.common.LoginVO;
import com.fornax.bought.common.UsuarioVO;
import com.fornax.bought.mock.ComprasMock;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.SharedPreferencesUtil;

import bought.fornax.com.bought.R;
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

    @Bind(R.id.btn_iniciar_compra) ImageButton iniciarCompraButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        ButterKnife.bind(this, rootView);

        iniciarCompraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Intent intent = new Intent(getActivity(), PegarCarrinhoActivity.class);

                //https://androidcookbook.com/Recipe.seam?recipeId=3324
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);	//Barcode Scanner to scan for us
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
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String qrCodeFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String resultado = intent.getStringExtra("SCAN_RESULT");
                if ("QR_CODE".equals(qrCodeFormat)){
                    final ProgressDialog dialog;
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Pegando carrinho...");
                    dialog.show();

                    RestClient restClient = new RestClient();

                    //TODO PROVISORIO...
                    UsuarioVO usuario = ComprasMock.getUsuario();

                    restClient.getRestAPI().getNovaCompra(resultado, usuario, new Callback<CompraVO>() {
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
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    /**
     * Método quando a compra é obtida com sucesso
     * @param compraResponse
     */
    private void onCompraLoaded(CompraVO compraResponse) {
        if(compraResponse != null && compraResponse.getId() != null){
            Intent carrinhoCompras = new Intent(getActivity(), CarrinhoComprasActivity.class);
            carrinhoCompras.putExtra("compra", compraResponse);
            startActivity(carrinhoCompras);
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Desculpe, não foi possível pegar o carrinho.", Toast.LENGTH_SHORT).show();
        }
    }
}
