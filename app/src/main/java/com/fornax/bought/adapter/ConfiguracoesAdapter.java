package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fornax.bought.R;
import com.fornax.bought.enums.ItemConfiguracaoEnum;


/**
 * Created by Hallan on 04/12/2015.
 */
public class ConfiguracoesAdapter extends BaseAdapter {

    private Context context;
    private int position;
    private View convertView;
    private ViewGroup parent;

    /* private view holder class */
    private class ViewHolder {
        TextView txtDescricaoItem;
    }
    public ConfiguracoesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ItemConfiguracaoEnum.values().length;
    }

    @Override
    public Object getItem(int position) {
        return ItemConfiguracaoEnum.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return ItemConfiguracaoEnum.values()[position].getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_item_configuracao, null);
            holder = new ViewHolder();
            holder.txtDescricaoItem = (TextView) convertView.findViewById(R.id.txtDescricao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if((position+1) == ItemConfiguracaoEnum.AJUDA.getCodigo()){
            holder.txtDescricaoItem.setText(ItemConfiguracaoEnum.AJUDA.getDescricao());
        }else if((position+1) == ItemConfiguracaoEnum.PERFIL.getCodigo()){
            holder.txtDescricaoItem.setText(ItemConfiguracaoEnum.PERFIL.getDescricao());
        }else if((position+1) == ItemConfiguracaoEnum.ALERTAS.getCodigo()){
            holder.txtDescricaoItem.setText(ItemConfiguracaoEnum.ALERTAS.getDescricao());
        }

        return convertView;
    }
}
