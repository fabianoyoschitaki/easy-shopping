package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.utils.ImageLoader;
import com.fornax.bought.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Hallan on 29/11/2015.
 */
public class ProdutoAdapter extends BaseAdapter{

    private Activity activity;
    private List<ProdutoVO> produtos;

    public List<ProdutoVO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVO> produtos) {
        this.produtos = produtos;
    }

    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public ProdutoAdapter(Activity a, List<ProdutoVO> produtos) {
        activity = a;

        if(this.produtos == null){
            this.produtos = new ArrayList<ProdutoVO>();
        }
        this.produtos = produtos;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        if(this.produtos == null){
            this.produtos = new ArrayList<ProdutoVO>();
        }
        return this.produtos.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_produto, null);

        TextView txtCodigoBarra = (TextView)vi.findViewById(R.id.txtCodigoBarra);
        TextView txtPreco = (TextView)vi.findViewById(R.id.txtPreco);

        ProdutoVO produto = produtos.get(position);
        if(produto != null){
            txtCodigoBarra.setText(produto.getCodigobarras());
            txtPreco.setText(Utils.getValorFormatado(produto.getPreco()));
        }

        return vi;
    }
}
