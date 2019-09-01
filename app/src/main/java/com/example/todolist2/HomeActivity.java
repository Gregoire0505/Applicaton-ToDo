package com.example.todolist2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    DatabaseReference maDatabase;

    public TextView titlepage;
    public TextView subtitlepage;
    public TextView endpage;
    public Button button_Add_Task;
    public Button button_sign_out;
    public ArrayList<ListeToDo> list;
    public ListeAdapter listAdapter;

    private static final int MY_REQUEST_CODE = 123;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Header
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);

        // Intent pour aller sur la page de création de tâche
        button_Add_Task = findViewById(R.id.buttonAddNew);
        button_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeActivity.this, CreateNewTask.class);
                startActivity(a);
            }
        });

        // Travaille avec les données du RecyclerView et
        myRecyclerView = (RecyclerView) findViewById(R.id.list_task);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ListeToDo>();
        /*
        listAdapter = new ListeAdapter(MainActivity.this, list);
        myRecyclerView.setAdapter(listAdapter);
        */

        // Récupération des données depuis FireBase
        maDatabase = FirebaseDatabase.getInstance().getReference().child("List");
        maDatabase.keepSynced(true);
        maDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Code pour retrouver les données et remplacer le layout
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ListeToDo p = dataSnapshot1.getValue(ListeToDo.class);
                    list.add(p);
                }
                listAdapter = new ListeAdapter(HomeActivity.this, list);
                myRecyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Code pour montrer une erreur
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

        button_sign_out = (Button) findViewById(R.id.button_sign_out);
        button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent c = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(c);
            }
        });

        /*
        button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout
                AuthUI.getInstance().signOut(HomeActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                button_sign_out.setEnabled(false);
                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        */

    }
        /*
        private void showSignInOptions() {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTheme(R.style.MyTheme)
                            .build(),MY_REQUEST_CODE
            );
        }
        */

    }
