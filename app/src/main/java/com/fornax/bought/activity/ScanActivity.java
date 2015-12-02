package com.fornax.bought.activity;

import android.app.Activity;
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

import com.fornax.bought.adapter.ProdutoAdapter;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.utils.Utils;

import java.util.ArrayList;

import bought.fornax.com.bought.R;

public class ScanActivity extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    ListView list;
    ProdutoAdapter adapter;

    private Button _btn_scan;
    private Button _btn_finalizar;

    private Double valorTotal;

    public void abrirTelaScan(){
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ScanActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void abrirTelaConfirmarFinalizar(){
        Intent intent = new Intent(getApplicationContext(), ConfirmacaoActivity.class);
        intent.putExtra("valorTotal", valorTotal);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the main content layout of the Activity
        setContentView(R.layout.activity_scan);

        _btn_scan = (Button) findViewById(R.id.btn_scan);
        _btn_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                abrirTelaScan();
            }
        });


        _btn_finalizar = (Button) findViewById(R.id.btn_finalizar);
        _btn_finalizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                abrirTelaConfirmarFinalizar();
            }
        });


        abrirTelaScan();
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
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                ProdutoVO produto = new ProdutoVO(contents, 15.05);

                if(LoginActivity.codigosEscaneados == null){
                    LoginActivity.codigosEscaneados = new ArrayList<ProdutoVO>();
                }
                LoginActivity.codigosEscaneados.add(produto);

                valorTotal = 0.0;
                for (ProdutoVO prod: LoginActivity.codigosEscaneados) {
                    valorTotal += prod.getPreco();
                }

                TextView txtValorTotal = (TextView) findViewById(R.id.txtValorTotal);
                txtValorTotal.setText("R$ " + Utils.getValorFormatado(valorTotal));
                list = (ListView) findViewById(R.id.listCodigos);

                // Getting adapter by passing xml data ArrayList
                adapter = new ProdutoAdapter(this, LoginActivity.codigosEscaneados);
                list.setAdapter(adapter);

                // Click event for single list row
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                    }
                });
            }
        }
    }
}
