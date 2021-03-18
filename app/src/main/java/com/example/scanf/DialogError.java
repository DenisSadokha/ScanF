package com.example.scanf;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogError extends Dialog {
    Button but;
    TextView tv;
    Activity a;
    public DialogError(Activity c){
        super(c);
        a=c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_error);
        but = findViewById(R.id.button);
        tv= findViewById(R.id.textView2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


    }
}
