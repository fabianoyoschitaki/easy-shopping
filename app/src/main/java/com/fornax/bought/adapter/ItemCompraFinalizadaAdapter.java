package com.fornax.bought.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fornax.bought.R;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.fragment.CarrinhoComprasFragment;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Hallan on 29/11/2015.
 */
public class ItemCompraFinalizadaAdapter extends RecyclerView.Adapter<ItemCompraFinalizadaAdapter.ItemCompraFinalizadaViewHolder> {

    private List<ItemCompraVO> itens;
    private CustomButtonListener customListener;
    private Fragment fragment;
    private NumberPicker np;

    public interface CustomButtonListener{
        void onButtonClickListener(int position);
    }

    public void setCustomButtonListener(CustomButtonListener customListener) {
        this.customListener = customListener;
    }

    @Override
    public ItemCompraFinalizadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCompraFinalizadaViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_produto_finalizada, parent, false));
    }

    public ItemCompraFinalizadaAdapter(List<ItemCompraVO> itens, Fragment fragment) {
        this.itens = itens;
        this.fragment = fragment;
    }

    public Object getItem(int position) {
        return this.itens.get(position);
    }

    public long getItemId(int position) {
        return this.itens.indexOf(getItem(position));
    }

    @Override
    public void onBindViewHolder(ItemCompraFinalizadaViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        ItemCompraVO row_pos = itens.get(position);

        if(row_pos != null && row_pos.getProdutoVO() != null){
            Picasso.with(context)
                    .load(row_pos.getProdutoVO().getUrlImagem())
                    .placeholder(R.drawable.progress_animation) //
                    .error(R.drawable.progress_animation)
                    .into(holder.imgViewFoto);
            holder.txtNome.setText(row_pos.getProdutoVO().getNome());
            holder.txtUnidade.setText(row_pos.getQuantidade() + " un.");
            holder.txtPreco.setText(Utils.getValorFormatado(row_pos.getValor()));
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class ItemCompraFinalizadaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewFoto;
        TextView txtNome;
        TextView txtUnidade;
        TextView txtPreco;

        public ItemCompraFinalizadaViewHolder(View itemView) {
            super(itemView);
            this.txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            this.imgViewFoto = (ImageView) itemView.findViewById(R.id.imgViewFoto);
            this.txtUnidade = (TextView) itemView.findViewById(R.id.txtUnidade);
            this.txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);
        }
    }
}
