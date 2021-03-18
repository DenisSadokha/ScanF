package com.example.scanf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class Choose extends AppCompatActivity implements View.OnClickListener {
    Button bGive, bBack, bList;
    Realm realm;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_act);
        bBack = findViewById(R.id.bBack);
        bGive = findViewById(R.id.bGive);
        bList = findViewById(R.id.bList);
        bBack.setOnClickListener(this);
        bGive.setOnClickListener(this);
        bList.setOnClickListener(this);
        Realm.init(getApplicationContext());
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        MyAdapter myAdapter = new MyAdapter(realm.where(DataCenter.class).findAll(),this);
        int b = myAdapter.getItemCount();
        bList.setText("СПИСОК" + " " +"("+b+")");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bBack:
                finish();
                break;
            case R.id.bList:
                Intent intent1 = new Intent(Choose.this, NomList.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bGive:
                Intent intent = new Intent(getApplicationContext(), Nomenkl.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
