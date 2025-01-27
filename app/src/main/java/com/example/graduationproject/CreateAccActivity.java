package com.example.graduationproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.graduationproject.Modules.PationtsModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

public class CreateAccActivity extends AppCompatActivity {

    TextView loginPage;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    Button Sign_btn;
    EditText fullname;
    EditText birth_date;
    EditText address1;
    EditText emailaddress;
    TextView haveAccount;

    EditText phone_number;
    // private DatabaseReference RootRef, chatRef,userRef;

    EditText pass;
    EditText confirmpass;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PationtsModule pationtsModule;
    private FirebaseAnalytics mFirebaseAnalytics;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_create_acc);


        Sign_btn = findViewById(R.id.signup_button);
       fullname = findViewById(R.id.nametxt);
        emailaddress = findViewById(R.id.emailtxt);
        pass = findViewById(R.id.passwordtxt);
        phone_number = findViewById(R.id.phonetxtt);
        confirmpass = findViewById(R.id.confirm_passwordtxt);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Patients");
        progressDialog = new ProgressDialog(this);

        loginPage = findViewById(R.id.loginPage);

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccActivity.this, LoginPatients.class));
                finish();

            }
        });

        Sign_btn.setOnClickListener(view ->{
            createUser();
            finish();

        });

    }


    private void createUser(){
        String email = emailaddress.getText().toString();
        String password = pass.getText().toString();
        String name = fullname.getText().toString();
        String confrimpassword = confirmpass.getText().toString();
        String phone = phone_number.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailaddress.setError("Email cannot be empty");
            emailaddress.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            fullname.setError("Password cannot be empty");
            fullname.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            address1.setError("Password cannot be empty");
            address1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            phone_number.setError("Password cannot be empty");
            phone_number.requestFocus();

        }else{
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                      startActivity(new Intent(CreateAccActivity.this, HomeScreen.class));
//                        doctorModule pationtsModule = new doctorModule(name, address, birthdate, email, phone, password, confrimpassword);
//                        databaseReference.child(name).setValue(pationtsModule);
                        String deviceToken= FirebaseInstanceId.getInstance().getToken();
                        String currentUserID=firebaseAuth.getCurrentUser().getUid();
                        databaseReference.child("Patients").child(currentUserID).setValue("");
                        databaseReference.child("Patients").child(currentUserID).child("device_token").setValue(deviceToken);
                        firebaseFirestore.collection("Patients").document(FirebaseAuth.getInstance().getUid()).set(
                                new PationtsModule(name,email,phone,password,confrimpassword)
                        );

                        progressDialog.cancel();
                    }else{
                        Toast.makeText(CreateAccActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }


    }

}