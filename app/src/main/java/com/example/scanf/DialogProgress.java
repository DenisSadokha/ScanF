package com.example.scanf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ProgressBar;

public class DialogProgress extends Dialog {
    public DialogProgress(Activity c){
        super(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        ProgressBar progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);


    }
}
