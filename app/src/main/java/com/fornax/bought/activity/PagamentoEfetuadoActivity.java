package com.fornax.bought.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.mock.ComprasMock;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.math.BigDecimal;
import java.util.UUID;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PagamentoEfetuadoActivity extends AppCompatActivity {

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    @Bind(R.id.btnEncerrarCompra)Button btnEncerrarCompra;
    @Bind(R.id.imgViewQRCode) ImageView imageViewQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_efetuado);
        ButterKnife.bind(this);

        //TODO tirar mock
        //CompraVO compra = ComprasMock.getCompraVO((BigDecimal) getIntent().getExtras().getSerializable("valorTotal"));
        CompraVO compra = null;
        String qrCodePagamentoEfetuado = getQRCodePagamentoEfetuado(compra);

        if (qrCodePagamentoEfetuado != null){
            setQRCode(qrCodePagamentoEfetuado, imageViewQRCode);
            /** Intent encode = new Intent("la.droid.qr.encode");
            encode.putExtra("la.droid.qr.code", qrCodePagamentoEfetuado);
            encode.putExtra("la.droid.qr.image", true);
            encode.putExtra("la.droid.qr.size", 0);

            try {
                startActivityForResult(encode, ACTIVITY_RESULT_QR_DRDROID);
            } catch (ActivityNotFoundException activity) {
                qrDroidRequired(PagamentoEfetuadoActivity.this);
            }**/
        }

        btnEncerrarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ACIONAR WS REST QUE AINDA NÃO ESTÁ FEITO PELO FABIANO PUTA CARA DEVAGAR PARA FAZER AS COISAS
                //MAS ENTAO, ACIONAR WS REST DE ENCERRAÇÃO DO ENCERRAMENTO DA COMPRA E EM SEGUIDA VOLTAR PARA TELA INICIO

                Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Gera QR Code e taca no ImageView
     *
     * @param qrCode
     * @param imageViewQRCode
     */
    private void setQRCode(String qrCode, ImageView imageViewQRCode) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrCode, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageViewQRCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private String getQRCodePagamentoEfetuado(CompraVO compra){
        String retorno = null;
        if(compra != null){
            //TODO ACIONAR WS REST PARA OBTER O CODIGO DA COMPRA GERADA NA BASE..
            retorno = UUID.randomUUID().toString();
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(ACTIVITY_RESULT_QR_DRDROID == requestCode
                && data != null && data.getExtras() != null ) {
            ImageView imgResult = (ImageView) findViewById(R.id.imgViewQRCode);
            String qrCode = data.getExtras().getString("la.droid.qr.result");

            if(qrCode == null || qrCode.trim().length() == 0) {
                Toast.makeText(getBaseContext(), "QR Code Image " +
                        "is not Saved", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(getBaseContext(), "QR Code Image is Saved"
                    + " " + qrCode, Toast.LENGTH_LONG).show();

            imgResult.setImageURI( Uri.parse(qrCode) );
            imgResult.setVisibility( View.VISIBLE );
        }
    }

    protected static void qrDroidRequired(final PagamentoEfetuadoActivity activity) {
        AlertDialog.Builder AlertBox = new AlertDialog.Builder(activity);

        AlertBox.setMessage("QRDroid Missing");

        AlertBox.setPositiveButton("Direct Download", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://droid.la/apk/qr/")));
            }
        });

        AlertBox.setNeutralButton("From Market", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://market.android.com/details?id=la.droid.qr")));
            }
        });

        AlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertBox.create().show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
