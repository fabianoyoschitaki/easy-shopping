package com.fornax.bought.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fornax.bought.common.MercadoVO;
import com.squareup.picasso.Picasso;

import java.util.List;

import bought.fornax.com.bought.R;

/**
 * Created by Hallan on 04/12/2015.
 */
public class MercadoListAdapter extends RecyclerView.Adapter<MercadoListAdapter.MercadoViewHolder> {
    private static final String TAG = MercadoListAdapter.class.getName();

    private final List<MercadoVO> mercados;
    private final Context context;
    private final MercadoOnClickListener onClickListener;

    public interface MercadoOnClickListener {
        public void onClickMercado(View view, int idx);
    }

    public MercadoListAdapter(Context context, List<MercadoVO> mercados, MercadoOnClickListener onClickListener) {
        this.context = context;
        this.mercados = mercados;
        this.onClickListener = onClickListener;
    }

    @Override
    public MercadoListAdapter.MercadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_mercado, parent, false);
        MercadoViewHolder holder = new MercadoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MercadoListAdapter.MercadoViewHolder holder, final int position) {
        MercadoVO mercado = mercados.get(position);
        Picasso.with(context)
                .load(mercado.getUrlFoto())
                .placeholder(R.drawable.ic_drawer) //
                .error(R.drawable.ic_drawer)
                .into(holder.imgViewMercado);
        holder.tvNome.setText(mercado.getNome());
        holder.tvDescricao.setText(mercado.getDescricao());
        holder.tvLocal.setText(
                mercado.getTipoLogradouro() + " " + mercado.getNomeLogradouro() + ", " + mercado.getNumeroLogradouro() +
                        " " + mercado.getNomeCidade() + ", " + mercado.getSiglaEstado());
        if (onClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickMercado(holder.view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.mercados != null ? this.mercados.size() : 0;
    }

    public static class MercadoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewMercado;
        TextView tvNome;
        TextView tvDescricao;
        TextView tvLocal;
        private View view;

        public MercadoViewHolder(View view){
            super(view);
            this.view = view;

            imgViewMercado = (ImageView) view.findViewById(R.id.imgViewMercado);
            tvNome = (TextView) view.findViewById(R.id.tvNome);
            tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
            tvLocal = (TextView) view.findViewById(R.id.tvLocal);
        }
    }

    /** @Override
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
    } **/
}
