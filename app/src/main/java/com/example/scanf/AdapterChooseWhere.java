package com.example.scanf;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import dataBase.DataWhere;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;

public class AdapterChooseWhere extends RealmRecyclerViewAdapter<DataWhere, AdapterChooseWhere.ViewHolder> {
    Context context;
    Realm realm;
DialogWhere dialogWhere;


public static final String SEND_WHERE="where_send";
private  OnRecyclerClickWhere onRecyclerClickWhere;

    public AdapterChooseWhere(@Nullable OrderedRealmCollection data, Context context) {
        super(data, true);
        this.context = context;
        setHasStableIds(true);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_where, viewGroup, false);
        Realm.init(context);
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        return new ViewHolder(view,onRecyclerClickWhere);

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DataWhere dataWhere = getItem(i);
        if (dataWhere != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onRecyclerClickWhere.onClickRecyclerWhere(viewHolder.getAdapterPosition(), dataWhere.getWhere());







                }
            });
            viewHolder.textView.setText(dataWhere.getWhere());

        }

    }


    public void setOnRecyclerClickWhere(OnRecyclerClickWhere onRecyclerClickWhere) {
        this.onRecyclerClickWhere=onRecyclerClickWhere;

    }

   static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;


        public   ViewHolder(@NonNull View itemView, final OnRecyclerClickWhere onRecyclerClickWhere) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvCardWhere);

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
