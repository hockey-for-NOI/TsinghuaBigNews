package com.example.john.bignews;

import com.example.hq.usermanager.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private String pageCategory;
    private ArrayList<String> listItems;
    private FragmentManager fm;
    private FragmentTransaction ft;

    public static PageFragment newInstance(String category) {
        Bundle args = new Bundle();
        args.putString(ARGS_PAGE, category);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageCategory = getArguments().getString(ARGS_PAGE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        listItems = User.getFavouriteCategories(); //TODO
        listItems.add(pageCategory);
        listItems.add(pageCategory);
        listItems.add(pageCategory);

        listView.setAdapter(new ListAdapter(view.getContext(), listItems));
        listView.setOnItemClickListener(new ClickEvent());
        return view;
    }

    class ListAdapter extends BaseAdapter
    {
        LayoutInflater inflater;
        private ArrayList<String> listItem;

        public ListAdapter(Context context, ArrayList<String> listItems)
        {
            inflater = LayoutInflater.from(context);
            this.listItem = listItems;
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
            TextView textView = (TextView)view.findViewById(R.id.text_name);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageview);

            ViewGroup.LayoutParams para;
            para = imageView.getLayoutParams();
            para.height = MainActivity.static_width/5;
            para.width = MainActivity.static_width/5;


            textView.setText(listItem.get(position).toString());

            textView.setWidth(MainActivity.static_width - para.width - 60);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(para);
            imageView.setImageResource(R.drawable.z0);

            return view;
        }
    }

    private class ClickEvent implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            Intent intent=new Intent(getActivity(), Reader.class);
            Bundle bundle = new Bundle();
            bundle.putString("sb", listItems.get(arg2).toString());
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

}

