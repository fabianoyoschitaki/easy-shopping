package com.fornax.bought.fragment;

import android.app.Activity;
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
import android.widget.TextView;


import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.activity.EscolherMercadoActivity;
import com.fornax.bought.activity.PegarCarrinhoActivity;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.mock.ComprasMock;

import bought.fornax.com.bought.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Rodrigo on 21/11/15.
 */
public class InicioFragment extends android.app.Fragment {

    private static final String TAG = InicioFragment.class.getName();

    private ListView categoriasListView;
    private ProgressDialog dialog;
    private Button iniciarCompraButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        iniciarCompraButton = (Button) rootView.findViewById(R.id.btn_iniciar_compra);
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
                    Intent carrinhoCompras = new Intent(getActivity(), CarrinhoComprasActivity.class);
                    MercadoVO mercadoEscolhido = ComprasMock.getMercadoPeloQRCode(resultado);
                    carrinhoCompras.putExtra("mercadoEscolhido", mercadoEscolhido);
                    startActivity(carrinhoCompras);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
