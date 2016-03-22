package com.fornax.bought.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.adapter.CarrinhoComprasPagerAdapter;
import com.fornax.bought.adapter.ItensCarrinhoPagerAdapter;
import com.fornax.bought.utils.SessionUtils;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItensCarrinhoActivity extends AppCompatActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_carrinho);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(new ItensCarrinhoPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrinho_compras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_concluir_compra){
            if(SessionUtils.getCompra() != null && SessionUtils.getCompra().getValorTotal() != null &&
                    SessionUtils.getCompra().getValorTotal().compareTo(BigDecimal.ZERO) > 0){
                Intent carrinhoCompras = new Intent(this, ConfirmacaoCompraActivity.class);
                startActivity(carrinhoCompras);
                overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
            }else{
                Toast.makeText(this, "Não é possível concluir uma compra com o carrinho vazio.", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
