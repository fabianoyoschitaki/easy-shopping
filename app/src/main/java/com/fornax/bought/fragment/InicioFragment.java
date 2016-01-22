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
import com.fornax.bought.common.CarrinhoVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.common.UsuarioVO;
import com.fornax.bought.rest.RestClient;

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
                    // TODO usuario de verdade
                    UsuarioVO usuario = new UsuarioVO();
                    usuario.setEmail("admin@bought.com.br");
                    usuario.setSenha("123456");

                    restClient.getRestAPI().obterNovoCarrinho(resultado, usuario, new Callback<CarrinhoVO>() {
                        @Override
                        public void success(CarrinhoVO carrinhoResponse, Response response) {
                            dialog.dismiss();
                            onCarrinhoLoaded(carrinhoResponse);
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
     * Método quando o carrinho é obtido com sucesso
     * @param carrinhoResponse
     */
    private void onCarrinhoLoaded(CarrinhoVO carrinhoResponse) {
        Intent carrinhoCompras = new Intent(getActivity(), CarrinhoComprasActivity.class);
        MercadoVO mercadoEscolhido = carrinhoResponse.getMercado();
        carrinhoCompras.putExtra("mercadoEscolhido", mercadoEscolhido);
        startActivity(carrinhoCompras);
    }
}
