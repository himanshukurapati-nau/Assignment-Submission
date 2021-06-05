package com.himanshusampath.assignmentsubmission;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PendingSubmission extends AppCompatActivity {
    Context context;
    CardView cardview;
    public String subject="Machine Learning";
    public String title="Assignment 1";
    public String date="12/06/2021";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_submission);
        context = getApplicationContext();
        CreateCardView(subject,title,date);
//        relativelayout = (RelativeLayout)findViewById(R.id.relativelayout1);

    }

    public void CreateCardView(String subject,String title,String date){
        cardview = new CardView(context);
        setContentView(R.layout.activity_main);
        cardview.setCardBackgroundColor(Color.GREEN);

        TextView Subject = new TextView(context);
        Subject.setText("Subject "+subject);
        cardview.addView(Subject);

        TextView Title = new TextView(context);
        Title.setText("Assignment Name"+title);
        cardview.addView(Subject);

        TextView Date = new TextView(context);
        Date.setText("Assignment Name: "+date);
        cardview.addView(Subject);
    }
}