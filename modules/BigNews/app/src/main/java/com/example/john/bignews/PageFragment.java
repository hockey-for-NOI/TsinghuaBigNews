package com.example.john.bignews;

import com.example.hq.usermanager.*;
import com.example.sth.net.Category;
import com.example.sth.net.NewsParam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private String pageCategory;
    private ArrayList<Newsabs> listItems;
    private FragmentManager fm;
    private FragmentTransaction ft;
    SwipeRefreshLayout srl;
    View view;
    Handler mHandler;
    ListView listView;

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
        mHandler = new Handler();
        view = inflater.inflate(R.layout.fragment_page, container, false);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);

                new Thread(){
                    @Override
                    public  void    run() {
                        Newsabs.grab(new NewsParam().setCategory(Category.getNum(pageCategory))
                                .setPageNo(((listItems == null ? 0 : listItems.size()) + 519) / 500).setPageSize(500));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory(pageCategory)
                                        .subList(1, (listItems == null ? 0 : listItems.size()) + 20));
                                listView.setAdapter(new ListAdapter(view.getContext(), listItems));
                            }
                        });
                    }
                }.start();

                srl.setRefreshing(false);
            }
        });

        listView = (ListView) view.findViewById(R.id.listView);
        listItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory(pageCategory).subList(1,20));
        listView.setAdapter(new ListAdapter(view.getContext(), listItems));
        listView.setOnItemClickListener(new ClickEvent());
        return view;
    }

    class ListAdapter extends BaseAdapter
    {
        LayoutInflater inflater;
        private ArrayList<Newsabs> listItem;

        public ListAdapter(Context context, ArrayList<Newsabs> listItems)
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
            view = inflater.inflate(R.layout.content_abstract_info, null);
            TextView textView = (TextView)view.findViewById(R.id.text_name);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageview);

            ViewGroup.LayoutParams para;
            para = imageView.getLayoutParams();
            para.height = MainActivity.static_width/5;
            para.width = MainActivity.static_width/5;


            textView.setText(listItem.get(position).getAbs());

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
            bundle.putString("ID", listItems.get(arg2).getNewsID());

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

}

