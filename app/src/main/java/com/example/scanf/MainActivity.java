package com.example.scanf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import helper_for_dataBase.HelperForDataWho;
import helper_for_dataBase.HelpreForDataWhere;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button butExit, butUchet, butNomencl;
    ImageView settings;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butExit = findViewById(R.id.butExit);
        Realm.init(this);
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        butUchet = findViewById(R.id.butUchet);
        butNomencl = findViewById(R.id.butNomencl);
        butExit.setOnClickListener(this);
        butUchet.setOnClickListener(this);
        butNomencl.setOnClickListener(this);
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }

    private void showDialog() {
        DialogChooseUser d = new DialogChooseUser();

        d.show(getSupportFragmentManager(), "q");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butExit:
                finish();
                break;
            case R.id.butUchet:
                break;
            case R.id.butNomencl:
                showDialog();
                break;
            case R.id.settings:
                Intent intent1 = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent1);
                break;

        }

    }
}
