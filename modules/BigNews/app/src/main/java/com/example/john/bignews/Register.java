package com.example.john.bignews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hq.usermanager.User;

public class Register extends AppCompatActivity {

    private Intent intent;
    private TextView warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        intent = new Intent(this, Login.class);
        warn = (TextView)findViewById(R.id.RegWarn);
        Button logBut = (Button)findViewById(R.id.RegButton);
        TextView regT = (TextView)findViewById(R.id.LoginText);

        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editUser = (EditText)findViewById(R.id.EditRegUser);
                EditText editPass1 = (EditText)findViewById(R.id.EditRegPass1);
                EditText editPass2 = (EditText)findViewById(R.id.EditRegPass2);
                try
                {
                    User.register(editUser.getText().toString(), editPass1.getText().toString(), editPass2.getText().toString());
                    finish();
                }
                catch(Exception e)
                {
                    warn.setText(e.getMessage());
                }
            }
        });

        regT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });


    }
}
