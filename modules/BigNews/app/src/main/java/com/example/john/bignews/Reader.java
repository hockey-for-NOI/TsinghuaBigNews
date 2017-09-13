package com.example.john.bignews;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hq.usermanager.Newsdata;
import com.example.sth.net.ImageLoader;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Reader extends AppCompatActivity {

    private Bundle bundle;
    private String str;
    private Handler mHandler;
    private LinearLayout readerLayout;
    private TextView titleView, contentView;
    private String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        bundle = this.getIntent().getExtras();
        readerLayout = (LinearLayout) findViewById(R.id.ReaderLayout);
        titleView = (TextView) findViewById(R.id.ReaderTitle);
        contentView = (TextView) findViewById(R.id.ReaderContent);
        if (Newsdata.get(bundle.getString("ID")).isComplete()) prepare(); else titleView.setText("Waiting");

        mHandler = new Handler();

        final String idToGrab = bundle.getString("ID");
        Thread t = new Thread() {
            public void run() {
                Newsdata.grab(idToGrab);
                if (Newsdata.get(bundle.getString("ID")).isComplete())
                mHandler.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                prepare();
                            }
                        }
                );
            }
        };
        t.start();

    }

    private void prepare()
    {
        Newsdata tmp = Newsdata.get(bundle.getString("ID"));
        titleView.setText(tmp.getTitle());
        contentView.setText(tmp.getContent());
        ArrayList<String> list = tmp.getPictures();
        for (String pic : list)
        {
            final ImageView imageView = new ImageView(this);
            imageView.setMaxWidth(MainActivity.static_width);
            imageView.setMaxHeight(MainActivity.static_height);
            readerLayout.addView(imageView);
            imgURL = pic;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable drawable = ImageLoader.loadImageFromNetwork(imgURL);
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                }
            }).start();
        }
    }
}
