package com.himanshusampath.assignmentsubmission;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;

    EditText emailInput, passwordInput;
    Button loginButton;
    ProgressBar progressBar;
    ConstraintLayout loginUI;
    TextView forgotPassword;

    String emailString, passwordString;

    private ConstraintLayout emailui, emailsentui;
    String forgotemailString;
    private EditText forgotemailinput;
    private Button forgotSubmitbutton, loginAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.passwordinput);
        loginButton = findViewById(R.id.loginbutton);
        progressBar = findViewById(R.id.progressBar);
        loginUI = findViewById(R.id.loginui);
        forgotPassword = findViewById(R.id.forgotpassword);

        emailui = findViewById(R.id.emailUI);
        emailsentui = findViewById(R.id.emailsentUI);
        forgotemailinput = findViewById(R.id.forgotemailInput);
        forgotSubmitbutton = findViewById(R.id.submitbutton);
        loginAgainButton = findViewById(R.id.loginAgain);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                loginUI.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                emailString = emailInput.getText().toString();
                passwordString = passwordInput.getText().toString();
                emailValidationlogin();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openForgotPassword();
            }
        });

        forgotSubmitbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forgotemailString = forgotemailinput.getText().toString();
                emailValidation();
            }
        });

        loginAgainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailsentui.setVisibility(View.INVISIBLE);
                loginUI.setVisibility(View.VISIBLE);
            }
        });
    }

    private void emailValidationlogin()
    {
        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
        {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        checkEmailExistsOrNot();
    }

    private void emailValidation()
    {
        if (forgotemailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(forgotemailString).matches())
        {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        forgotcheckEmailExistsOrNot();
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
                    Toast.makeText(LoginActivity.this, "Account does not Exist!!", Toast.LENGTH_SHORT).show();
                } else
                {
                    // email existed
                    firebaseLogin();
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

    private void forgotcheckEmailExistsOrNot()
    {
        mAuth.fetchSignInMethodsForEmail(forgotemailString).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>()
        {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task)
            {
                Log.d(TAG, "" + task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0)
                {
                    // email not existed
                    Toast.makeText(LoginActivity.this, "Account does not Exist!!", Toast.LENGTH_SHORT).show();
                } else
                {
                    // email existed
                    openEmailSent();
                    firebaseForgotPassword();
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

    private void firebaseLogin()
    {
        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            openFirstActivity();
                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            loginUI.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void firebaseForgotPassword()
    {
        FirebaseAuth.getInstance().sendPasswordResetEmail(forgotemailString)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    private void openFirstActivity()
    {
        Intent intent = new Intent(this, PendingSubmission.class);
        startActivity(intent);
    }

    private void openForgotPassword()
    {
        loginUI.setVisibility(View.INVISIBLE);
        emailui.setVisibility(View.VISIBLE);
    }

    private void openEmailSent()
    {
        emailui.setVisibility(View.INVISIBLE);
        emailsentui.setVisibility(View.VISIBLE);
    }
}