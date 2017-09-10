package com.example.john.bignews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hq.usermanager.User;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private Intent intent;
    private TextView warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, Register.class);

        warn = (TextView)findViewById(R.id.LogWarn);
        Button logBut = (Button)findViewById(R.id.LogButton);
        TextView regT = (TextView)findViewById(R.id.RegisterText);

        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editUser = (EditText)findViewById(R.id.EditLogUser);
                EditText editPass = (EditText)findViewById(R.id.EditLogPass);
                try
                {
                    User.login(editUser.getText().toString(),editPass.getText().toString(), false, false);
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
