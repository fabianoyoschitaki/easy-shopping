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
import android.widget.Button;
import android.widget.EditText;
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

    private ItemCompraAdapter itemCompraAdapter;
    private ProgressDialog dialog;

    @Bind(R.id.itemListView)RecyclerView itemListView;
    @Bind(R.id.txtValorTotal)TextView txtValorTotal;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.coordinatorLayout)CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carrinho_compras, container, false);
        ButterKnife.bind(this, rootView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogEscolherForma = new Dialog(getActivity());
                dialogEscolherForma.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogEscolherForma.setContentView(R.layout.dialog_escolher_forma);
                dialogEscolherForma.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                Button btnScan = (Button) dialogEscolherForma.findViewById(R.id.btnScan);
                btnScan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        abrirTelaScan();
                        dialogEscolherForma.dismiss();
                    }
                });

                Button btnDigitar = (Button) dialogEscolherForma.findViewById(R.id.btnDigitar);
                btnDigitar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final Dialog dialogInserirCodBarra = new Dialog(getActivity());
                        dialogInserirCodBarra.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogInserirCodBarra.setContentView(R.layout.dialog_inserir_codbarra);
                        dialogInserirCodBarra.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                        final EditText edt = (EditText) dialogInserirCodBarra.findViewById(R.id.edtCodigoBarras);
                        if (edt.requestFocus()) {
                            dialogInserirCodBarra.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }

                        Button btnPronto = (Button) dialogInserirCodBarra.findViewById(R.id.btnPronto);
                        btnPronto.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
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
                        dialogInserirCodBarra.show();
                        dialogEscolherForma.dismiss();
                    }
                });
                dialogEscolherForma.show();
            }
        });
        atualizaListaProdutos();

        return rootView;
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
        txtValorTotal.setText(Utils.getValorFormatado(SessionUtils.getCompra().getValorTotal()));

        itemCompraAdapter = new ItemCompraAdapter(SessionUtils.getCompra().getItensCompraVO(), this);
        itemCompraAdapter.setCustomButtonListener(this);

        itemListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemListView.setAdapter(itemCompraAdapter);

        // Getting adapter by passing xml data ArrayList
        /** itemCompraAdapter = new ItemCompraAdapter(getActivity(), SessionUtils.getCompra().getItensCompraVO());

        // Click event for single list row
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog_editar);
                dialog.setTitle("Quantidade");
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                number.setMaxValue(100);
                number.setMinValue(1);

                ItemCompraVO itemSelecionado = SessionUtils.getCompra().getItensCompraVO().get(position);
                if (itemSelecionado != null) {
                    number.setValue(itemSelecionado.getQuantidade());
                }

                Button btnPronto = (Button) dialog.findViewById(R.id.btnPronto);
                btnPronto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                        SessionUtils.getCompra().getItensCompraVO().get(position).setQuantidade(number.getValue());

                        itemCompraAdapter = new ItemCompraAdapter(getActivity(), SessionUtils.getCompra().getItensCompraVO());
                        itemListView.setAdapter(itemCompraAdapter);

                        txtValorTotal.setText(Utils.getValorFormatado(SessionUtils.getCompra().getValorTotal()));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        }); **/
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
