package com.example.scanf;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

class ViewHolderAdapter extends RecyclerView.ViewHolder {
    TextView textNom;
    TextView textCount;

    public ViewHolderAdapter(@NonNull View itemView) {
        super(itemView);
        textCount = itemView.findViewById(R.id.textCount);
        textNom = itemView.findViewById(R.id.textNom);


    }
}

public class AdapterNew extends RecyclerView.Adapter<ViewHolderAdapter> {
    List<DataCenter> list = new ArrayList<DataCenter>();
    Realm realm;

    public AdapterNew(List<DataCenter> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view, viewGroup, false);
        return new ViewHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter viewHolderAdapter, int i) {

        RealmResults<DataCenter> results = realm.where(DataCenter.class).findAll();

        viewHolderAdapter.textNom.setText(list.get(i).getName());
        viewHolderAdapter.textCount.setText(list.get(i).getCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
