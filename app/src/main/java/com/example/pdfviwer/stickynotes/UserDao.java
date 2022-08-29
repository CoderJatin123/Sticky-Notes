package com.example.pdfviwer.stickynotes;

import android.graphics.drawable.Drawable;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT EXISTS (SELECT * FROM My_Table WHERE Title_Text=:x)")
    Boolean is_exist(String x);


    @Query("SELECT * FROM My_Table")
    List<User> getAll();

    @Query("SELECT * FROM My_Table")
    LiveData<List<User>> getLiveAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User users);

    @Query("SELECT uid FROM My_Table WHERE Title_Text=:Title")
    int getIdbyTitle(String Title);


    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("UPDATE My_Table SET drawble=:drawable WHERE uid=:id")
    void setDrwableByID(int id ,String drawable);

    @Query("UPDATE MY_TABLE SET Body_Text =:B_text,Title_Text =:T_text,Date=:date WHERE uid=:i")
    void updateById(int i,String T_text, String B_text,String date);




}
