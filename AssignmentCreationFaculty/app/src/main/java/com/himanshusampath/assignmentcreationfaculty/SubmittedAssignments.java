package com.himanshusampath.assignmentcreationfaculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SubmittedAssignments extends AppCompatActivity
{
    RecyclerView recyclerView;

    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;

    String branch;
    String assignmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_assignments);

        AssignUIElements();

    }

    private void AssignUIElements()
    {
        recyclerView = findViewById(R.id.recview);
        GetVariables();
    }

    private void GetVariables()
    {
        Intent intent = getIntent();
        branch = intent.getStringExtra("branch");
        assignmentId = intent.getStringExtra("uuid");
    }

    private void GetSubmittedAssignments()
    {
        //Query
        Query query = firebaseFirestore.collection("submittedassignments").document(branch).collection(assignmentId);//.whereEqualTo("branch",userBranch);//.orderBy("timestamp", Query.Direction.ASCENDING);
        Log.d("Astro","Branch: "+branch);
        Log.d("Astro","Assignment id: "+assignmentId);

        //Recycler Options
        FirestoreRecyclerOptions<AssignmentModel> options = new FirestoreRecyclerOptions.Builder<AssignmentModel>()
                .setQuery(query, AssignmentModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<AssignmentModel, ProductsViewHolder>(options)
        {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull AssignmentModel model)
            {
                holder.rollnumber.setText(model.getRollnumber());
                holder.name.setText(model.getStudentname());
                holder.uri=model.getUri();
//                Log.d("Astro","Submitted Date: "+model.getSubmitteddate());
//                Log.d("Astro","Roll Number: "+model.getRollnumber());
//                Log.d("Astro","Student Name: "+model.getStudentname());

            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubmittedAssignments.this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    private class ProductsViewHolder extends RecyclerView.ViewHolder
    {
//        TextView subjectName, assignmentName, endDate;
        String uri;
        TextView name,rollnumber,date,answers,status;
        Button pdfbutton;

        public ProductsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.studentname);
            rollnumber = itemView.findViewById(R.id.rollnumber);
            pdfbutton=itemView.findViewById(R.id.pdfbutton);

            pdfbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d("Astro","Called");
                    DisplayPDF(uri);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
//                Intent intent = new Intent(PendingSubmission.this,ProjectView.class);
//                intent.putExtra("assignmentName",assignmentName.getText());
//                intent.putExtra("subjectName",subjectName.getText());
//                intent.putExtra("endDate",endDate.getText());
//                intent.putExtra("uri",uri);
//                intent.putExtra("uuid",uuid);
//                intent.putExtra("branch",userBranch);
//                intent.putExtra("rollnumber",rollNumber);
//                intent.putExtra("name",studentName);
//                Log.d("Astro","Student Name Sent: "+studentName);
//                Log.d("Astro","Roll Number Sent: "+rollNumber);
//                startActivity(intent);
                }
            });
        }


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        GetSubmittedAssignments();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    private void DisplayPDF(String uri)
    {
        Log.d("Astro","URI: "+uri);
        Intent intent=new Intent(SubmittedAssignments.this,PDFViewerActivity.class);
        intent.putExtra("pdfurl",uri);
        startActivity(intent);

    }

}
