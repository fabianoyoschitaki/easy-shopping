package com.fornax.bought.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.fornax.bought.R;
import com.fornax.bought.fragment.ConfiguracoesFragment;
import com.fornax.bought.fragment.InicioFragment;
import com.fornax.bought.fragment.MinhasComprasFragment;
import com.fornax.bought.fragment.OndeComprarFragment;
import com.fornax.bought.fragment.PayPalFragment;
import com.fornax.bought.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TelaPrincipalActivity extends AppCompatActivity {
    private static final String TAG = TelaPrincipalActivity.class.getName();

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view) NavigationView navigationView;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPreferencesUtil = new SharedPreferencesUtil(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                displayView(menuItem.getItemId());
                return true;
            }
        });
        displayView(R.id.navigation_item_inicio);
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

        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     ***/
    public void displayView(int itemId) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (itemId) {
            case R.id.navigation_item_inicio:
                fragment = new InicioFragment();
                break;
            case R.id.navigation_item_onde_comprar:
                fragment = new OndeComprarFragment();
                break;
            case R.id.navigation_item_minhas_compras:
                fragment = new MinhasComprasFragment();
                break;
            case R.id.navigation_item_paypal:
                fragment = new PayPalFragment();
                break;
            case R.id.navigation_item_configuracoes:
                fragment = new ConfiguracoesFragment();
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
        }else{
            logout();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void logout() {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
        builder1
                .setMessage("Deseja realizar o logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //SE TIVER PERFIL LOGADO..
                        Profile profile = Profile.getCurrentProfile();
                        if(profile != null){
                            //DESLOGA DO FACE
                            LoginManager.getInstance().logOut();
                        }else{
                            sharedPreferencesUtil.remove("Login");
                            sharedPreferencesUtil.remove("Password");
                        }
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
