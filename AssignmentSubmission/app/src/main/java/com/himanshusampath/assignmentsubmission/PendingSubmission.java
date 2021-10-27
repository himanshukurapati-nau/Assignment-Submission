package com.himanshusampath.assignmentsubmission;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PendingSubmission extends AppCompatActivity
{
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    
    ImageView signOutButton;
    static String userBranch;
    static String studentName;
    static String rollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_submission);

        recyclerView = findViewById(R.id.recview);
        signOutButton=findViewById(R.id.backButton);

        signOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userSignout();
            }
        });
    }

    private void getAssignmentList()
    {
        //Query
        Query query = firebaseFirestore.collection("assignments").whereEqualTo("branch",userBranch);//.orderBy("timestamp", Query.Direction.ASCENDING);
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
                holder.uri=model.geturi();
                holder.uuid=model.getUuid();
            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingSubmission.this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder
    {
        TextView subjectName, assignmentName, endDate;
        String uri,uuid;

        public ProductsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subjectname);
            assignmentName = itemView.findViewById(R.id.assignmentname);
            endDate = itemView.findViewById(R.id.enddate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PendingSubmission.this,ProjectView.class);
                    intent.putExtra("assignmentName",assignmentName.getText());
                    intent.putExtra("subjectName",subjectName.getText());
                    intent.putExtra("endDate",endDate.getText());
                    intent.putExtra("uri",uri);
                    intent.putExtra("uuid",uuid);
                    intent.putExtra("branch",userBranch);
                    intent.putExtra("rollnumber",rollNumber);
                    intent.putExtra("name",studentName);
                    Log.d("Astro","Student Name Sent: "+studentName);
                    Log.d("Astro","Roll Number Sent: "+rollNumber);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //Get Branch of User
        FirebaseUser user = mAuth.getCurrentUser();
        String firebaseUid= user.getUid();
        DocumentReference docRef = firebaseFirestore.collection("student").document(firebaseUid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userBranch = documentSnapshot.getString("branch");
                studentName=documentSnapshot.getString("name");
                rollNumber=documentSnapshot.getString("rollnumber");
                Log.d("Astro","Student Name Got: "+studentName);
                Log.d("Astro","Roll Number Got: "+rollNumber);
                getAssignmentList();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    private void userSignout()
    {
        mAuth.signOut();
        OpenFirstActivity();
        Toast.makeText(PendingSubmission.this, "Signed Out Successfully",Toast.LENGTH_SHORT).show();
    }

    private void OpenFirstActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}