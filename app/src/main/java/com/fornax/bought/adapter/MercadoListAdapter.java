package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fornax.bought.common.MercadoVO;
import com.squareup.picasso.Picasso;

import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MercadoListAdapter extends BaseAdapter {
    private static final String TAG = MercadoListAdapter.class.getName();

    Context context;
    List<MercadoVO> rowItems;
    private int position;
    private View convertView;
    private ViewGroup parent;

    public MercadoListAdapter(Context context, List<MercadoVO> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView imgViewMercado;
        TextView tvNome;
        TextView tvDescricao;
        TextView tvLocal;
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
            convertView = mInflater.inflate(R.layout.row_mercado, null);
            holder = new ViewHolder();
            holder.imgViewMercado = (ImageView) convertView.findViewById(R.id.imgViewMercado);
            holder.tvNome = (TextView) convertView.findViewById(R.id.tvNome);
            holder.tvDescricao = (TextView) convertView.findViewById(R.id.tvDescricao);
            holder.tvLocal = (TextView) convertView.findViewById(R.id.tvLocal);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MercadoVO row_pos = rowItems.get(position);

        Picasso.with(parent.getContext())
                .load(row_pos.getUrlFoto())
                .placeholder(R.drawable.ic_drawer) //
                .error(R.drawable.ic_drawer)
                .into(holder.imgViewMercado);
        holder.tvNome.setText(row_pos.getNome());
        holder.tvDescricao.setText(row_pos.getDescricao());
        holder.tvLocal.setText(
                row_pos.getTipoLogradouro() + " " + row_pos.getNomeLogradouro() + ", " + row_pos.getNumeroLogradouro() +
                " " + row_pos.getNomeCidade() + ", " + row_pos.getSiglaEstado());

        return convertView;
    }
}
