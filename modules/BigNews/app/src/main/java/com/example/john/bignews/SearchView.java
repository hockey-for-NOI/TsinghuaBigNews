package com.example.john.bignews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hq.usermanager.Newsabs;

import java.util.ArrayList;

public class SearchView extends AppCompatActivity {
    private ArrayList<Newsabs> listItems;
    private ListView listView;
    private EditText editText;
    private Button button;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            }
        });

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
