package com.example.scanf;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import dataBase.DataWhere;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

@SuppressLint("ValidFragment")
public class  DialogWhere extends DialogFragment {
    @SuppressLint("ValidFragment")

    public  DialogWhere(WhereCallback whereCallback){
        this.whereCallback=whereCallback;
    }
    RecyclerView recyclerView;
    WhereCallback whereCallback;
SharedPreferences s;
    public static final String APP_PREFERENCE="settings";
    public static final String SEND_WHERE="send_where";
    Dialog dialogChooseUser;
    Realm realm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_where,container,false);
        Realm.init(getActivity());
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        recyclerView = view.findViewById(R.id.recWhere);
        AdapterChooseWhere adapterChooseWhere = new AdapterChooseWhere(realm.where(DataWhere.class).findAll(),getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterChooseWhere);
        adapterChooseWhere.setOnRecyclerClickWhere(new OnRecyclerClickWhere() {
            @Override
            public void onClickRecyclerWhere(int position) {



            }

            @Override
            public void onClickRecyclerWhere(int position, String string) {
                dialogChooseUser=getDialog();
                s= Objects.requireNonNull(getActivity()).getSharedPreferences(APP_PREFERENCE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putString(SEND_WHERE,string);
                editor.apply();
                whereCallback.whereCallback(string);
                dismiss();
            }

        });


        return view;
    }





}

