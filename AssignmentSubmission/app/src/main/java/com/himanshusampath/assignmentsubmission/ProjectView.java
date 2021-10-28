package com.himanshusampath.assignmentsubmission;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProjectView extends AppCompatActivity
{
    TextView assignmentName,subjectName,endDate,fileName;
    ImageButton pdfButton,selectPdfButton,submitButton;
    String pdfUri,uuid,branch,studentName,rollNumber;

    private static final int STORAGE_PERMISSION_CODE = 101;
    StorageReference storageReference;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        AssignElements();
    }

    private void AssignElements()
    {
        //Assigns UI elements
        assignmentName=findViewById(R.id.assignmentname);
        subjectName=findViewById(R.id.subjectname);
        endDate=findViewById(R.id.enddate);
        pdfButton=findViewById(R.id.pdfbutton);
        fileName=findViewById(R.id.filename);
        selectPdfButton=findViewById(R.id.answerspdfbutton);
        submitButton=findViewById(R.id.submitbutton);

        pdfButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DisplayPDF();
            }
        });
        SetText();
        selectPdfButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectPDF();
            }
        });
    }

    private void SetText()
    {
        Intent intent = getIntent();
        String assignmentname = intent.getStringExtra("assignmentName");
        String subjectname = intent.getStringExtra("subjectName");
        String enddate = intent.getStringExtra("endDate");
        pdfUri=intent.getStringExtra("uri");
        uuid=intent.getStringExtra("uuid");
        branch=intent.getStringExtra("branch");
        studentName=intent.getStringExtra("name");
        rollNumber=intent.getStringExtra("rollnumber");
        Log.d("Astro","Student Name Received: "+studentName);
        Log.d("Astro","Roll Number Received: "+rollNumber);

        assignmentName.setText(assignmentname);
        subjectName.setText(subjectname);
        endDate.setText(enddate);
    }

    private void DisplayPDF()
    {
        Intent intent=new Intent(this,PDFViewerActivity.class);
        intent.putExtra("pdfurl",pdfUri);
        startActivity(intent);
    }

    private void selectPDF()
    {
        Log.v("Astro", "!!!! Select PDF !!!!!!");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ProjectView.this,
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
        Log.v("Astro", "!!!! onActivityResult Start !!!!!!");
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
            fileName.setText(displayName);

            submitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.v("Astro", "!!!! Submit Clicked !!!!!!");
                    if(data.getData() != null)
                    {uploadPDFFileFirebase(data.getData());}
                    else
                    {
                        Toast.makeText(ProjectView.this, "Select PDF", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            });
        }
    }

    private void uploadPDFFileFirebase(Uri data)
    {
        Log.v("Astro", "PDF UPLOADING!!!!");
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

                        String subjectname = subjectName.getText().toString();
                        String assignmentname = assignmentName.getText().toString();
                        Map<String, Object> assignment = new HashMap<>();
                        assignment.put("studentname", studentName);
                        assignment.put("rollnumber", rollNumber);
                        assignment.put("submitteddate", getCurrentTimestamp());
                        assignment.put("uri", uri.toString());
                        assignment.put("assignmentid",uuid);
                        Log.v("Astro", "Fields Uploading.....!!!!!!!");
                        Log.v("Astro", "Branch: "+branch);
                        Log.v("Astro", "UUID: "+uuid);
                        db.collection("submittedassignments")
                                .document(branch)
                                .collection(uuid)
                                .add(assignment)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference)
                                    {
                                        Log.d("Astro", "Assignment Submitted Successfully !!");
                                        Toast.makeText(ProjectView.this, "Assignment Submitted Successfully !!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Log.d("Astro", "Assignment Submission Failed !!");
                                Toast.makeText(ProjectView.this, "Assignment Submission Failed", Toast.LENGTH_SHORT).show();
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
                Log.v("Astro", "File Uploaded.. " + (int) progress + "%");
            }
        });
    }

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(Calendar
                .getInstance().getTime());
    }
}