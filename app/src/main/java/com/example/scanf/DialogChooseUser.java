package com.example.scanf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import dataBase.DataWhere;

public class DialogChooseUser extends DialogFragment implements View.OnClickListener, ValidationData {
    private Context context;
    Button bWho, bWhere;
    Activity a;
    TextView tvWho, tvWhere, bOk;
    SharedPreferences sharedPreferences;
    String where;
    String who;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_user, container, false);
        bWho = view.findViewById(R.id.choose_Who);
        sharedPreferences = getActivity().getSharedPreferences(DialogWhere.APP_PREFERENCE, Context.MODE_PRIVATE);
        bWhere = view.findViewById(R.id.choose_Where);
        bOk = view.findViewById(R.id.ok);
        bOk.setOnClickListener(this);
        bWhere.setOnClickListener(this);
        bWho.setOnClickListener(this);
        tvWho = view.findViewById(R.id.tvWho);
        tvWhere = view.findViewById(R.id.tvWhere);
        if (sharedPreferences.contains(DialogWhere.SEND_WHERE)) {
            tvWhere.setText(sharedPreferences.getString(DialogWhere.SEND_WHERE, "Не выбрано"));
            Log.d("NEWTAG", "set " + sharedPreferences.getString(DialogWhere.SEND_WHERE, "Не выбрано"));
        } else {
            tvWhere.setText("Не выбрано");
        }
        if (sharedPreferences.contains(DialogWho.SEND_WHO)) {
            tvWho.setText(sharedPreferences.getString(DialogWho.SEND_WHO, "Не выбрано"));
        } else {
            tvWho.setText("Не выбрано");
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                if(isValueValid()){
                    Intent intent = new Intent(getActivity(),Choose.class);
                    startActivity(intent);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Выберите данные", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.choose_Who:
                openDialogWho();
                break;
            case R.id.choose_Where:
                openDialogWhere();
                break;


        }

    }

     public boolean isValueValid() {
        String who = tvWho.getText().toString().toLowerCase();
        String where =  tvWhere.getText().toString().toLowerCase();
        if(who.equals("не выбрано") || where.equals("не выбрано")){
            return false;
        }
        else
          return true;
    }

    public void openDialogWhere() {
        DialogWhere dialogWhere = new DialogWhere(new WhereCallback() {
            @Override
            public void whereCallback(String where) {
                tvWhere.setText(where);
            }
        });
        dialogWhere.show(getActivity().getSupportFragmentManager(), "w");

    }

    public void openDialogWho() {
        DialogWho dialogWho = new DialogWho(new WhereCallback() {
            @Override
            public void whereCallback(String where) {
                tvWho.setText(where);
            }
        });
        dialogWho.show(getFragmentManager(), "q");
    }


}
