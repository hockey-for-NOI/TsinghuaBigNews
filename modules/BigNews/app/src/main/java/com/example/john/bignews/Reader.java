package com.example.john.bignews;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hq.usermanager.Newsdata;

import org.w3c.dom.Text;

public class Reader extends AppCompatActivity {

    private String str;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        final Bundle bundle = this.getIntent().getExtras();
        final TextView textView = (TextView) findViewById(R.id.ReaderText);
        str = Newsdata.get(bundle.getString("ID")).getDisplay();
        textView.setText(str);

        mHandler = new Handler();

        final String idToGrab = bundle.getString("ID");
        Thread t = new Thread() {
            public void run() {
                Newsdata.grab(idToGrab);
                mHandler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                Newsdata tmp = Newsdata.get(bundle.getString("ID"));
                                TextView title = (TextView)findViewById(R.id.ReaderTitle);
                                title.setText(tmp.getTitle());
                                TextView content = (TextView)findViewById(R.id.ReaderContent);
                                title.setText(tmp.getContent());
                                ArrayList<String> list = tmp.getImage();
                                
                            }
                        }
                );
            }
        };
        t.start();

    }

}
