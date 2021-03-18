package com.example.scanf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import javax.annotation.Nonnull;


import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class MyAdapter extends RealmRecyclerViewAdapter<DataCenter, MyAdapter.ViewHolder> {
    private Context context;
    Realm realm;
    static long getId;
    Activity activity;
    NomList nomList= new NomList();
    public MyAdapter(@Nullable OrderedRealmCollection<DataCenter> data, Context context) {
        super(data, true);
        this.context=context;

        setHasStableIds(true);
    }

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(@Nonnull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list,viewGroup,false);
        Realm.init(context);
        realm=Realm.getInstance(DataUtility.getDefaultConfig());
        return new MyAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@Nonnull final ViewHolder viewHolder, int i) {
        final DataCenter dataCenter =getItem(i);
        assert dataCenter != null;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId=viewHolder.getItemId();
                openDialog1();
                //Toast.makeText(context, "Hello from magicbook bitch", Toast.LENGTH_SHORT).show();









            }
        });



        viewHolder.name.setText(dataCenter.getName());
        viewHolder.count.setText(dataCenter.getCount());
        viewHolder.id.setText(Integer.toString(i+1));

    }

      class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count,code ,id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            count=itemView.findViewById(R.id.tvCount);
            id=itemView.findViewById(R.id.tvId);



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
    public  void openDialog1 () {

        DidalogChange d = new DidalogChange(context);
        //  d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);
        d.show();
    }

}
