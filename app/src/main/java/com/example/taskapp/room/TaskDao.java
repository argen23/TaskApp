package com.example.taskapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp.ui.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM  task ")
    List<Task> getAll();

    @Query("SELECT * FROM  task ORDER by title ASC")
    List<Task> sortAll();

    @Insert
    void insert(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void editItem(Task task);

}
