package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fornax.bought.R;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.MinhaCompraVO;
import com.fornax.bought.enums.StatusCompraENUM;
import com.fornax.bought.fragment.CarrinhoComprasFragment;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Hallan on 04/12/2015.
 */
public class MinhasComprasAdapter extends RecyclerView.Adapter<MinhasComprasAdapter.MinhasComprasViewHolder> {

    private Fragment fragment;
    private List<CompraVO> compras;

    @Override
    public MinhasComprasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MinhasComprasViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_compra, parent, false));
    }

    public MinhasComprasAdapter(List<CompraVO> compras, Fragment fragment) {
        this.compras = compras;
        this.fragment = fragment;
    }

    public Object getItem(int position) {
        return this.compras.get(position);
    }

    public long getItemId(int position) {
        return this.compras.indexOf(getItem(position));
    }

    @Override
    public void onBindViewHolder(MinhasComprasViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();

        CompraVO row_pos = compras.get(position);
        if(row_pos != null){
            Picasso.with(context)
                    .load(row_pos.getEstabelecimentoVO().getUrlLogo())
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.progress_animation)
                    .into(holder.imgViewFoto);
            holder.txtDataHora.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(row_pos.getData()));
            holder.txtValorCompra.setText(Utils.getValorFormatado(row_pos.getValorTotal()));

            if(row_pos.getStatusCompraENUM().equals(StatusCompraENUM.PAGA) ||
                    row_pos.getStatusCompraENUM().equals(StatusCompraENUM.ENCERRADA)  ){
                holder.imgViewStatus.setImageResource(R.drawable.dollar_green);
            }else {
                holder.imgViewStatus.setImageResource(R.drawable.dollar_yellow);
            }
        }
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    class MinhasComprasViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewFoto;
        TextView txtDataHora;
        TextView txtValorCompra;
        ImageView imgViewStatus;

        public MinhasComprasViewHolder(View itemView) {
            super(itemView);

            this.imgViewFoto = (ImageView) itemView.findViewById(R.id.imgViewFoto);
            this.txtDataHora = (TextView) itemView.findViewById(R.id.txtDataHora);
            this.txtValorCompra = (TextView) itemView.findViewById(R.id.txtValorCompra);
            this.imgViewStatus = (ImageView) itemView.findViewById(R.id.imgViewStatus);
        }
    }

    public List<CompraVO> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraVO> compras) {
        this.compras = compras;
    }
}
