package com.example.sth.net;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ThinkPad on 2017/9/13.
 */

public class ImageLoader {

    public  static Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }


}
