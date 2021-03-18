package com.example.scanf;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DialogDocument extends Dialog {
    TextView text;
    Button b;
    Activity a;

    public  DialogDocument(Activity c){
      super(c);
      a=c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_document);
        text = findViewById(R.id.textView);
        b=findViewById(R.id.but);
if(NomList.postDocument==null||!NomList.postDocument.equals("")){
    text.setText("Ошбика");
}
else{
    text.setText(NomList.postDocument);
}
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 cancel();
            }
        });
    }
}
