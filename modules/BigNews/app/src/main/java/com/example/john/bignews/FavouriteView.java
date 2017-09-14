package com.example.john.bignews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hq.usermanager.Newsabs;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavouriteView extends AppCompatActivity {

    private ArrayList<Newsabs> listItems;
    private ListView listView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_view);
        intent = new Intent(this, Reader.class);
        listView = (ListView)findViewById(R.id.FavouriteList);

        listItems = new ArrayList<Newsabs>(Newsabs.getCachedAbstractByCategory("科技"));
        listView.setAdapter(new ListAdapter(this, listItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", listItems.get(i - 1).getNewsID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
