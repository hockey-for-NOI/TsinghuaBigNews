package com.example.john.bignews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hq.usermanager.Newsabs;
import com.example.sth.net.ImageLoader;

import java.util.ArrayList;

/**
 * Created by John on 2017/9/14.
 */

class ListAdapter extends BaseAdapter
{
    LayoutInflater inflater;
    private ArrayList<Newsabs> listItem;
    Handler mHandler;

    public ListAdapter(Context context, ArrayList<Newsabs> listItems)
    {
        inflater = LayoutInflater.from(context);
        this.listItem = listItems;
        mHandler = new Handler();
    }

    public int getCount(){
        // TODO Auto-generated method stub
        return listItem.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listItem.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.content_abstract_info, null);
        LinearLayout textPack = (LinearLayout)view.findViewById(R.id.text_pack);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageview);

        ViewGroup.LayoutParams para;
        para = textPack.getLayoutParams();
        para.width = MainActivity.static_width - MainActivity.static_width / 5 - 60;
        textPack.setLayoutParams(para);

        TextView textView = (TextView)textPack.findViewById(R.id.text_name);
        textView.setText(listItem.get(position).getTitle());
        TextView    absView = (TextView)textPack.findViewById(R.id.text_abs);
        absView.setText(listItem.get(position).getContent());

        para = imageView.getLayoutParams();
        para.height = MainActivity.static_width/5;
        para.width = MainActivity.static_width/5;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(para);
        imageView.setImageResource(R.drawable.z0);

        final String imgstr = listItem.get(position).getFirstPicture();
        if (imgstr != null)
        {
            final ImageView tmpView = imageView;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable drawable = ImageLoader.loadImageFromNetwork(imgstr);
                    if (drawable != null)
                        tmpView.post(new Runnable() {
                            @Override
                            public void run() {
                                tmpView.setImageDrawable(drawable);
                            }
                        });
                }
            }).start();
        }

        return view;
    }
}
