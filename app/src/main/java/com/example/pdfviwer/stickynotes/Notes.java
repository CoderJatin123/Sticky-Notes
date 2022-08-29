package com.example.pdfviwer.stickynotes;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Notes_Adapter notes_adapter;
    UserViewModel userViewModel;
    public static List<User> Notesh;
    FloatingActionButton creat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        creat = findViewById(R.id.creat);
        recyclerView=findViewById(R.id.recyclerView);

        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users1) {
//                Notesh.clear();
                Notesh=users1;
                notes_adapter=new Notes_Adapter(Notes.this,Notesh);
                recyclerView.setAdapter(notes_adapter);
//                notes_adapter.Noticed(users1);


            }
        });
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Note_Pad.class);
                startActivity(intent);
            }
        });
    }
    public class setrecyclerView extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Notesh=AppDatabase.getInstance(getApplicationContext()).userDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);


        }
    }
}