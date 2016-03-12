package com.fornax.bought.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

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
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.custom_dialog_editar);
                dialog.setTitle("Quantidade");
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                number.setMaxValue(100);
                number.setMinValue(1);

                ItemCompraVO itemSelecionado = SessionUtils.getCompra().getItensCompraVO().get(position);
                if (itemSelecionado != null) {
                    number.setValue(itemSelecionado.getQuantidade());
                }

                Button btnPronto = (Button) dialog.findViewById(R.id.btnPronto);
                btnPronto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NumberPicker number = (NumberPicker) dialog.findViewById(R.id.numberPicker);
                        SessionUtils.getCompra().getItensCompraVO().get(position).setQuantidade(number.getValue());

                        ((CarrinhoComprasFragment)fragment).atualizaListaProdutos();
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
