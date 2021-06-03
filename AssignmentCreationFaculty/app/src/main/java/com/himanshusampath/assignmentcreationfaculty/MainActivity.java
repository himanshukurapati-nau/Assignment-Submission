package com.himanshusampath.assignmentcreationfaculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{

    public Button loginButton, registerButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginbutton);
        registerButton = findViewById(R.id.registerbutton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                loginActivity();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                registerActivity();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //check if Firebase is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            //already signed in
            openFirstActivity();
        } else
        {
            //User not signed in

        }
    }

    private void loginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openFirstActivity()
    {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    private void registerActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}