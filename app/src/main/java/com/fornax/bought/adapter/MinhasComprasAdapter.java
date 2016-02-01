package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fornax.bought.common.MinhaCompraVO;

import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhasComprasAdapter extends BaseAdapter {

    private Context context;
    private int position;
    private View convertView;
    private ViewGroup parent;
    private List<MinhaCompraVO> compras;

    /* private view holder class */
    private class ViewHolder {
        ImageView imgViewFoto;
        TextView txtCodigo;
        TextView txtValorCompra;
    }
    public MinhasComprasAdapter(Context context, List<MinhaCompraVO> compras) {
        this.context = context;
        this.compras = compras;
    }
    public List<MinhaCompraVO> getCompras() {
        return compras;
    }

    public void setCompras(List<MinhaCompraVO> compras) {
        this.compras = compras;
    }

    @Override
    public int getCount() {
        return compras.size();
    }

    @Override
    public Object getItem(int position) {
        return compras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return compras.indexOf(getItem(position));
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
            convertView = mInflater.inflate(R.layout.row_compra, null);
            holder = new ViewHolder();
            holder.imgViewFoto = (ImageView) convertView.findViewById(R.id.imgViewFoto);
            holder.txtCodigo = (TextView) convertView.findViewById(R.id.txtCodigo);
            holder.txtValorCompra = (TextView) convertView.findViewById(R.id.txtValorCompra);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MinhaCompraVO row_pos = compras.get(position);


        return convertView;
    }
}
