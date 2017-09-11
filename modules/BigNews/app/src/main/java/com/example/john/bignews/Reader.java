package com.example.john.bignews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hq.usermanager.Newsdata;

public class Reader extends AppCompatActivity {

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Bundle bundle = this.getIntent().getExtras();
        str = Newsdata.get(bundle.getString("ID")).getDisplay();
        TextView textView = (TextView) findViewById(R.id.ReaderText);
        textView.setText(str);
    }

}
