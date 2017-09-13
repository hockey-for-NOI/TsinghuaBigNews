package com.example.john.bignews;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hq.usermanager.Newsdata;
import com.example.sth.net.ImageLoader;
import com.google.common.reflect.Parameter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Math.floor;

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
        for (int i=0; i<readerLayout.getChildCount(); i++)
            if (readerLayout.getChildAt(i) instanceof ImageView)
                readerLayout.removeView(readerLayout.getChildAt(i));
        Newsdata tmp = Newsdata.get(bundle.getString("ID"));
        titleView.setText(tmp.getTitle());
        contentView.setText(tmp.getContent());
        ArrayList<String> list = tmp.getPictures();
        for (int pid=0; pid<list.size(); pid++)
        {
            String pic = list.get(pid);
            ImageView imageView = new ImageView(this);
            readerLayout.addView(imageView);
            imgURL = pic;

            new Thread() {
                ImageView t;

                public Thread set(ImageView t) {
                    this.t = t;
                    return this;
                }

                @Override
                public void run() {
                    final Drawable drawable = ImageLoader.loadImageFromNetwork(imgURL);
                    t.post(new Runnable() {
                        @Override
                        public void run() {
                            t.setImageDrawable(drawable);
                            t.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        }
                    });
                }
            }.set(imageView).start();
        }
    }
}
