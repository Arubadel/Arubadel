package com.delos.github.arubadel.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.delos.github.arubadel.MainActivity;
import com.delos.github.arubadel.R;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset, btnLogin2,btnSignUp,btnSignIn,btnCreateAccount;
    private LinearLayout btn_login_singup_linear;
    private MaterialTextField TextInputLayoutPass,TextInputLayoutEmail;
    private ImageView TeamWinLoginLogo,XdaLoginLogo;
    /*Backend less user */
    private BackendlessUser mBackendlessUser = new BackendlessUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp( this, "1FCF27BB-4307-6EEB-FF51-A6D6A83F1100", "FD1E2430-8650-03F1-FF07-CA84C667AC00", "v1" );
        /*Get Firebase auth instance*/
            auth = FirebaseAuth.getInstance();
        /*Check if user has already login */
            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnLogin2=(Button)findViewById(R.id.btn_login_2);
        btn_login_singup_linear=(LinearLayout)findViewById(R.id.btn_login_singup_linear);
        TextInputLayoutPass=(MaterialTextField)findViewById(R.id.text_input_layout_password);
        btn_login_singup_linear.setVisibility(View.VISIBLE);
        XdaLoginLogo=(ImageView)findViewById(R.id.xda_login_logo);
        TeamWinLoginLogo=(ImageView)findViewById(R.id.teamwin_login_logo);
        XdaLoginLogo.setVisibility(View.VISIBLE);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnCreateAccount = (Button) findViewById(R.id.create_account_button);
        TextInputLayoutEmail=(MaterialTextField)findViewById(R.id.text_input_layout_email);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignUp.setVisibility(View.VISIBLE);
                btn_login_singup_linear.setVisibility(View.GONE);
                inputEmail.setVisibility(View.VISIBLE);
                TextInputLayoutPass.setVisibility(View.VISIBLE);
                TextInputLayoutEmail.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                TeamWinLoginLogo.setVisibility(View.VISIBLE);
                XdaLoginLogo.setVisibility(View.GONE);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignIn.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);
                btnLogin2.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);

            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCreateAccount.setVisibility(View.GONE);
                btnLogin2.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(LoginActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    mBackendlessUser.setEmail(email);
                                    mBackendlessUser.setPassword(password);
                                    Backendless.UserService.register( mBackendlessUser, new BackendlessCallback<BackendlessUser>()
                                            {
                                                @Override
                                                public void handleResponse( BackendlessUser backendlessUser )
                                                {
                                                    Log.i( "Registration", backendlessUser.getEmail() + " successfully registered" );
                                                }
                                            });
                                            finish();
                                }
                            }
                        });
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login_singup_linear.setVisibility(View.GONE);
                btnLogin2.setVisibility(View.VISIBLE);
                inputEmail.setVisibility(View.VISIBLE);
                TextInputLayoutPass.setVisibility(View.VISIBLE);
                TextInputLayoutEmail.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                TeamWinLoginLogo.setVisibility(View.VISIBLE);
                XdaLoginLogo.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);

            }
        });

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Backendless.UserService.login( email, password, new AsyncCallback<BackendlessUser>()
                                    {
                                        public void handleResponse( BackendlessUser user )
                                        {
                                            Log.i( "Login: ", " successfully Login" );

                                        }

                                                public void handleFault( BackendlessFault fault )
                                        {
                                        // login failed, to get the error code call fault.getCode()
                                        }
                                    });
                                    finish();
                                }
                            }
                        });

            }
        });
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        XdaLoginLogo.setVisibility(View.VISIBLE);
        btn_login_singup_linear.setVisibility(View.VISIBLE);
        TeamWinLoginLogo.setVisibility(View.GONE);
        btnLogin2.setVisibility(View.GONE);
        inputEmail.setVisibility(View.GONE);
        TextInputLayoutPass.setVisibility(View.GONE);
        TextInputLayoutEmail.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        btnCreateAccount.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}

