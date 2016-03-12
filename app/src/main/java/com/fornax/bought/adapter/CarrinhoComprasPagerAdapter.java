package com.fornax.bought.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.fornax.bought.fragment.CarrinhoComprasFragment;
import com.fornax.bought.fragment.PromocaoFragment;

/**
 * Created by Fabia on 12/03/2016.
 */
public class CarrinhoComprasPagerAdapter extends FragmentPagerAdapter {
    public CarrinhoComprasPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new CarrinhoComprasFragment();
        return new PromocaoFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Carrinho";
        return "Promoções";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
