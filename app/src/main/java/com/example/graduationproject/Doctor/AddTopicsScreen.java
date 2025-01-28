package com.example.graduationproject.Doctor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.graduationproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddTopicsScreen extends AppCompatActivity {

    Uri videouri;
    MediaController mediaController;
    EditText address;
    EditText cotent;
    Button add_btn;
    Uri imageUri;
    StorageReference storageReference;
    StorageReference storageReference2;

    ProgressDialog progressDialog;

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_topics_screen);

        progressDialog=new ProgressDialog(this);

        add_btn=findViewById(R.id.add_btn);
        cotent=findViewById(R.id.topic_details);
        address=findViewById(R.id.topic_address);
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    public void uploadTopic() {
        String title = address.getText().toString();
        String content = cotent.getText().toString();

        Map<String, Object> topic = new HashMap<>();
        topic.put("topic_name", title);
        topic.put("topic_content", content);

        firebaseFirestore.collection("Topics")
                .add(topic)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(AddTopicsScreen.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(AddTopicsScreen.this, "Failed to add topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}