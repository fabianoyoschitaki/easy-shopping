package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.utils.ImageLoader;
import com.fornax.bought.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Hallan on 29/11/2015.
 */
public class ProdutoAdapter extends BaseAdapter{

    private Context context;
    private List<ProdutoVO> produtos;
    private int position;
    private View convertView;
    private ViewGroup parent;

    public List<ProdutoVO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVO> produtos) {
        this.produtos = produtos;
    }


    public ProdutoAdapter(Context context, List<ProdutoVO> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    public int getCount() {
        return this.produtos.size();
    }

    public Object getItem(int position) {
        return this.produtos.get(position);
    }

    public long getItemId(int position) {
        return this.produtos.indexOf(getItem(position));
    }


    /* private view holder class */
    private class ViewHolder {
        ImageView imgViewFoto;
        //TextView txtCodigoBarra;
        TextView txtNome;
        TextView txtPreco;
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
            convertView = mInflater.inflate(R.layout.row_produto, null);
            holder = new ViewHolder();
            holder.txtNome = (TextView) convertView.findViewById(R.id.txtNome);
            holder.imgViewFoto = (ImageView) convertView.findViewById(R.id.imgViewFoto);
            holder.txtPreco = (TextView) convertView.findViewById(R.id.txtPreco);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProdutoVO row_pos = produtos.get(position);

        Picasso.with(parent.getContext())
                .load(row_pos.getUrlImagem())
                .placeholder(android.R.drawable.star_big_on) //
                .error(android.R.drawable.star_big_on)
                .into(holder.imgViewFoto);
        holder.txtNome.setText(row_pos.getNome());
        holder.txtPreco.setText(Utils.getValorFormatado(row_pos.getPreco()));
        return convertView;
    }

}
