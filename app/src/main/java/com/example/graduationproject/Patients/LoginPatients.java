package com.example.graduationproject.Patients;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduationproject.Doctor.DoctorActivity;
import com.example.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPatients extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    ImageView doctorImg;
    TextView createAccount;
    EditText email1;
    EditText pass;
    Button loginBtn;
    private FirebaseAnalytics mFirebaseAnalytics;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_screen);

        doctorImg = findViewById(R.id.doctorImg);
        createAccount = findViewById(R.id.createAccount);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        email1 = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        progressDialog=new ProgressDialog(this);
        loginBtn.setOnClickListener(view -> {
            loginUser();
            finish();

        });

        doctorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPatients.this, DoctorActivity.class));
                finish();

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPatients.this, CreateAccActivity.class));
                finish();

            }
        });

    }

    private void loginUser(){
        String email = email1.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email)){
            email1.setError("Email cannot be empty");
            email1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginPatients.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPatients.this, HomeScreen.class));
                        progressDialog.cancel();
                        finish();

                    }else{
                        Toast.makeText(LoginPatients.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }

}