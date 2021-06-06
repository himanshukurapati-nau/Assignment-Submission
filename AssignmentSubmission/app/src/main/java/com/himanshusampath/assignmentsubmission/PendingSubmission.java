package com.himanshusampath.assignmentsubmission;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PendingSubmission extends AppCompatActivity
{
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_submission);

        recyclerView = findViewById(R.id.recview);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("assignments");
        //Recycler Options
        FirestoreRecyclerOptions<model> options = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query, model.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<model, ProductsViewHolder>(options)
        {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_single, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull model model)
            {
                holder.subjectName.setText(model.getSubjectname());
                holder.assignmentName.setText(model.getAssignmentname());
                holder.endDate.setText(model.getEnddate());
            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingSubmission.this));
        recyclerView.setAdapter(adapter);

    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder
    {
        TextView subjectName, assignmentName, endDate;

        public ProductsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subjectname);
            assignmentName = itemView.findViewById(R.id.assignmentname);
            endDate = itemView.findViewById(R.id.enddate);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}