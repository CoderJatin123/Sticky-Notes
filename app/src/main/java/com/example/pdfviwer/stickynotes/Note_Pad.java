package com.example.pdfviwer.stickynotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import org.w3c.dom.Text;

import java.util.*;

import static com.colors.params.color_Params.*;
import static com.example.pdfviwer.stickynotes.Notes.Notesh;

public class Note_Pad extends AppCompatActivity {
    String drawable,dData;
    private TextView dateFormate;
    private EditText Title_Text,BodyText;
    private Button colorButton;
    String Finalcolor,FinalColor;
    private  String title,body;
    BottomSheetDialog bottomSheetDialog;
    int n=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);

        initilization();
        ShowButtonSheetDilog();
        List<User> List=Notesh;


        getMyIntent();
         if(dData==null) {
            dateFormate.setText(getDate());
        }
        if(n!= -1){

            Title_Text.setText(title);
            BodyText.setText(body);
            if(drawable!=null){
                colorButton.setBackgroundColor(Color.parseColor(Finalcolor));
            }
            if(dData!=null){
                dateFormate.setText(dData);
            }


        }

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog.show();
            }
        });
bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    private void initilization() {
        bottomSheetDialog=new BottomSheetDialog(this);
        dateFormate=findViewById(R.id.date);
        colorButton=findViewById(R.id.colors);
        Title_Text=findViewById(R.id.TitleText);
        BodyText=findViewById(R.id.BodyText);
        Title_Text.setText("");
        BodyText.setText("");
    }

    private void ShowButtonSheetDilog() {

        @SuppressLint("InflateParams") View view =getLayoutInflater().inflate(R.layout.colors_palet,null,false);

        View df=view.findViewById(R.id.cd);
        View db=view.findViewById(R.id.cb);
        View dr=view.findViewById(R.id.cr);
        View dy=view.findViewById(R.id.cy);
        View dg=view.findViewById(R.id.cg);
        View dp=view.findViewById(R.id.cp);
        df.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Finalcolor=color_default;
                colorButton.setBackgroundColor(Color.parseColor(color_default));
                bottomSheetDialog.dismiss();

            }
        });
        db.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Finalcolor=color_blue;
                colorButton.setBackgroundColor(Color.parseColor(color_blue));
                bottomSheetDialog.dismiss();


            }
        });
        dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Finalcolor=color_red;
                colorButton.setBackgroundColor(Color.parseColor(color_red));
                bottomSheetDialog.dismiss();

            }
        });
        dy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Finalcolor=color_yellow;
                colorButton.setBackgroundColor(Color.parseColor(color_yellow));
                bottomSheetDialog.dismiss();

            }
        });
        dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Finalcolor=color_green;
                colorButton.setBackgroundColor(Color.parseColor(color_green));
                bottomSheetDialog.dismiss();

            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDrawble updateDrawble=new UpdateDrawble();
                updateDrawble.execute(color_pink);
                Finalcolor=color_pink;
                colorButton.setBackgroundColor(Color.parseColor(color_pink));
                bottomSheetDialog.dismiss();

            }
        });
        bottomSheetDialog.setContentView(view);

    }

    private void getMyIntent() {

        Intent intent = getIntent();
        n=intent.getIntExtra("uid",-1);
        title=intent.getStringExtra("title");
        body=intent.getStringExtra("body");
        drawable=intent.getStringExtra("drawable");
        dData=intent.getStringExtra("Data");
        Finalcolor=drawable;

    }
    class InsertAsynckTask extends AsyncTask<User,Void,User> {

        @Override
        protected User doInBackground(User... users) {
            AppDatabase.getInstance(getApplicationContext()).userDao().insert(users[0]);
            return users[0];
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            n= user.uid;

        }
    }



    class UpadteAsynckTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if(!Title_Text.getText().toString().equals(title) || !BodyText.getText().toString().equals(body)){
                AppDatabase.getInstance(getApplicationContext()).userDao().updateById(n,Title_Text.getText().toString(),BodyText.getText().toString(),getDate());

            }

            AppDatabase.getInstance(getApplicationContext()).userDao().setDrwableByID(n,Finalcolor);
            return null;
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        if(n!=-1){

            UpadteAsynckTask upadteAsynckTask = new UpadteAsynckTask();
            upadteAsynckTask.execute();
        }
        else{
            if(!Title_Text.getText().toString().equals("") || !BodyText.getText().toString().equals("")){
                User user =new User(Title_Text.getText().toString(),BodyText.getText().toString(),Finalcolor,getDate());
                InsertAsynckTask insertAsynckTask=new InsertAsynckTask();
                insertAsynckTask.execute(user);
            }

        }

    }

    class UpdateDrawble extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... voids) {
            AppDatabase.getInstance(getApplicationContext()).userDao().setDrwableByID(n,voids[0]);
            return null;
        }
    }
    String getDate(){

        String DateToStr = null;
        Date curDate = new Date();
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
               SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
               format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
               DateToStr = format.format(curDate);
               dateFormate.setText(DateToStr);
           }
        return DateToStr;
    }
}