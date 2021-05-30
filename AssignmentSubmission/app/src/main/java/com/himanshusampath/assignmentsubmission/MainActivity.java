package com.himanshusampath.assignmentsubmission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbutton = findViewById(R.id.login_button);
        registerbutton = findViewById(R.id.register_button);
        
        loginbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
    }

    private void openLoginActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openRegistrationActivity(){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}