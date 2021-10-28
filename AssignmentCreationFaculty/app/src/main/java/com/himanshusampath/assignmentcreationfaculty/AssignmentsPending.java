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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AssignmentsPending extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private Spinner branchDropdown;
    static String[] items = new String[]{"CSE", "IT", "ECE", "EEE", "Mechanical"};
    private String branchString;

    RecyclerView recyclerView;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    FirestoreRecyclerAdapter adapter;
    ImageView backButton;
    
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_pending);

        AssignUI();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        branchDropdown.setAdapter(adapter);
        branchDropdown.setOnItemSelectedListener(this);
        branchString = branchDropdown.getSelectedItem().toString();
        mAuth = FirebaseAuth.getInstance();
    }

    private void AssignUI()
    {
        branchDropdown=findViewById(R.id.branchdrop);
        recyclerView = findViewById(R.id.recview);
        backButton=findViewById(R.id.backButton);
        fab=findViewById(R.id.fab);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userSignout();
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenNewAssignment();
            }
        });
    }

    private void getAssignmentList()
    {
        //Query
        Query query = firebaseFirestore.collection("assignments").whereEqualTo("branch",branchString);//.orderBy("timestamp", Query.Direction.ASCENDING);
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
                Log.d("Astro","Submitted Date: "+model.geturi());
                Log.d("Astro","Roll Number: "+model.getUuid());

            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentsPending.this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        //Value of branch drop down is changed
         branchString=parent.getSelectedItem().toString();
         getAssignmentList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

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
                    Intent intent = new Intent(AssignmentsPending.this,SubmittedAssignments.class);
//                    intent.putExtra("assignmentName",assignmentName.getText());
//                    intent.putExtra("subjectName",subjectName.getText());
//                    intent.putExtra("endDate",endDate.getText());
//                    intent.putExtra("uri",uri);
                    intent.putExtra("uuid",uuid);
                    intent.putExtra("branch",branchString);
//                    intent.putExtra("rollnumber",rollNumber);
//                    intent.putExtra("name",studentName);
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
        getAssignmentList();
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
        Toast.makeText(AssignmentsPending.this, "Signed Out Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void OpenFirstActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void OpenNewAssignment()
    {
        Intent intent = new Intent(this, NewAssignmentCreation.class);
        startActivity(intent);
    }

}