package com.example.scanf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import dataBase.DataWho;
import io.realm.Realm;

@SuppressLint("ValidFragment")
public class DialogWho extends DialogFragment {
    @SuppressLint("ValidFragment")
    public DialogWho(WhereCallback whereCallback){
        this.whereCallback=whereCallback;
    }
    RecyclerView recyclerView;
    WhereCallback whereCallback;
    Realm realm;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCE="settings";
    public static final String SEND_WHO="send_who";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_who,container,false);
        Realm.init(getActivity());
        realm=Realm.getInstance(DataUtility.getDefaultConfig());
        recyclerView=view.findViewById(R.id.recWho);
        AdapterChooseWho adapterChooseWho = new AdapterChooseWho(realm.where(DataWho.class).findAll(),getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterChooseWho.setOnRecyclerClickWhere(new OnRecyclerClickWhere() {
            @Override
            public void onClickRecyclerWhere(int position) {

            }

            @Override
            public void onClickRecyclerWhere(int position, String string) {
                sharedPreferences=getActivity().getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SEND_WHO,string);
                editor.apply();
                whereCallback.whereCallback(string);
                dismiss();


            }
        });
        recyclerView.setAdapter(adapterChooseWho);
        return view;
    }
}
