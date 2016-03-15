package com.fornax.bought.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.fragment.CarrinhoComprasFragment;
import com.fornax.bought.utils.ImageLoader;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hallan on 29/11/2015.
 */
public class ItemCompraAdapter extends RecyclerView.Adapter<ItemCompraAdapter.ItemCompraViewHolder> {

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
    public ItemCompraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCompraViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_produto, parent, false));
    }

    public ItemCompraAdapter(List<ItemCompraVO> itens, Fragment fragment) {
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
    public void onBindViewHolder(ItemCompraViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Quantidade");

                np = new NumberPicker(v.getContext());
                np.setMinValue(1);
                np.setMaxValue(100);
                np.setWrapSelectorWheel(false);
                np.setValue(SessionUtils.getCompra().getItensCompraVO().get(position).getQuantidade());
                alert.setView(np);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SessionUtils.getCompra().getItensCompraVO().get(position).setQuantidade(np.getValue());
                        ((CarrinhoComprasFragment)fragment).atualizaListaProdutos();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel.
                    }
                });

                alert.show();
            }
        });

        ItemCompraVO row_pos = itens.get(position);

        if(row_pos != null && row_pos.getProdutoVO() != null){
            Picasso.with(context)
                    .load(row_pos.getProdutoVO().getUrlImagem())
                    .placeholder(android.R.drawable.star_big_on) //
                    .error(android.R.drawable.star_big_on)
                    .into(holder.imgViewFoto);
            holder.txtNome.setText(row_pos.getProdutoVO().getNome());
            holder.txtUnidade.setText(row_pos.getQuantidade() + " un.");
            holder.txtPreco.setText(Utils.getValorFormatado(row_pos.getValor()));
        }

        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListener != null) {
                    customListener.onButtonClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class ItemCompraViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewFoto;
        TextView txtNome;
        TextView txtUnidade;
        TextView txtPreco;
        ImageButton imgButton;

        public ItemCompraViewHolder(View itemView) {
            super(itemView);

            this.txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            this.imgViewFoto = (ImageView) itemView.findViewById(R.id.imgViewFoto);
            this.txtUnidade = (TextView) itemView.findViewById(R.id.txtUnidade);
            this.txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);
            this.imgButton = (ImageButton) itemView.findViewById(R.id.imgBtnExcluir);
        }
    }
}
