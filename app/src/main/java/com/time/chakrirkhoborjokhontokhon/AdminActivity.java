package com.time.chakrirkhoborjokhontokhon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText loginEmailEditText,loginPasswordEditText;
    private Button loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.setTitle("Admin Login");

        mAuth = FirebaseAuth.getInstance();

        loginEmailEditText = findViewById(R.id.login_email_editText_id);
        loginPasswordEditText = findViewById(R.id.login_password_editText_id);
        loginButton = findViewById(R.id.login_login_btn_id);

        loadingBar = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View v) {
        LoginUser();
    }

    private void LoginUser() {
        String email = loginEmailEditText.getText().toString().trim();
        String password = loginPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
           loginEmailEditText.setError("Please enter email address");
           loginEmailEditText.requestFocus();
          }
        else if (TextUtils.isEmpty(password))
        {
            loginPasswordEditText.setError("Please enter password");
            loginEmailEditText.requestFocus();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the informations.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        loadingBar.dismiss();
                        Toast.makeText(AdminActivity.this, "Login is unsuccessfull,Please try again", Toast.LENGTH_LONG).show();
                    }else {
                        loadingBar.dismiss();
                        startActivity(new Intent(AdminActivity.this,AddNewActivity.class));
                        Toast.makeText(AdminActivity.this, "Login is successfull", Toast.LENGTH_LONG).show();

                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

                    }
                }
            });
        }
    }
}
