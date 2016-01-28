package com.fornax.bought.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fornax.bought.fragment.ConfiguracoesFragment;
import com.fornax.bought.fragment.InicioFragment;
import com.fornax.bought.fragment.MinhasComprasFragment;
import com.fornax.bought.fragment.OndeComprarFragment;
import com.fornax.bought.fragment.PayPalFragment;
import com.fornax.bought.utils.SharedPreferencesUtil;

import bought.fornax.com.bought.R;


public class TelaPrincipalActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private static final String TAG = TelaPrincipalActivity.class.getName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    private SharedPreferencesUtil sharedPreferencesUtil;

    /** posicao dos itens de fragmento da Tela Principal **/
    private static final int INICIO_FRAGMENT_POSITION = 0;
    private static final int ONDE_COMPRAR_FRAGMENT_POSITION = 1;
    private static final int MINHAS_COMPRAS_FRAGMENT_POSITION = 2;
    private static final int CONFIGURACOES_FRAGMENT_POSITION = 3;
    private static final int PAYPAL_FRAGMENT_POSITION = 4;
    private static final int LOGOUT_FRAGMENT_POSITION = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        sharedPreferencesUtil = new SharedPreferencesUtil(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(INICIO_FRAGMENT_POSITION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case INICIO_FRAGMENT_POSITION:
                fragment = new InicioFragment();
                break;
            case ONDE_COMPRAR_FRAGMENT_POSITION:
                fragment = new OndeComprarFragment();
                break;
            case MINHAS_COMPRAS_FRAGMENT_POSITION:
                fragment = new MinhasComprasFragment();
                break;
            case CONFIGURACOES_FRAGMENT_POSITION:
                fragment = new ConfiguracoesFragment();
                break;
            case PAYPAL_FRAGMENT_POSITION:
                fragment = new PayPalFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void logout() {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
        builder1
                .setMessage("Deseja realizar o logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferencesUtil.remove("Login");
                        sharedPreferencesUtil.remove("Password");

                        Intent intent = new Intent(TelaPrincipalActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //drawerLayout.closeDrawer(drawerListView);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
