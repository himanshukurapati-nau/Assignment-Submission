package com.himanshusampath.assignmentcreationfaculty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    String nameString, emailString, passwordString, confirmPasswordString;
    Button registerButton;
    ConstraintLayout registerUI, loadingUI;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.nameinput);
        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.passwordinput);
        confirmPasswordInput = findViewById(R.id.confirmpasswordinput);

        registerUI = findViewById(R.id.registerui);
        loadingUI = findViewById(R.id.loadingui);

        registerButton = findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerUI.setVisibility(View.INVISIBLE);
                loadingUI.setVisibility(View.VISIBLE);

                formValidation();
                //1.FormValidation
                //2.CheckEmailExistornot
                //3.FirebaseSignup
                //4.uploaduserdata
            }
        });
    }

    //Validates Form
    private void formValidation()
    {
        nameString = nameInput.getText().toString();
        emailString = emailInput.getText().toString();
        passwordString = passwordInput.getText().toString();
        confirmPasswordString = confirmPasswordInput.getText().toString();

        if (nameString.isEmpty())
        {
            Toast.makeText(this, "Enter you name", Toast.LENGTH_SHORT).show();
            setRegisterUI();
            return;
        }
        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
        {
            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
            setRegisterUI();
            return;
        }
        if (passwordString.length() < 6)
        {
            Toast.makeText(this, "Enter password with atleast 6 characters", Toast.LENGTH_SHORT).show();
            setRegisterUI();
            return;
        }
        if (!confirmPasswordString.equals(passwordString))
        {
            Toast.makeText(this, "Passwords did not match", Toast.LENGTH_SHORT).show();
            setRegisterUI();
            return;
        }

        Toast.makeText(this, "Validation Successfull!!", Toast.LENGTH_SHORT).show();
        checkEmailExistsOrNot();
    }

    private void setRegisterUI()
    {
        registerUI.setVisibility(View.VISIBLE);
        loadingUI.setVisibility(View.INVISIBLE);
    }

    void checkEmailExistsOrNot()
    {
        mAuth.fetchSignInMethodsForEmail(emailString).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
            {
                Log.d(TAG, "" + task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0)
                {
                    // email not existed
                    firebaseSignup();
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

    //Firebase Signup
    private void firebaseSignup()
    {
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
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

    //Uploads form information to Firestore
    private void uploadUserData()
    {
        //Uploads user information to Firestore
        Map<String, Object> faculty = new HashMap<>();
        faculty.put("Name", nameString);

        db.collection("faculty")
                .add(faculty)
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

    private void openFirstActivity()
    {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

}