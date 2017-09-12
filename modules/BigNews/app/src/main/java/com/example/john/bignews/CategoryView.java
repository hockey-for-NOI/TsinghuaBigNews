package com.example.john.bignews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.hq.usermanager.User;

import java.util.ArrayList;

public class CategoryView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.CategoryPage);
        ArrayList<String> allCategory = com.example.sth.net.Category.getAllNames(), favouriteCategory = User.getFavouriteCategories();
        final ArrayList<CheckBox> checkBoxView = new ArrayList<CheckBox>();
        for (String i : allCategory)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(i);
            cb.setChecked(favouriteCategory.contains(i));
            checkBoxView.add(cb);
            linearLayout.addView(cb);
        }

        Button save = (Button)findViewById(R.id.FavouriteCategorySave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = new ArrayList<String>();
                for (CheckBox i : checkBoxView)
                {
                    if (i.isChecked()) list.add(i.getText().toString());
                }
                try {
                    User.setFavouriteCategories(list);
                }
                catch (Exception e){}

                MainActivity.mainInstance.refresh();

                finish();
            }
        });
        Button cancel = (Button)findViewById(R.id.FavouriteCategoryCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
