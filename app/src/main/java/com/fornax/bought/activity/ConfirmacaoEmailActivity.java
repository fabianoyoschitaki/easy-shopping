package com.fornax.bought.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fornax.bought.R;
import com.fornax.bought.common.UsuarioVO;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmacaoEmailActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmacaoEmail";
    @Bind(R.id.tvFaltaPouco)
    TextView tvFaltaPouco;

    @Bind(R.id.tvEmail)
    TextView tvEmail;

    @Bind(R.id.ibOpenMail)
    ImageButton ibOpenMail;

    @Bind(R.id.tvJaConfirmei)
    TextView tvJaConfirmei;

    private UsuarioVO usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_email);
        ButterKnife.bind(this);
        try {
            usuario = (UsuarioVO) getIntent().getSerializableExtra("usuario");
            tvEmail.setText(usuario.getEmail());
            tvFaltaPouco.setText(usuario.getNome() + ", falta pouco!");
        } catch (Exception e){
            Log.d(TAG, "Erro:" + e.getMessage());
            e.printStackTrace();
        }

        tvJaConfirmei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibOpenMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}