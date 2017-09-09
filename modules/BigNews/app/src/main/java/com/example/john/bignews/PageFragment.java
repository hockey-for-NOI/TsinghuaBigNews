package com.example.john.bignews;

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
    private int mPage;
    private ArrayList<String> listItems;
    private FragmentManager fm;
    private FragmentTransaction ft;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        listItems = new ArrayList<String>();
        listItems.add("Caonima");
        listItems.add("Shabi");
        listItems.add("NImabi");
        listItems.add("C!");
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



            int s_width = 720;

            ViewGroup.LayoutParams para;
            para = imageView.getLayoutParams();
            para.height = 150;
            para.width = s_width;


            textView.setText(listItem.get(position).toString());

            textView.setWidth(510);

            para.width = 150;

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

