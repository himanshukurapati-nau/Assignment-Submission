package com.himanshusampath.assignmentsubmission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button registerbutton;

    private FirebaseAuth mAuth;

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

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if Firebase is already signed in
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser != null)
        {
            //already signed in
            openFirstActivity();
        }
        else
        {
            //User not signed in

        }
    }

    private void openLoginActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openFirstActivity(){
        Intent intent=new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    private void openRegistrationActivity(){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}