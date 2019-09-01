package com.example.todolist2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class CreateNewTask extends AppCompatActivity {

    TextView titlepage, add_title_task, add_statut_task, add_date_task, add_note_task;
    EditText title_task, note_task, statut_task, date_task;
    DatePickerDialog picker;
    Button button_Save_Task, button_Cancel_Task, button_Date;
    DatabaseReference reference;
    // ID de la tâche
    Integer taskNumber = new Random().nextInt();
    String key_task = Integer.toString(taskNumber);
    private FirebaseUser user_id;
    // FirebaseUser firebaseUser = user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);

        // Header
        titlepage = (TextView) findViewById(R.id.titlepage);

        add_title_task = (TextView) findViewById(R.id.add_title_task);
        add_statut_task = (TextView) findViewById(R.id.add_statut_task);
        add_date_task = (TextView) findViewById(R.id.add_date_task);
        add_note_task = (TextView) findViewById(R.id.add_note_task);


        title_task = (EditText) findViewById(R.id.title_task);
        statut_task = (EditText) findViewById(R.id.statut_task);
        date_task = (EditText) findViewById(R.id.date_task);
        note_task = (EditText) findViewById(R.id.note_task);

        button_Save_Task = (Button) findViewById(R.id.buttonSaveTask);
        button_Cancel_Task = (Button) findViewById(R.id.buttonCancelTask);
        button_Date = (Button) findViewById(R.id.buttonDate);


        // Fonction pour le Calendrier
        button_Date.setInputType(InputType.TYPE_NULL);
        button_Date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateNewTask.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_task.setText("Date : " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        button_Save_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insertion des données sur la BDD
                reference = FirebaseDatabase.getInstance().getReference().child("List").child("Task" + taskNumber);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("title_task").setValue(title_task.getText().toString());
                        dataSnapshot.getRef().child("statut_task").setValue(statut_task.getText().toString());
                        dataSnapshot.getRef().child("date_task").setValue(date_task.getText().toString());
                        dataSnapshot.getRef().child("note_task").setValue(note_task.getText().toString());
                        dataSnapshot.getRef().child("key_task").setValue(key_task);

                        Intent a = new Intent(CreateNewTask.this,HomeActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        // Intent pour aller sur la page de création de tâche
        button_Cancel_Task = findViewById(R.id.buttonCancelTask);
        button_Cancel_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(CreateNewTask.this, HomeActivity.class);
                startActivity(b);
            }
        });

    }
}
