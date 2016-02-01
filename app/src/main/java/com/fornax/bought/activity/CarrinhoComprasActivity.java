package com.fornax.bought.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.adapter.ItemCompraAdapter;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.EstabelecimentoVO;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarrinhoComprasActivity extends AppCompatActivity implements ItemCompraAdapter.CustomButtonListener{
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    /** lista que representa o carrinho de compras **/
    private static List<ItemCompraVO> itens = new ArrayList<ItemCompraVO>();

    /** valor total das compras **/
    private BigDecimal valorTotal = new BigDecimal(0);

    /** mercado escolhido anteriormente **/
    private CompraVO compraVO;

    private EstabelecimentoVO estabelecimentoVO;

    private ItemCompraAdapter itemCompraAdapter;

    @Bind(R.id.itemListView)ListView itemListView;
    @Bind(R.id.btn_scan)Button btnScan;
    @Bind(R.id.btn_finalizar)Button btnFinalizar;
    @Bind(R.id.txtValorTotal)TextView txtValorTotal;

    private ProgressDialog dialog;

    /*static {
        ProdutoVO produto = new ProdutoVO();
        produto.setCategoria("Alimento");
        produto.setCodigoBarra("123123123");
        produto.setId(1);
        produto.setNome("Meus Bago");
        produto.setMarca("Galinha");
        produto.setPreco(new Double(3.12));
        produto.setUrlImagem("http://www.paodeacucar.com.br/img/uploads/1/354/473354x200x200.jpg");
        ItemCompraVO itemCompra = new ItemCompraVO(produto, 1);
        itens.add(itemCompra);

        produto = new ProdutoVO();
        produto.setCategoria("Enlatado");
        produto.setCodigoBarra("123123123");
        produto.setId(1);
        produto.setNome("Ervilha Zuada");
        produto.setMarca("CASINO");
        produto.setPreco(new Double(1.33));
        produto.setUrlImagem("http://www.paodeacucar.com.br/img/uploads/1/424/474424x200x200.jpg");
        itemCompra = new ItemCompraVO(produto, 1);
        itens.add(itemCompra);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_compras);
        ButterKnife.bind(this);

        if (getIntent().getExtras().getSerializable("compra") != null) {
            compraVO = (CompraVO) getIntent().getExtras().getSerializable("compra");
            estabelecimentoVO = compraVO.getEstabelecimentoVO();
            Toast.makeText(getApplicationContext(), estabelecimentoVO.getNome(), Toast.LENGTH_SHORT).show();
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogEscolherForma = new Dialog(CarrinhoComprasActivity.this);
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
                        final Dialog dialogInserirCodBarra = new Dialog(CarrinhoComprasActivity.this);
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
                                    buscarProduto(edt.getText().toString());
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


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compraVO.setItensCompraVO(itens);
                compraVO.setValorTotal(valorTotal);
                Intent intent = new Intent(getApplicationContext(), CarrinhoFinalizadoActivity.class);
                intent.putExtra("compra", compraVO);
                startActivity(intent);
            }
        });

        atualizaListaProdutos();
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

    /*public void abrirTelaConfirmarFinalizar(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_finalizar);
        dialog.setTitle("Deseja finalizar a compra?");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

        TextView text = (TextView) dialog.findViewById(R.id.txtMsgConfirmacao);
        text.setText("Valor Total: " + Utils.getValorFormatado(getValorTotalItens()));

        @SuppressLint("WrongViewCast")
        Button btnSim = (Button) dialog.findViewById(R.id.btnSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SucessoActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        Button btnNao = (Button) dialog.findViewById(R.id.btnNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }*/

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
                    buscarProduto(codigoBarras);
                }
            }
        }
    }


    public void buscarProduto(final String codigoBarras){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Buscando produto");
        dialog.show();

        RestClient restClient = new RestClient();
        String codigoEstabelecimento = estabelecimentoVO.getCodigoEstabelecimento();
        restClient.getRestAPI().obterItemCompraPorCodigoBarra(codigoBarras, codigoEstabelecimento, new Callback<ItemCompraVO>() {
            @Override
            public void success(ItemCompraVO itemCompraResponse, Response response) {
                dialog.dismiss();
                if (itemCompraResponse != null) {
                    itens.add(itemCompraResponse);
                    atualizaListaProdutos();
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

    /**
     * Método que atualiza lista com novo produto escaneado
     */
    private void atualizaListaProdutos() {
        valorTotal = getValorTotalItens();
        txtValorTotal.setText(Utils.getValorFormatado(valorTotal));

        // Getting adapter by passing xml data ArrayList
        itemCompraAdapter = new ItemCompraAdapter(this, itens);
        itemCompraAdapter.setCustomButtonListener(CarrinhoComprasActivity.this);
        itemListView.setAdapter(itemCompraAdapter);

        // Click event for single list row
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final Dialog dialog = new Dialog(CarrinhoComprasActivity.this);
                dialog.setContentView(R.layout.custom_dialog_editar);
                dialog.setTitle("Quantidade");
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                number.setMaxValue(100);
                number.setMinValue(1);

                ItemCompraVO itemSelecionado = itens.get(position);
                if(itemSelecionado != null){
                    number.setValue(itemSelecionado.getQuantidade());
                }

                Button btnPronto = (Button) dialog.findViewById(R.id.btnPronto);
                btnPronto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                        itens.get(position).setQuantidade(number.getValue());

                        itemCompraAdapter = new ItemCompraAdapter(CarrinhoComprasActivity.this, itens);
                        itemListView.setAdapter(itemCompraAdapter);

                        valorTotal = getValorTotalItens();
                        txtValorTotal.setText(Utils.getValorFormatado(valorTotal));
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
        });
    }

    public BigDecimal getValorTotalItens(){
        BigDecimal retorno = new BigDecimal(0);
        if (itens != null){
            for (ItemCompraVO item:itens) {
                retorno = retorno.add(item.getValor());
            }
        }
        return retorno;
    }

    @Override
    public void onButtonClickListener(int position) {
        itens.remove(position);
        atualizaListaProdutos();
    }
}