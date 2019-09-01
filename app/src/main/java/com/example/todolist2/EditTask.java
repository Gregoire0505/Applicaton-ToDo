package com.example.todolist2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    EditText title_task;
    EditText statut_task;
    EditText note_task;
    EditText date_task;
    Button buttonModifyTask, buttonDeleteTask, button_Date, buttonBack;
    DatabaseReference reference;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        title_task = findViewById(R.id.title_task);
        statut_task = findViewById(R.id.statut_task);
        date_task = findViewById(R.id.date_task);
        note_task = findViewById(R.id.note_task);

        buttonModifyTask = (Button) findViewById(R.id.buttonModifyTask);
        buttonDeleteTask = (Button) findViewById(R.id.buttonDeleteTask);
        button_Date = (Button) findViewById(R.id.buttonDate);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        // Obtenir valeur de la page précèdente
        title_task.setText(getIntent().getStringExtra("title_task"));
        statut_task.setText(getIntent().getStringExtra("statut_task"));
        date_task.setText(getIntent().getStringExtra("date_task"));
        note_task.setText(getIntent().getStringExtra("note_task"));

        // Id de la tâche
        final String keyTask = getIntent().getStringExtra("key_task");

        reference = FirebaseDatabase.getInstance().getReference().child("List").child("Task" + keyTask);

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
                picker = new DatePickerDialog(EditTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_task.setText("Date : " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent a = new Intent(EditTask.this, HomeActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Evénement lorsque l'on sauvegarde les changements
        buttonModifyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("title_task").setValue(title_task.getText().toString());
                        dataSnapshot.getRef().child("statut_task").setValue(statut_task.getText().toString());
                        dataSnapshot.getRef().child("date_task").setValue(date_task.getText().toString());
                        dataSnapshot.getRef().child("note_task").setValue(note_task.getText().toString());
                        dataSnapshot.getRef().child("key_task").setValue(keyTask);

                        Intent a = new Intent(EditTask.this,HomeActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(EditTask.this, HomeActivity.class);
                startActivity(back);
            }
        });

    }
}

