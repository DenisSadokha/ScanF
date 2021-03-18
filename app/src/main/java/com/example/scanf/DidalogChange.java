package com.example.scanf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import io.realm.Realm;

public class DidalogChange extends  Dialog implements View.OnClickListener {
    private EditText editText;
    Activity activity;
    private Context context;
    private Button bOk, bCancle;
 private    TextView textView;

 String max;
 TextView tvMax;
 private Realm realm;


    public DidalogChange( Context context) {
        super(context);
        this.context=context;




    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
setContentView(R.layout.dialog_change);

         Realm.init(context);
         realm=Realm.getInstance(DataUtility.getDefaultConfig());
         bOk=findViewById(R.id.button2);
         bCancle=findViewById(R.id.button3);
         editText=findViewById(R.id.etDcount);
         tvMax=findViewById(R.id.tvMax);
         textView=findViewById(R.id.textView3);
        editText.setInputType(InputType.TYPE_NULL);
        max=  DataHelper.getMaxCount(realm,MyAdapter.getId);
         bOk.setOnClickListener(this);
         bCancle.setOnClickListener(this);
         tvMax.setText("Остаток: " + max);
        textView.setTextColor(Color.BLACK);




    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
              String b=  editText.getText().toString().trim();
              if(b.isEmpty()){
                  Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();


              }else {
                  Integer maxCount = Integer.parseInt(max);
                  Integer newCoount = Integer.parseInt(b);
                  if (maxCount >= newCoount) {
                      DataHelper.update(realm, MyAdapter.getId, b);
                      Toast.makeText(context, "Изменено", Toast.LENGTH_SHORT).show();
                      textView.setTextColor(Color.BLACK);
                      cancel();
                  } else

                      Toast.makeText(context, "Введеное количество превышает остаток", Toast.LENGTH_LONG).show();
                  editText.setText("");
                  textView.setText("Не хватает " + "(" + Math.abs(maxCount - newCoount) + ")");

                  textView.setTextColor(Color.RED);
              }
                  break;

            case R.id.button3:
          cancel();
         break;
        }

    }
}
