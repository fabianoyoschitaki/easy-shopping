package com.fornax.bought.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.adapter.ItemCompraAdapter;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.mock.IBoughtMock;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Fabia on 12/03/2016.
 */
public class CarrinhoComprasFragment extends Fragment implements ItemCompraAdapter.CustomButtonListener {
    private static ItemCompraVO ultimoItemRemovido;
    private static int posicaoUltimoItemRemovido;

    private static boolean areFabsVisible = false;

    private ItemCompraAdapter itemCompraAdapter;
    private ProgressDialog dialog;

    @Bind(R.id.fab_escanear_codigo) FloatingActionButton fabEscanearCodigo;
    @Bind(R.id.fab_digitar_codigo) FloatingActionButton fabDigitarCodigo;

    @Bind(R.id.itemListView)RecyclerView itemListView;
    @Bind(R.id.txtValorTotal)TextView txtValorTotal;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.coordinatorLayout)CoordinatorLayout coordinatorLayout;

    public static class Item{
        public final String text;
        public final int icon;
        public Item(String text, Integer icon) {
            this.text = text;
            this.icon = icon;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carrinho_compras, container, false);
        ButterKnife.bind(this, rootView);

        final Item[] items = {
                new Item("Bipar", R.drawable.scan_barcode),
                new Item("Digitar", R.drawable.keyboard_ibought),
        };

        final ListAdapter adapter = new ArrayAdapter<Item>(
                getActivity(),
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items){

            public View getView(int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                //Put the image on the TextView
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };

        fabDigitarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showHideFabs();
                final Dialog dialogInserirCodBarra = new Dialog(getActivity());
                dialogInserirCodBarra.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogInserirCodBarra.setContentView(R.layout.dialog_inserir_codbarra);

                final EditText edt = (EditText) dialogInserirCodBarra.findViewById(R.id.edtCodigoBarras);
                if (edt.requestFocus()) {
                    dialogInserirCodBarra.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

                Button bntOk = (Button) dialogInserirCodBarra.findViewById(R.id.bntOk);
                bntOk.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                    if (edt.getText() != null) {
                        if (IBoughtMock.isMock) {
                            onProdutoLoaded(IBoughtMock.getItemCompraMock(), edt.getText().toString());
                        } else {
                            buscarProduto(edt.getText().toString());
                        }
                    }
                    dialogInserirCodBarra.dismiss();
                    }
                });

                Button btnCancelar = (Button) dialogInserirCodBarra.findViewById(R.id.btnCancelar);
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialogInserirCodBarra.cancel();
                    }
                });
                dialogInserirCodBarra.show();
            }
        });

        fabEscanearCodigo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //showHideFabs();
                 abrirTelaScan();
             }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHideFabs();
            }
        });
        atualizaListaProdutos();

        return rootView;
    }

    private void showHideFabs() {
        FrameLayout.LayoutParams fabDigitarCodigoLayoutParams = (FrameLayout.LayoutParams) fabDigitarCodigo.getLayoutParams();
        FrameLayout.LayoutParams fabEscanearCodigoLayoutParams = (FrameLayout.LayoutParams) fabEscanearCodigo.getLayoutParams();

        if (!areFabsVisible){
            areFabsVisible = true;
            fabDigitarCodigoLayoutParams.rightMargin += (int) (fabDigitarCodigo.getWidth() * 0.75);
            fabDigitarCodigoLayoutParams.bottomMargin += (int) (fabDigitarCodigo.getHeight() * 0.50);
            fabDigitarCodigo.setLayoutParams(fabDigitarCodigoLayoutParams);
            fabDigitarCodigo.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab_digitar_codigo_show));
            fabDigitarCodigo.setClickable(true);

            fabEscanearCodigoLayoutParams.rightMargin -= (int) (fabEscanearCodigo.getWidth() * 0.75);
            fabEscanearCodigoLayoutParams.bottomMargin += (int) (fabEscanearCodigo.getHeight() * 0.50);
            fabEscanearCodigo.setLayoutParams(fabEscanearCodigoLayoutParams);
            fabEscanearCodigo.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab_escanear_codigo_show));
            fabEscanearCodigo.setClickable(true);
        } else {
            areFabsVisible = false;
            fabDigitarCodigoLayoutParams.rightMargin -= (int) (fabDigitarCodigo.getWidth() * 0.75);
            fabDigitarCodigoLayoutParams.bottomMargin -= (int) (fabDigitarCodigo.getHeight() * 0.50);
            fabDigitarCodigo.setLayoutParams(fabDigitarCodigoLayoutParams);
            fabDigitarCodigo.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab_digitar_codigo_hide));
            fabDigitarCodigo.setClickable(false);

            fabEscanearCodigoLayoutParams.rightMargin += (int) (fabEscanearCodigo.getWidth() * 0.75);
            fabEscanearCodigoLayoutParams.bottomMargin -= (int) (fabEscanearCodigo.getHeight() * 0.50);
            fabEscanearCodigo.setLayoutParams(fabEscanearCodigoLayoutParams);
            fabEscanearCodigo.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab_escanear_codigo_hide));
            fabEscanearCodigo.setClickable(false);
        }
    }

    public void abrirTelaScan(){
        try {
            IntentIntegrator integrator = new IntentIntegrator(getActivity());
            integrator.initiateScan();

        } catch (ActivityNotFoundException anfe) {
            showDialog(getActivity(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            if (resultCode == Activity.RESULT_OK) {
                //get the extras that are returned from the intent
                final String codigoBarras = intent.getStringExtra("SCAN_RESULT");
                //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                if (codigoBarras != null){
                    buscarProduto(codigoBarras);
                }
            }
        }
    }

    public void buscarProduto(final String codigoBarras){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Buscando produto");
        dialog.show();

        RestClient restClient = new RestClient();
        restClient.getRestAPI().obterItemCompraPorCodigoBarra(
                codigoBarras,
                SessionUtils.getCompra().getEstabelecimentoVO().getCodigoEstabelecimento(),
                new Callback<ItemCompraVO>() {
                    @Override
                    public void success(ItemCompraVO itemCompraResponse, Response response) {
                        onProdutoLoaded(itemCompraResponse, codigoBarras);
                        dialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    /**
     * Método que é carregado quando o produto é carregado
     *
     * @param itemCompraResponse
     */
    private void onProdutoLoaded(ItemCompraVO itemCompraResponse, String codigoBarras) {
        if (itemCompraResponse != null) {
            SessionUtils.getCompra().getItensCompraVO().add(itemCompraResponse);
            atualizaListaProdutos();
        } else {
            Toast.makeText(getContext(), "Produto " + codigoBarras + " nao encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que atualiza lista com novo produto escaneado
     */
    public void atualizaListaProdutos() {
        if(SessionUtils.getCompra() != null
                && SessionUtils.getCompra().getItensCompraVO() != null){
            BigDecimal valorTotal = BigDecimal.ZERO;
            for (ItemCompraVO item : SessionUtils.getCompra().getItensCompraVO()) {
                valorTotal = valorTotal.add(item.getValor());
            }
            SessionUtils.getCompra().setValorTotal(valorTotal);
            txtValorTotal.setText(Utils.getValorFormatado(valorTotal));
        }

        itemCompraAdapter = new ItemCompraAdapter(SessionUtils.getCompra().getItensCompraVO(), this);
        itemCompraAdapter.setCustomButtonListener(this);

        itemListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemListView.setAdapter(itemCompraAdapter);

    }

    @Override
    public void onButtonClickListener(int position) {
        ultimoItemRemovido = SessionUtils.getCompra().getItensCompraVO().remove(position);
        posicaoUltimoItemRemovido = position;
        atualizaListaProdutos();
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item removido.", Snackbar.LENGTH_LONG);
        snackbar.setAction("DESFAZER", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionUtils.getCompra().getItensCompraVO().add(posicaoUltimoItemRemovido, ultimoItemRemovido);
                atualizaListaProdutos();
                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Produto " + ultimoItemRemovido.getProdutoVO().getNome() + " adicionado!", Snackbar.LENGTH_SHORT);
                snackbar1.show();
            }
        });
        snackbar.show();
    }
}
