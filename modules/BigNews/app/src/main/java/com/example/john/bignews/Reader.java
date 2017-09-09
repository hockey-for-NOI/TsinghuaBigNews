package com.example.john.bignews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Reader extends AppCompatActivity {

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Bundle bundle = this.getIntent().getExtras();
        str = bundle.getString("sb");
        TextView textView = (TextView) findViewById(R.id.ReaderText);
        textView.setText(str);
    }

}
