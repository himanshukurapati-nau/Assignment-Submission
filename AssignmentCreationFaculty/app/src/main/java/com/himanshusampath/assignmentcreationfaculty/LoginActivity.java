package com.himanshusampath.assignmentcreationfaculty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String emailString, passwordString, forgotemailString;
    Button loginButton, submitButton, loginAgainButton;
    TextView forgotPassword;
    EditText forgotemailInput;
    ConstraintLayout loginUI, loadingUI, emailUI, emailsentUI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setReference();
        mAuth = FirebaseAuth.getInstance();

        setButtonsOnClick();

    }

    //Sets reference to all elements
    private void setReference()
    {
        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.passwordinput);

        loginButton = findViewById(R.id.loginbutton);
        forgotPassword = findViewById(R.id.forgotpasswordbutton);
        forgotemailInput = findViewById(R.id.forgotemailInput);
        submitButton = findViewById(R.id.submitbutton);
        loginAgainButton = findViewById(R.id.loginAgain);

        loginUI = findViewById(R.id.loginui);
        loadingUI = findViewById(R.id.loadingui);
        emailUI = findViewById(R.id.emailui);
        emailsentUI = findViewById(R.id.emailsentui);
    }

    //Sets onClickListener to all buttons
    private void setButtonsOnClick()
    {
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forgotemailString = forgotemailInput.getText().toString();
                emailValidation();
            }
        });

        loginAgainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailsentUI.setVisibility(View.INVISIBLE);
                loginUI.setVisibility(View.VISIBLE);
            }
        });
    }

    //Checks if user exists with that email
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

    //Login email validation
    private void emailValidationlogin()
    {
        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
        {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        loginUI.setVisibility(View.INVISIBLE);
        loadingUI.setVisibility(View.VISIBLE);
        checkEmailExistsOrNot();
    }

    //Firebase Login
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
                            loadingUI.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    //checks if email is valid or not forgot password
    private void emailValidation()
    {
        if (forgotemailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(forgotemailString).matches())
        {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        forgotcheckEmailExistsOrNot();
    }

    //check if email is valid or not in forgot password UI
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

    //Firebase Forgot Password
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

    //Opens ForgotPassword UI
    private void openForgotPassword()
    {
        loginUI.setVisibility(View.INVISIBLE);
        emailUI.setVisibility(View.VISIBLE);
    }

    //Opens Email sent UI
    private void openEmailSent()
    {
        emailUI.setVisibility(View.INVISIBLE);
        emailsentUI.setVisibility(View.VISIBLE);
    }

    private void openFirstActivity()
    {
        Intent intent = new Intent(this, NewAssignmentCreation.class);
        startActivity(intent);
    }
}