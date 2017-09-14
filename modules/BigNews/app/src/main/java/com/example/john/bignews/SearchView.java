package com.example.john.bignews;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hq.usermanager.Newsabs;
import com.example.sth.net.NewsParam;

import java.util.ArrayList;

public class SearchView extends AppCompatActivity {
    private ArrayList<Newsabs> listItems;
    private ListView listView;
    private EditText editText;
    private Button button;
    private Intent intent;
    private Handler mHandler;

    private SearchView instance()
    {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setContentView(R.layout.activity_search_view);
        intent = new Intent(this, Reader.class);
        listView = (ListView)findViewById(R.id.SearchList);
        editText = (EditText)findViewById(R.id.EditSearch);
        button = (Button)findViewById(R.id.SearchButton);

        editText.setWidth(MainActivity.static_width * 4 / 5 - 60);
        button.setWidth(MainActivity.static_width / 5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        Newsabs.grab(new NewsParam().setKeyword(editText.getText().toString()));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listItems = new ArrayList<Newsabs>(Newsabs.searchAbstractOffline(editText.getText().toString()));
                                listView.setAdapter(new ListAdapter(instance(), listItems));
                            }
                        });
                    }
                }.start();
            }
        });

        listItems = new ArrayList<Newsabs>();
        listView.setAdapter(new ListAdapter(this, listItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", listItems.get(i).getNewsID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
