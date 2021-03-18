package com.example.scanf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import dataBase.DataWho;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class AdapterChooseWho extends RealmRecyclerViewAdapter<DataWho,AdapterChooseWho.ViewHolder> {
Context context;
OnRecyclerClickWhere onRecyclerClickWhere;
    public static final String SEND_WHO="who_send";
    public AdapterChooseWho(@Nullable OrderedRealmCollection<DataWho> data, Context context) {
        super(data, true);
        this.context=context;
         setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_where,viewGroup,false);
        return new AdapterChooseWho.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DataWho dataWho = getItem(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert dataWho != null;
                onRecyclerClickWhere.onClickRecyclerWhere(viewHolder.getAdapterPosition(),dataWho.getWho());

            }
        });
        assert dataWho != null;
        viewHolder.textView.setText(dataWho.getWho());

    }
    public void setOnRecyclerClickWhere(OnRecyclerClickWhere onRecyclerClickWhere) {
        this.onRecyclerClickWhere=onRecyclerClickWhere;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tvCardWhere);

        }
    }

    @Override
    public long getItemId(int index) {
        return Objects.requireNonNull(getItem(index)).getId();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}
