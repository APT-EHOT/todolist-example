package com.example.todolist_example.data;

import androidx.room.Dao;
import androidx.room.Insert;

import io.reactivex.Completable;

@Dao
public interface TasksDao {

    @Insert
    Completable insert(Task task);

}
