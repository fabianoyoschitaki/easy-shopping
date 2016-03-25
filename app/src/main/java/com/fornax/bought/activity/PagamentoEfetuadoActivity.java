package com.fornax.bought.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.enums.StatusCompraENUM;
import com.fornax.bought.rest.RestClient;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.SessionUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PagamentoEfetuadoActivity extends AppCompatActivity {

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    @Bind(R.id.imgViewQRCode)
    ImageView imageViewQRCode;

    @Bind(R.id.bntVoltarInicio)Button bntVoltarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_efetuado);
        ButterKnife.bind(this);
        setQRCode(SessionUtils.getCompra().getId().toString(), imageViewQRCode);


        bntVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
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
            retorno = compra.getId().toString();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
