package com.fornax.bought.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.adapter.ProdutoAdapter;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import bought.fornax.com.bought.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarrinhoComprasActivity extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    /** lista que representa o carrinho de compras **/
    private List<ProdutoVO> produtos = new ArrayList<ProdutoVO>();

    /** valor total das compras **/
    private Double valorTotal = 0.0;

    /** mercado escolhido anteriormente **/
    private MercadoVO mercadoEscolhido;

    private ProdutoAdapter produtoAdapter;

    @InjectView(R.id.produtos_list_view) ListView produtoListView;
    @InjectView(R.id.btn_scan) Button btnScan;
    @InjectView(R.id.btn_finalizar) Button btnFinalizar;
    @InjectView(R.id.txtValorTotal) TextView txtValorTotal;

    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_compras);
        ButterKnife.inject(this);

        if (getIntent().getExtras().getSerializable("mercadoEscolhido") != null){
            mercadoEscolhido = (MercadoVO) getIntent().getExtras().getSerializable("mercadoEscolhido");
            Toast.makeText(getApplicationContext(), mercadoEscolhido.getNome(), Toast.LENGTH_SHORT).show();
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaScan();
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaConfirmarFinalizar();
            }
        });
    }

    public void abrirTelaScan(){
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(CarrinhoComprasActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void abrirTelaConfirmarFinalizar(){
        Intent intent = new Intent(getApplicationContext(), ConfirmacaoActivity.class);
        intent.putExtra("valorTotal", valorTotal);
        startActivity(intent);
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
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                final String codigoBarras = intent.getStringExtra("SCAN_RESULT");
                //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                if (codigoBarras != null){
                    dialog = new ProgressDialog(this);
                    dialog.setMessage("Buscando produto");
                    dialog.show();

                    RestClient restClient = new RestClient();
                    restClient.getRestAPI().obterProduto(codigoBarras, new Callback<ProdutoVO>() {
                        @Override
                        public void success(ProdutoVO produtoResponse, Response response) {
                            dialog.dismiss();
                            if (produtoResponse != null) {
                                atualizaListaProdutos(produtoResponse);
                            } else {
                                Toast.makeText(getApplicationContext(), "Produto " + codigoBarras + " nao encontrado!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        }
    }

    /**
     * Método que atualiza lista com novo produto escaneado
     * @param novoProduto
     */
    private void atualizaListaProdutos(ProdutoVO novoProduto) {
        produtos.add(novoProduto);
        valorTotal += novoProduto.getPreco();
        txtValorTotal.setText(Utils.getValorFormatado(valorTotal));

        // Getting adapter by passing xml data ArrayList
        produtoAdapter = new ProdutoAdapter(this, produtos);
        produtoListView.setAdapter(produtoAdapter);

        // Click event for single list row
        produtoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO remover? editar quantidade?
            }
        });
    }
}
