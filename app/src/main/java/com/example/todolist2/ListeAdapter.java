package com.example.todolist2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.MyViewHolder> {

    public Context context;
    private ArrayList<ListeToDo> listeTaches;

    public ListeAdapter (Context c, ArrayList<ListeToDo> p) {
        context = c;
        listeTaches = p;
    }

    // RecyclerView
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_task, statut_task, date_task, note_task, key_task;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_task = (TextView) itemView.findViewById(R.id.title_task);
            statut_task = (TextView) itemView.findViewById(R.id.statut_task);
            date_task = (TextView) itemView.findViewById(R.id.date_task);
            note_task = (TextView) itemView.findViewById(R.id.note_task);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Context context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.is_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title_task.setText(listeTaches.get(i).getTitle_task());
        myViewHolder.statut_task.setText(listeTaches.get(i).getStatut_task());
        myViewHolder.date_task.setText(listeTaches.get(i).getDate_task());
        myViewHolder.note_task.setText(listeTaches.get(i).getNote_task());

        final String getTitleTask = listeTaches.get(i).getTitle_task();
        final String getStatutTask = listeTaches.get(i).getStatut_task();
        final String getDateTask = listeTaches.get(i).getDate_task();
        final String getNoteTask = listeTaches.get(i).getNote_task();
        final String getKeyTask = listeTaches.get(i).getKey_task();

        // Afficher les valeurs de la tâche lorsqu"on la modifie
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(v.getContext(), EditTask.class);
                aa.putExtra("title_task", getTitleTask);
                aa.putExtra("statut_task", getStatutTask);
                aa.putExtra("date_task", getDateTask);
                aa.putExtra("note_task", getNoteTask);
                aa.putExtra("key_task", getKeyTask);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listeTaches.size();
    }

}

