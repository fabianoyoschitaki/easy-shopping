package com.fornax.bought.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fornax.bought.adapter.DrawerItemListAdapter;
import com.fornax.bought.common.DrawerItem;
import com.fornax.bought.fragment.ConfiguracoesFragment;
import com.fornax.bought.fragment.InicioFragment;
import com.fornax.bought.fragment.OndeComprarFragment;
import com.fornax.bought.fragment.MeusCartoesFragment;
import com.fornax.bought.fragment.MinhasComprasFragment;
import com.fornax.bought.fragment.PayPalFragment;
import com.fornax.bought.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import bought.fornax.com.bought.R;


public class TelaPrincipalActivity extends AppCompatActivity {
    private static final String TAG = TelaPrincipalActivity.class.getName();

    /** lista que contem os itens da gaveta **/
    private ListView drawerListView;

    /** layout principal que contém tudo **/
    private DrawerLayout drawerLayout;

    /** lista de objetos que carrega o título,icone e contador de cada item do menu **/
    private ArrayList<DrawerItem> drawerItens;

    /** títulos e ícones **/
    private String[] drawerItemTitles;
    private TypedArray drawerItemIcons;

    /** adapter do item gaveta **/
    private DrawerItemListAdapter drawerItemListAdapter;

    private ActionBarDrawerToggle mDrawerToggle;

    /** título quando acionar a gaveta **/
    private CharSequence drawerOpenedTitle;

    /** título quando fechar a gaveta **/
    private CharSequence drawerClosedTitle;

    private SharedPreferencesUtil sharedPreferencesUtil;

    /** posicao dos itens de fragmento da Tela Principal **/
    private static final int INICIO_FRAGMENT_POSITION = 0;
    private static final int ONDE_COMPRAR_FRAGMENT_POSITION = 1;
    private static final int MINHAS_COMPRAS_FRAGMENT_POSITION = 2;
    private static final int MEUS_CARTOES_FRAGMENT_POSITION = 3;
    private static final int CONFIGURACOES_FRAGMENT_POSITION = 4;
    private static final int PAYPAL_FRAGMENT_POSITION = 5;
    private static final int LOGOUT_FRAGMENT_POSITION = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferencesUtil = new SharedPreferencesUtil(this);

        //getActionBar().setTitle("Ola Teste"); // dá nullpointer a não ser que coloca minSDKVersion = 11
        getSupportActionBar().setTitle("Ibought");
        setContentView(R.layout.activity_tela_principal);

        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        drawerItemTitles = getResources().getStringArray(R.array.drawer_item_titles);
        drawerItemIcons = getResources().obtainTypedArray(R.array.drawer_item_icons);

        drawerOpenedTitle = drawerClosedTitle = "IBought";

        /** inicializando os itens da gaveta com os títulos e ícones **/
        drawerItens = new ArrayList<DrawerItem>();
        drawerItens.add(new DrawerItem(drawerItemTitles[INICIO_FRAGMENT_POSITION], drawerItemIcons.getResourceId(INICIO_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[ONDE_COMPRAR_FRAGMENT_POSITION], drawerItemIcons.getResourceId(ONDE_COMPRAR_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[MINHAS_COMPRAS_FRAGMENT_POSITION], drawerItemIcons.getResourceId(MINHAS_COMPRAS_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[MEUS_CARTOES_FRAGMENT_POSITION], drawerItemIcons.getResourceId(MEUS_CARTOES_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[CONFIGURACOES_FRAGMENT_POSITION], drawerItemIcons.getResourceId(CONFIGURACOES_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[PAYPAL_FRAGMENT_POSITION], drawerItemIcons.getResourceId(PAYPAL_FRAGMENT_POSITION, -1)));
        drawerItens.add(new DrawerItem(drawerItemTitles[LOGOUT_FRAGMENT_POSITION], drawerItemIcons.getResourceId(LOGOUT_FRAGMENT_POSITION, -1)));
        // Recycle the typed arrayg
        drawerItemIcons.recycle();

        drawerItemListAdapter = new DrawerItemListAdapter(getApplicationContext(), drawerItens);
        drawerListView = (ListView) findViewById(R.id.drawer_list_view);
        drawerListView.setOnItemClickListener(new SlideMenuClickListener());
        drawerListView.setAdapter(drawerItemListAdapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                //R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(drawerClosedTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerOpenedTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            /** na primeira vez, deixa carregado o 0 **/
            atualizaFragmento(0);
        }
    }

    /**
     * Slide menu item click listener para atualizar o item selecionado
     * */
    private class SlideMenuClickListener implements
            OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (position == LOGOUT_FRAGMENT_POSITION) {
                logout();
            } else {
                atualizaFragmento(position);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerListView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void atualizaFragmento(int position) {
        // update the main content by replacing fragments
        Fragment fragmento = new InicioFragment();
        switch (position) {
            case INICIO_FRAGMENT_POSITION:
                fragmento = new InicioFragment();
                break;
            case ONDE_COMPRAR_FRAGMENT_POSITION:
                fragmento = new OndeComprarFragment();
                break;
            case MINHAS_COMPRAS_FRAGMENT_POSITION:
                fragmento = new MinhasComprasFragment();
                break;
            case MEUS_CARTOES_FRAGMENT_POSITION:
                fragmento = new MeusCartoesFragment();
                break;
            case CONFIGURACOES_FRAGMENT_POSITION:
                fragmento = new ConfiguracoesFragment();
                break;
            case PAYPAL_FRAGMENT_POSITION:
                fragmento = new PayPalFragment();
                break;
            default:
                break;
        }
        if (fragmento != null) {
            // During initial setup, plug in the details fragment.
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_atual, fragmento).commit();

            // atualiza o item selecionado e o título, então fecha a gaveta
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            setTitle(drawerItemTitles[position]);
            drawerLayout.closeDrawer(drawerListView);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
        public void setTitle(CharSequence title) {
        drawerClosedTitle = title;
        getSupportActionBar().setTitle(drawerClosedTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void logout() {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
        builder1.setTitle("Logout")
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
