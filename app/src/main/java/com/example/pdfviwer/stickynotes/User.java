package com.example.pdfviwer.stickynotes;

import android.graphics.drawable.Drawable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "My_Table")
public class User {
    public String getDrwable() {
        return drawable;
    }

    public void setDrwable(String drwable) {
        this.drawable = drwable;
    }



    @PrimaryKey(autoGenerate = true)
    public int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return Date;
    }

    public User() {

    }

    public User(String Title, String Body, String drawable, String date) {
        this.Date=date;
        this.Title = Title;
        this.Body = Body;
        this.drawable=drawable;
    }
    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }

    @ColumnInfo(name = "Title_Text")
    public String Title;

    @ColumnInfo(name = "Body_Text")
    public String Body;

    @ColumnInfo(name = "drawble")
    public String drawable;

    @ColumnInfo(name = "Date")
    public  String Date;


}