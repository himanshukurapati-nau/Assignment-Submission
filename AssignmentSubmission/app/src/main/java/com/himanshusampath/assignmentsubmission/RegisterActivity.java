package com.himanshusampath.assignmentsubmission;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    Spinner branchDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        branchDropdown=findViewById(R.id.branchdrop);
        String[] items = new String[]{"CSE", "IT", "ECE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        branchDropdown.setAdapter(adapter);
    }
}