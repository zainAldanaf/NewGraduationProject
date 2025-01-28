package com.example.graduationproject.Doctor;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.Adapters.DoctorAdapter;
import com.example.graduationproject.Modules.Topics;
import com.example.graduationproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity implements DoctorAdapter.ItemClickListener ,DoctorAdapter.ItemClickListener2 {

    FloatingActionButton fba;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Topics> items;
    DoctorAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor);
        fba=findViewById(R.id.fab);

        rv = findViewById(R.id.recyclerview);
        items = new ArrayList<Topics>();
        adapter =new DoctorAdapter(this,items,this,this);
        getTopics();

        fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorActivity.this, AddTopicsScreen.class));
            }
        });
    }
        public  void getTopics(){
            db.collection("DoctorTopic").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.d("alaa", "onSuccess: LIST EMPTY");
                                return;
                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String id = documentSnapshot.getId();
                                        String title = documentSnapshot.getString("topic_address");
                                        String content = documentSnapshot.getString("topic_details");


                                        Topics note = new Topics(id, title ,content);
                                        items.add(note);

                                        rv.setLayoutManager(layoutManager);
                                        rv.setHasFixedSize(true);
                                        rv.setAdapter(adapter);
                                        ;
                                        adapter.notifyDataSetChanged();
                                        Log.e("LogDATA", items.toString());

                                    }
                                }
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("LogDATA", "get failed with ");

                        }
                    });
        }

    public  void DeleteTopic(final Topics topics){
        AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(this);
        alertDialogBuilderLabelDelete.create();
        alertDialogBuilderLabelDelete.setIcon(R.drawable.xbutton);
        alertDialogBuilderLabelDelete.setCancelable(false);
        alertDialogBuilderLabelDelete.setTitle("Delete label");
        alertDialogBuilderLabelDelete.setMessage("Are you sure to delete?");
        alertDialogBuilderLabelDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogBox, int id) {
                db.collection("Topics").document(topics.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                items.remove(topics);
                                Toast.makeText(DoctorActivity.this, " Removed Successfully", Toast.LENGTH_SHORT).show();
                                rv.setAdapter(adapter);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("logData","get failed with delete");
                            }
                        });

            }
        });
        alertDialogBuilderLabelDelete.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.cancel();
                    }
                });        alertDialogBuilderLabelDelete.show();
    }

    @Override
    public void onItemClick(int position, String id) {

    }

    @Override
    public void onItemClick2(int position, String id) {
        DeleteTopic(items.get(position));

    }
}