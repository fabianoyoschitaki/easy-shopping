package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fornax.bought.common.DrawerItem;

import java.util.ArrayList;

import bought.fornax.com.bought.R;


/**
 * * Classe responsável por tratar os itens da gaveta
 * controla como o item é exibido
 *
 * Created by fabiano on 12/11/15.
 */
public class DrawerItemListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DrawerItem> drawerItens;

    public DrawerItemListAdapter(
            Context context,
            ArrayList<DrawerItem> drawerItens) {
        this.context = context;
        this.drawerItens = drawerItens;
    }

    @Override
    public int getCount() {
        return drawerItens.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerItens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_drawer_item, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.icone);
        TextView title = (TextView) convertView.findViewById(R.id.titulo);
        TextView counter = (TextView) convertView.findViewById(R.id.counter);

        /** atualiza os valores p/ as views **/
        icon.setImageResource(drawerItens.get(position).getIcon());
        title.setText(drawerItens.get(position).getTitle());

        /** mostra o contador **/
        // check whether it set visible or not
        if (drawerItens.get(position).isCounterVisible()) {
            counter.setText(drawerItens.get(position).getCount());
        } else {
            // hide the counter view
            counter.setVisibility(View.GONE);
        }

        return convertView;
    }

}