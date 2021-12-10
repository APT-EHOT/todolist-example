package com.example.todolist_example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface TasksDao {

    @Insert
    Completable insert(Task task);

    @Query("SELECT * FROM tasks_table WHERE id=:id")
    Observable<Task> getById(int id);

    @Query("SELECT * FROM tasks_table")
    Observable<List<Task>> getTasks();

}
