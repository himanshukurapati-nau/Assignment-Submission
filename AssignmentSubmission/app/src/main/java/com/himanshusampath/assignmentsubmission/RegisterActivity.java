package com.himanshusampath.assignmentsubmission;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity
{

    private Spinner branchDropdown;

    private EditText nameInput, emailInput, rollnumberInput, passwordInput, confirmpasswordInput;
    private Button submitButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //Variables
    String emailtostring, rollnumber, password, nameString, branchString;
    static String[] items = new String[]{"CSE", "IT", "ECE", "EEE", "Mechanical"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        branchDropdown = findViewById(R.id.branchdrop);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        branchDropdown.setAdapter(adapter);

        nameInput = findViewById(R.id.nameinput);
        emailInput = findViewById(R.id.emailinput);
        rollnumberInput = findViewById(R.id.rollnumberinput);
        passwordInput = findViewById(R.id.password);
        confirmpasswordInput = findViewById(R.id.confirmpassword);
        submitButton = findViewById(R.id.submitbutton);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validateForm();
            }
        });
    }

    private void validateForm()
    {

        //Check name is not null
        nameString = nameInput.getText().toString();
        if (nameString.isEmpty())
        {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        emailtostring = emailInput.getText().toString();
        //check email format
        if (emailtostring.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailtostring).matches())
        {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        //Check roll number is not null
        rollnumber = rollnumberInput.getText().toString();
        if (rollnumber.isEmpty() && rollnumber.length() != 10)
        {
            Toast.makeText(this, "Enter your Roll Number", Toast.LENGTH_SHORT).show();
            return;
        }

        //check password Validation
        password = passwordInput.getText().toString();
        if (password.length() < 6)
        {
            Toast.makeText(this, "Enter password with atleast 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        //Check password matches confirm password
        String confirmpassword = confirmpasswordInput.getText().toString();
        if (!confirmpassword.equals(password))
        {
            Toast.makeText(this, "Passwords did not match", Toast.LENGTH_SHORT).show();
            return;
        }

        branchString = branchDropdown.getSelectedItem().toString();

        Toast.makeText(this, "Validation Successfull!!", Toast.LENGTH_SHORT).show();
        checkEmailExistsOrNot();
    }

    private void firebaseSignin()
    {
        mAuth.createUserWithEmailAndPassword(emailtostring, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            uploadUserData();
                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    void checkEmailExistsOrNot()
    {
        mAuth.fetchSignInMethodsForEmail(emailtostring).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
            {
                Log.d(TAG, "" + task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0)
                {
                    // email not existed
                    firebaseSignin();
                } else
                {
                    // email existed
                    Toast.makeText(RegisterActivity.this, "Account Already Exist", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    private void uploadUserData()
    {
        //Uploads user information to Firestore

        Map<String, Object> student = new HashMap<>();
        student.put("Name", nameString);
        student.put("Roll Number", rollnumber);
        student.put("Branch", branchString);

        db.collection("student")
                .add(student)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Toast.makeText(RegisterActivity.this, "Information Stored Successfully!!", Toast.LENGTH_SHORT).show();
                        openFirstActivity();
                    }
                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(RegisterActivity.this, "Information Storing Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openFirstActivity()
    {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }
}