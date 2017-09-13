package com.example.john.bignews;

import com.example.hq.usermanager.*;
import com.example.sth.net.Category;
import com.example.sth.net.NewsParam;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
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
    PullToRefreshListView prl;
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

        prl = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        prl.setMode(PullToRefreshBase.Mode.BOTH);
        prl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase <ListView> refreshView) {
                if (refreshView.isFooterShown()) {
                    new Thread() {
                        @Override
                        public void run() {
                            Newsabs.grab(new NewsParam().setCategory(Category.getNum(pageCategory))
                                    .setPageNo(((listItems == null ? 0 : listItems.size()) + 519) / 500).setPageSize(500));
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayList<Newsabs> tmpItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory(pageCategory));
                                    int lf = (listItems == null ? 0 : listItems.size());
                                    if (tmpItems.size() < lf) {
                                        listItems.clear();
                                        listItems.addAll(tmpItems);
                                    }
                                    else {
                                        int ri = lf + 20;
                                        if (tmpItems.size() < ri) ri = tmpItems.size();
                                        tmpItems = new ArrayList<Newsabs>(tmpItems.subList(lf, ri));
                                        listItems.addAll(tmpItems);
                                    }
                                    prl.onRefreshComplete();
                                }
                            });
                            }
                    }.start();
                }
                else {
                    new Thread() {
                        @Override
                        public void run() {
                            Newsabs.grab(new NewsParam().setCategory(Category.getNum(pageCategory))
                                    .setPageNo(1).setPageSize(500));
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listItems.clear();
                                    ArrayList<Newsabs> tmpItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory(pageCategory));
                                    if (tmpItems.size() > 20) tmpItems = new ArrayList<Newsabs>(tmpItems.subList(0, 20));
                                    listItems.addAll(tmpItems);
                                    prl.onRefreshComplete();
                                }
                            });
                        }
                    }.start();
                }
            }
        });

        listView = prl.getRefreshableView();
        ArrayList<Newsabs> tmpItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory(pageCategory));
        if (tmpItems.size() > 20) tmpItems = new ArrayList<Newsabs>(tmpItems.subList(0, 20));
        listItems = tmpItems;
        prl.setAdapter(new ListAdapter(view.getContext(), listItems));
        listView.setOnItemClickListener(new ClickEvent());

        return view;
    }

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
            view = inflater.inflate(R.layout.content_abstract_info, null);
            LinearLayout textPack = (LinearLayout)view.findViewById(R.id.text_pack);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageview);

            ViewGroup.LayoutParams para;
            para = textPack.getLayoutParams();
            para.width = MainActivity.static_width - MainActivity.static_width / 5 - 60;
            textPack.setLayoutParams(para);

            TextView    textView = (TextView)textPack.findViewById(R.id.text_name);
            textView.setText(listItem.get(position).getTitle());
            TextView    absView = (TextView)textPack.findViewById(R.id.text_abs);
            absView.setText(listItem.get(position).getContent());

            para = imageView.getLayoutParams();
            para.height = MainActivity.static_width/5;
            para.width = MainActivity.static_width/5;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(para);
            imageView.setImageResource(R.drawable.z0);

            String imgstr = listItem.get(position).getFirstPicture();
            if (imgstr != null)
            {
                new Thread() {
                    @Override
                    public void run(){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }.start();
            }

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

