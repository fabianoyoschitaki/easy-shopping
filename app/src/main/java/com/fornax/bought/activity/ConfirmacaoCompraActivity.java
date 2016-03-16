package com.fornax.bought.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.fornax.bought.R;
import com.fornax.bought.adapter.CarrinhoComprasPagerAdapter;
import com.fornax.bought.adapter.ConfirmacaoComprasPagerAdapter;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmacaoCompraActivity extends AppCompatActivity {


    @Bind(R.id.view_pager) ViewPager viewPager;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_compra);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager.setAdapter(new ConfirmacaoComprasPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_concluir_compra){
            Intent carrinhoCompras = new Intent(this, ConfirmacaoCompraActivity.class);
            startActivity(carrinhoCompras);
            overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_carrinho_compras, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}