package com.example.john.bignews;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hq.usermanager.Newsdata;
import com.example.hq.usermanager.SavedNews;
import com.example.hq.usermanager.User;
import com.example.hq.usermanager.ViewedNews;
import com.example.sth.net.ImageLoader;
import com.example.sth.net.Speech;
import com.github.clans.fab.FloatingActionButton;
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
    private Button button;
    private boolean prepared;
    private FloatingActionButton fab;
    private Newsdata tmp;
    private boolean reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reading = false;
        setContentView(R.layout.activity_reader);
        bundle = this.getIntent().getExtras();
        readerLayout = (LinearLayout) findViewById(R.id.ReaderLayout);
        titleView = (TextView) findViewById(R.id.ReaderTitle);
        contentView = (TextView) findViewById(R.id.ReaderContent);
        fab = (FloatingActionButton) findViewById(R.id.FavouriteNews);
        button = (Button) findViewById(R.id.ReaderButton);
        button.setBackgroundColor(Color.parseColor("#F0FFF0"));
        button.setBackgroundResource(android.R.drawable.ic_media_play);
        prepared = false;
        if (Newsdata.get(bundle.getString("ID")).isComplete()) prepare(); else{titleView.setText("Waiting"); fab.setEnabled(false);}


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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SavedNews.exist(User.getUser(), tmp))
                {
                    fab.setImageResource(android.R.drawable.star_big_off);
                    SavedNews.unsave(User.getUser(), tmp);
                }
                else
                {
                    fab.setImageResource(android.R.drawable.star_big_on);
                    SavedNews.save(User.getUser(), tmp);
                }
            }
        });
    }

    private void prepare()
    {
        if (prepared) return;
        prepared = true;
        tmp = Newsdata.get(bundle.getString("ID"));
        ViewedNews.view(User.getUser(), tmp.getAbs());
        titleView.setText(tmp.getTitle());
        contentView.setText(tmp.getContent());
        fab.setEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reading) {
                    Speech.start(tmp.getContent());
                    reading = true;
                }
                else {
                    Speech.stop();
                    reading = false;
                }
            }
        });

        if (SavedNews.exist(User.getUser(), tmp)) fab.setImageResource(android.R.drawable.star_big_on);
        else fab.setImageResource(android.R.drawable.star_big_off);
        ArrayList<String> list = tmp.getPictures();
        for (String pic: list)
        {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.content_reader_image, null);
            ImageView imageView = view.findViewById(R.id.ReaderImage);
            imageView.setMaxWidth(MainActivity.static_width);
            imageView.setMaxHeight(MainActivity.static_height);
            readerLayout.addView(view);

            new Thread() {
                ImageView t;
                String imgURL;

                public Thread set(ImageView t, String imgURL) {
                    this.t = t;
                    this.imgURL = imgURL;
                    return this;
                }

                @Override
                public void run() {
                    final Bitmap bitmap = ImageLoader.getbitmap(imgURL);
                    t.post(new Runnable() {
                        @Override
                        public void run() {
                            t.setImageBitmap(bitmap);
                        }
                    });
                }
            }.set(imageView, pic).start();
        }
    }
}
