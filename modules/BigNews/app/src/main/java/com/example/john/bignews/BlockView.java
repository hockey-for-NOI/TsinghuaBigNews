package com.example.john.bignews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.hq.usermanager.User;

import java.util.ArrayList;

public class BlockView extends AppCompatActivity {

    private ArrayList<CheckBox> checkBoxList;
    private LinearLayout blockBox;

    private BlockView instance()
    {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_view);

        blockBox = (LinearLayout) findViewById(R.id.BlockPage);
        final EditText editText = (EditText)findViewById(R.id.EditBlock);
        editText.setWidth(MainActivity.static_width * 4 / 5 - 60);
        Button button = (Button)findViewById(R.id.BlockButton);
        button.setWidth(MainActivity.static_width / 5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = new CheckBox(instance());
                cb.setText(editText.getText());
                cb.setChecked(true);
                checkBoxList.add(cb);
                blockBox.addView(cb);
            }
        });

        ArrayList<String> allCategory = com.example.sth.net.Category.getAllNames(), favouriteCategory = User.getFavouriteCategories();
        checkBoxList = new ArrayList<CheckBox>();
        for (String i : allCategory)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(i);
            cb.setChecked(favouriteCategory.contains(i));
            checkBoxList.add(cb);
            blockBox.addView(cb);
        }

        Button save = (Button)findViewById(R.id.BlockSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = new ArrayList<String>();
                for (CheckBox i : checkBoxList)
                {
                    if (i.isChecked()) list.add(i.getText().toString());
                }
                MainActivity.mainInstance.refresh();
                finish();
            }
        });
        Button cancel = (Button)findViewById(R.id.BlockCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
