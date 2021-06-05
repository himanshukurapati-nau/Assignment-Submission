package com.himanshusampath.assignmentcreationfaculty;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NewAssignmentCreation extends AppCompatActivity
{

    static String[] items = new String[]{"CSE", "IT", "ECE", "EEE", "Mechanical"};
    private static final int STORAGE_PERMISSION_CODE = 101;

    private Spinner branchDropdown;
    Button selectPDFButton;
    TextView fileNameTextView, submitButton;
    EditText subjectName, assignmentName, endDate;

    StorageReference storageReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assignment_creation);

        //sets options in branch drop down
        setBranchDropdown();

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        setUIReference();
    }

    private void setUIReference()
    {
        selectPDFButton = findViewById(R.id.selectpdfbutton);
        fileNameTextView = findViewById(R.id.filename);
        submitButton = findViewById(R.id.submitbutton);
        subjectName = findViewById(R.id.subjectinput);
        assignmentName = findViewById(R.id.assignmentname);
        endDate = findViewById(R.id.enddateinput);


        setActionListeners();
    }

    private void setBranchDropdown()
    {
        branchDropdown = findViewById(R.id.branchinput);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        branchDropdown.setAdapter(adapter);
    }

    private void setActionListeners()
    {
        selectPDFButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectPDF();
            }
        });
    }

    private void selectPDF()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(NewAssignmentCreation.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else
        {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent.createChooser(intent, "PDF FILE SELECT"), 12);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://"))
            {
                Cursor cursor = null;
                try
                {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst())
                    {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally
                {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://"))
            {
                displayName = myFile.getName();
            }
            fileNameTextView.setText(displayName);

            submitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileFirebase(Uri data)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("upload" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        //File successfully uploaded
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();

                        String branch = branchDropdown.getSelectedItem().toString();
                        String subjectname = subjectName.getText().toString();
                        String assignmentname = assignmentName.getText().toString();
                        String enddate = endDate.getText().toString();
                        Map<String, Object> assignment = new HashMap<>();
                        assignment.put("Subject Name", subjectname);
                        assignment.put("Assignment Name", assignmentname);
                        assignment.put("End Date", enddate);
                        assignment.put("Uri: ", uri);
                        db.collection("assignments")
                                .add(assignment)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference)
                                    {
                                        Toast.makeText(NewAssignmentCreation.this, "Assignment Created Successfully !!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(NewAssignmentCreation.this, "Assignment Created Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
            {
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("File Uploaded.. " + (int) progress + "%");
            }
        });
    }
}