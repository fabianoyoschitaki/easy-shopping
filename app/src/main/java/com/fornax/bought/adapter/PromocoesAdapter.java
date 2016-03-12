package com.fornax.bought.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fornax.bought.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Fabia on 12/03/2016.
 */
public class PromocoesAdapter extends RecyclerView.Adapter<PromocoesAdapter.PromocaoViewHolder> {

    private static final List<String> ITEMS = Arrays.asList("Pretty Pasta", "Hmm... Dessert",
            "Pizza!", "Crispy Steak", "Barbecue Sauce");

    @Override
    public PromocaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PromocaoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promocao_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(PromocaoViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if (context instanceof View.OnClickListener) {
            holder.itemView.setOnClickListener((View.OnClickListener) context);
        }

        String title = ITEMS.get(position % 5);
        int image = context.getResources().getIdentifier(String.format("img_0%d", (position % 5) + 1),
                "drawable", context.getPackageName());

        holder.itemView.setTag(R.id.title, title);
        holder.itemView.setTag(R.id.image, image);

        holder.title.setText(title);
        holder.image.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return ITEMS.size() * 10;
    }

    class PromocaoViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public PromocaoViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}